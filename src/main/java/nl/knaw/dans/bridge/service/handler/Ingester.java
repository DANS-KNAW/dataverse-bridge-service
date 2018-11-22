package nl.knaw.dans.bridge.service.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import nl.knaw.dans.bridge.plugin.lib.common.*;
import nl.knaw.dans.bridge.plugin.lib.exception.BridgeException;
import nl.knaw.dans.bridge.plugin.lib.util.StateEnum;
import nl.knaw.dans.bridge.service.api.config.BridgeConfEnvironment;
import nl.knaw.dans.bridge.service.db.dao.ArchivingAuditlogDao;
import nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog;
import nl.knaw.dans.bridge.service.generated.model.DarData;
import nl.knaw.dans.bridge.service.generated.model.IngestData;
import nl.knaw.dans.bridge.service.util.SimpleEmail;
import org.apache.abdera.i18n.iri.IRI;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Component
public class Ingester {
    @Autowired
    private Environment env;

    @Autowired
    private BridgeConfEnvironment bcenv;

    @Autowired
    private SimpleEmail simpleEmail;

    @Autowired
    private ArchivingAuditlogDao archivingAuditlogDao;

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private ArchivingAuditLog createNewArchived(IngestData ingestData, String darIri) {
        ArchivingAuditLog archivingAuditLog = new ArchivingAuditLog();
        archivingAuditLog.setStartTime(new Date());
        archivingAuditLog.setSrcMetadataUrl(ingestData.getSrcData().getSrcMetadataUrl());
        archivingAuditLog.setSrcMetadataVersion(ingestData.getSrcData().getSrcMetadataVersion());
        archivingAuditLog.setDestinationIri(darIri);
        archivingAuditLog.setDarName(ingestData.getDarData().getDarName());
        archivingAuditLog.setState(StateEnum.IN_PROGRESS.toString());
        archivingAuditlogDao.create(archivingAuditLog);
        return archivingAuditLog;
    }

    public ArchivingAuditLog performIngestToDar(boolean syncIngest, IngestData ingestData, String darIri, DarPluginConf darPluginConf, ObjectMapper objectMapper) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        log.info(">>>>>>> Trying ingest to " + ingestData.getDarData().getDarName() + " from url source: " + ingestData.getSrcData().getSrcMetadataUrl());
        XslSource xs = darPluginConf.getXslSourceList().stream().filter(xslSource -> xslSource.getName().equals(ingestData.getSrcData().getSrcName())).findAny().orElse(null);
        URLClassLoader actionClassLoader = darPluginConf.getActionClassLoader();
        Class actionClass = Class.forName(darPluginConf.getActionClassName(), true, actionClassLoader);
        IAction action = (IAction)actionClass.newInstance();
        log.info("action: " + action.toString());
        ArchivingAuditLog archivingAuditLog = createNewArchived(ingestData, darIri);
        if (syncIngest) {
            try {
                IResponseData responseData = ingestToDar(ingestData, darIri, xs.getXslTransformerList(), action, archivingAuditLog);
                log.debug(responseData.getResponse());
                archivingAuditLog = archivingAuditlogDao.getById(archivingAuditLog.getId());
            } catch (BridgeException e) {
                archivingAuditLog.setState(HttpStatus.BAD_REQUEST.name());
                //   simpleEmail.sendToAdmin("Sync Process is failed.", e.getMessage());
            }
            return archivingAuditLog;
        }

        asyncIngestToDar(ingestData, darIri, xs.getXslTransformerList(), action, archivingAuditLog, objectMapper);
        return archivingAuditlogDao.getById(archivingAuditLog.getId());
    }

    private void asyncIngestToDar(IngestData ingestData, String darIri, List<XslTransformer> xslConverterList, IAction action, ArchivingAuditLog archivingAuditLog, ObjectMapper objectMapper) {
        archivingAuditLog.setState(StateEnum.REGISTERED.toString());
        archivingAuditlogDao.update(archivingAuditLog);
        Flowable.fromCallable(() -> {
            log.info("Starting ASYNC ingest process using Flowable.fromCallable()");
            Instant start = Instant.now();
            IResponseData responseDataHolder = ingestToDar(ingestData, darIri, xslConverterList, action, archivingAuditLog);
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).getSeconds();
            log.info("The process is done in " + timeElapsed + " seconds.");
            log.info("#### End of ingest process using Flowable.fromCallable() ####");
            return responseDataHolder;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(erd -> {
                    if (erd != null)
                        writeArchivedlog(archivingAuditLog, erd, objectMapper);
                    else {
                        log.error("The response data is null.");
                        simpleEmail.sendToAdmin("[FATAL ERROR]", "omething really wrong here!\nerd is null.");
                    }
                }, e -> {
                    String msg = "[throwable], msg: " + e.getMessage() + " \n srcMetadata: " + archivingAuditLog.getSrcMetadataUrl() + " \nVersion: " + archivingAuditLog.getSrcMetadataVersion();
                    log.error(msg);
                    if (e instanceof BridgeException) {
                        BridgeException be = (BridgeException) e;
                        msg = "[" + be.getClassName() + "] " + be.getMessage();

                    } else {
                        msg = e.getMessage();
                    }
                    String prevMsg = archivingAuditLog.getLog();
                    if (prevMsg != null)
                        msg = prevMsg + "|" + msg;
                    String subject =  "[doOnError] on id: " + archivingAuditLog.getId();
                    archivingAuditLog.setLog(msg);
                    archivingAuditLog.setState(StateEnum.ERROR.toString());
                    archivingAuditLog.setEndTime(new Date());
                    archivingAuditlogDao.update(archivingAuditLog);
                    if (archivingAuditLog != null && archivingAuditLog.getId() != null)
                        simpleEmail.sendToAdmin("[throwable] on id: " + archivingAuditLog.getId(), msg);
                    else
                        simpleEmail.sendToAdmin("[throwable]", msg);
                });
    }

    private IResponseData ingestToDar(IngestData ingestData, String darIri, List<XslTransformer> xslConverterList, IAction action, ArchivingAuditLog archivingAuditLog) throws BridgeException {
        String bagDir = env.getProperty("bridge.temp.dir.bags");
        SourceDar sourceDar = null;
        DarData darData = ingestData.getDarData();
        DestinationDar destinationDar = new DestinationDar(new IRI(darIri), darData.getDarUsername(), darData.getDarPassword(), darData.getDarUserAffiliation());
        try {
            sourceDar = new SourceDar(new URL(ingestData.getSrcData().getSrcMetadataUrl()), ingestData.getSrcData().getSrcApiToken());
        } catch (MalformedURLException e) {
            log.error("Error during creating SourceDar - ingestToDar, msg: {}", e.getMessage());
            throw new BridgeException("Error during creating SourceDar - ingestToDar, msg: ", e, this.getClass());
        }
        Optional<Map<String, String>> transformResult = action.transform(sourceDar, destinationDar, xslConverterList);
        Optional<File> bagitFile = Optional.empty();
        if (transformResult.isPresent())
            bagitFile = action.composeBag(sourceDar, bagDir, transformResult.get());

        if (bagitFile.isPresent()) {
            log.info("Set the bagit dir in database. Bagit dir: " + bagDir);
            archivingAuditLog.setBagitDir(bagitFile.get().getAbsolutePath().replace(".zip", ""));
            archivingAuditlogDao.update(archivingAuditLog);
        }
        IResponseData responseDataHolder = action.execute(destinationDar
                , bagitFile, transformResult);
        if (responseDataHolder != null) {
            log.info("Intermediate saving the response data information.");
            archivingAuditLog.setState(responseDataHolder.getState().get().toString());
            archivingAuditLog.setLog(responseDataHolder.getResponse());
            archivingAuditlogDao.update(archivingAuditLog);
        }
        return responseDataHolder;
    }

    private void writeArchivedlog(ArchivingAuditLog archivingAuditLog, IResponseData erd, ObjectMapper objectMapper) throws JsonProcessingException {
        if (erd.getPidLandingPage() != null)
            archivingAuditLog.setLandingPage(erd.getPidLandingPage().get().toString());
        if (erd.getDarLandingPage() != null)
            archivingAuditLog.setDarLandingPage(erd.getDarLandingPage().get().toString());
        archivingAuditLog.setPid(erd.getPid().get());
        archivingAuditLog.setEndTime(new Date());
        archivingAuditLog.setState(erd.getState().get().toString());
        if (erd.getResponse() != null)
            archivingAuditLog.setLog(erd.getResponse());
        log.info("Ingest finish. Status " + erd.getState());
        if ( erd.getState().isPresent() && erd.getState().get() == StateEnum.ARCHIVED) {
            //delete bagitdir and its zip.
            removeBagit(archivingAuditLog);
        } else {
            log.error("Something wrong here, state:  " + erd.getState());
            log.error(objectMapper.writeValueAsString(archivingAuditLog));
            simpleEmail.sendToAdmin(erd.getState().get().toString(), objectMapper.writeValueAsString(archivingAuditLog));
        }
        archivingAuditlogDao.update(archivingAuditLog);
    }

    private void removeBagit(ArchivingAuditLog archivingAuditLog) {
        log.info(archivingAuditLog.getBagitDir());
        if (archivingAuditLog.getBagitDir() !=null) {
            File bagDirToDelete = FileUtils.getFile(archivingAuditLog.getBagitDir());
            File bagZipFileToDelete = FileUtils.getFile(archivingAuditLog.getBagitDir() + ".zip");
            boolean bagZipFileIsDeleted = FileUtils.deleteQuietly(bagZipFileToDelete);
            if (bagZipFileIsDeleted) {
                log.info("Bagit files are deleted.");
                archivingAuditLog.setBagitDir("DELETED");
            } else {
                log.warn(bagDirToDelete.getAbsolutePath() + " is not deleted");
                log.warn(bagZipFileToDelete + " is not deleted");
            }
        }
    }
}
