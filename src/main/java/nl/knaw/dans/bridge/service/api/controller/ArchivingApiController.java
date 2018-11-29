/**
 * Copyright (C) 2018 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.bridge.service.api.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nl.knaw.dans.bridge.plugin.lib.common.DarPluginConf;
import nl.knaw.dans.bridge.plugin.lib.util.BridgeHelper;
import nl.knaw.dans.bridge.plugin.lib.util.StateEnum;
import nl.knaw.dans.bridge.service.api.config.BridgeConfEnvironment;
import nl.knaw.dans.bridge.service.db.dao.ArchivingAuditlogDao;
import nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog;
import nl.knaw.dans.bridge.service.generated.api.ArchivingApi;
import nl.knaw.dans.bridge.service.generated.model.IngestData;
import nl.knaw.dans.bridge.service.handler.Ingester;
import nl.knaw.dans.bridge.service.util.SimpleEmail;
import org.apache.abdera.i18n.iri.IRI;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-08-31T22:37:43.418+02:00")

@Controller
@CrossOrigin
public class ArchivingApiController implements ArchivingApi {
    @Autowired
    private Environment env;

    @Autowired
    private BridgeConfEnvironment bcenv;

    @Autowired
    private SimpleEmail simpleEmail;

    @Autowired
    private Ingester ingester;

    @Autowired
    private ArchivingAuditlogDao archivingAuditlogDao;

    private List<DarPluginConf> darPluginConfList = new ArrayList<DarPluginConf>();

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public ArchivingApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        darPluginConfList = bcenv.getPluginList();
    }

    @Override
    public Optional<ObjectMapper> getObjectMapper() {
        return Optional.ofNullable(objectMapper);
    }

    @Override
    public Optional<HttpServletRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    @ApiOperation(value = "Operation to retrive a state of an Archived dataset", nickname = "getArchivingState", notes = "Operation to retrive a state of an Archived dataset by filtering pid, version, dar target.", response = nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog.class, tags={ "Archiving", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Plugin response", response = nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog.class),
            @ApiResponse(code = 200, message = "unexpected error", response = Error.class) })
    @RequestMapping(value = "/archiving/state",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<ArchivingAuditLog> getArchivingState(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "srcMetadataUrl", required = true) String srcMetadataUrl, @NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "srcMetadataVersion", required = true) String srcMetadataVersion, @NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "targetDarName", required = true) String targetDarName) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    ArchivingAuditLog dbArchivingAuditLog = archivingAuditlogDao.getBySrcurlSrcversionTargetiri(srcMetadataUrl, srcMetadataVersion, targetDarName);
                    if (dbArchivingAuditLog == null) {
                        log.error("The following GET request is NOT FOUND: srcMetadataUrl: {}\tsrcMetadataVersion: {}\ttargetDarName: {}",srcMetadataUrl, srcMetadataVersion, targetDarName);
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }

                    return new ResponseEntity<>(getObjectMapper().get().readValue(objectMapper.writeValueAsString(dbArchivingAuditLog), ArchivingAuditLog.class), HttpStatus.OK);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ArchiveApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    @ApiOperation(value = "Operation to Ingest dataset to DAR", nickname = "ingestToDar", notes = "", response = nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog.class, tags={ "Archiving", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Dataset succesfully created.", response = nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog.class),
            @ApiResponse(code = 400, message = "Dataset couldn't have been created."),
            @ApiResponse(code = 405, message = "Invalid input") })
    @RequestMapping(value = "/archiving",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog> ingestToDar(@ApiParam(value = "Dataset object that needs to be added to the Archived's table." ,required=true )  @Valid @RequestBody IngestData ingestData,@ApiParam(value = "" ,required=true) @RequestHeader(value="api_key", required=true) String apiKey,@ApiParam(value = "When the request comes from machine to machine, it is likely doesn't need authentication check of DAR credentials before ingest data." ) @RequestHeader(value="skipDarAuthPreCheck", required=false) Boolean skipDarAuthPreCheck,@ApiParam(value = "By default, the archiving process is done using async process. However some process need in a scync way." ) @RequestHeader(value="sync", required=false) Boolean sync) {
        String errorMessage = "";
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    String appsPropSrcNameApikey = env.getProperty("bridge.apikey.of.src.name." + ingestData.getSrcData().getSrcName().toLowerCase());
                    if (appsPropSrcNameApikey == null ||  !appsPropSrcNameApikey.equals(apiKey)) {
                        log.error("src.name property in the application properties is null or its value is not equal with apikey");
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }

                    Optional<DarPluginConf> darPluginConf = darPluginConfList.stream().filter(x -> x.getDarName().equals(ingestData.getDarData().getDarName())).findAny();
                    if (darPluginConf.isPresent()) {
                        String darIri = darPluginConf.get().getDarIri().toString();
                        log.debug("darIri: " + darIri);
                        if(skipDarAuthPreCheck ==null || !skipDarAuthPreCheck.booleanValue()) {
                            int statusCode = checkCredentials(darIri
                                    , ingestData.getDarData().getDarUsername()
                                    , ingestData.getDarData().getDarPassword()
                                    , env.getProperty("bridge.dar.credentials.checking.timeout", Integer.class));
                            switch (statusCode) {
                                case org.apache.http.HttpStatus.SC_REQUEST_TIMEOUT:
                                    return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
                                case org.apache.http.HttpStatus.SC_FORBIDDEN:
                                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                            }
                        }

                        ArchivingAuditLog dbArchivingAuditLog = archivingAuditlogDao.getBySrcurlSrcversionTargetiri(ingestData.getSrcData().getSrcMetadataUrl()
                                , ingestData.getSrcData().getSrcMetadataVersion()
                                , ingestData.getDarData().getDarName());
                        if (dbArchivingAuditLog != null) {
                            //existing archived or archiving is in progress
                            return new ResponseEntity<>(getObjectMapper().get().readValue(objectMapper.writeValueAsString(dbArchivingAuditLog), ArchivingAuditLog.class), HttpStatus.OK);
                        }
                        ArchivingAuditLog archivingAuditLog = ingester.performIngestToDar((sync != null && sync.booleanValue()),ingestData , darIri, darPluginConf.get(), objectMapper);
                        HttpStatus status = responseStatus(archivingAuditLog);
                        return new ResponseEntity<>(getObjectMapper().get().readValue(objectMapper.writeValueAsString(archivingAuditLog), ArchivingAuditLog.class), status);
                    }
                } catch (URISyntaxException e) {
                    errorMessage = "URISyntaxException: " + e.getMessage();
                } catch (JsonParseException e) {
                    errorMessage = "Couldn't serialize response for content type application/json, msg: " + e.getMessage();
                } catch (JsonMappingException e) {
                    errorMessage = "JsonMappingException: " + e.getMessage();
                } catch (JsonProcessingException e) {
                    errorMessage = "JsonProcessingException: " + e.getMessage();
                } catch (IOException e) {
                    errorMessage = "IOException: " + e.getMessage();
                } catch (IllegalAccessException e) {
                    errorMessage = "IllegalAccessException: " + e.getMessage();
                } catch (InstantiationException e) {
                    errorMessage = "InstantiationException: " + e.getMessage();
                } catch (ClassNotFoundException e) {
                    errorMessage = "ClassNotFoundException: " + e.getMessage();
                }
                log.error(errorMessage);
                //           simpleEmail.sendToAdmin("EXCEPTION ERROR", errorMessage);
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ArchiveApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private HttpStatus responseStatus(ArchivingAuditLog archivingAuditLog) {
        try {
            StateEnum state = StateEnum.fromValue(archivingAuditLog.getState());
            switch (state) {
                case IN_PROGRESS:
                case REGISTERED:
                    return  HttpStatus.CREATED;
                case UPDATED:
                    return HttpStatus.OK;
                case ERROR:
                case UNKNOWN:
                case BAD_REQUEST:
                    return HttpStatus.BAD_REQUEST;
                 default:
                     return HttpStatus.OK;

            }
        }catch (IllegalArgumentException e) {
            //This belongs to standard http status, however, when
            return HttpStatus.valueOf(archivingAuditLog.getState());
        }
    }

    private int checkCredentials(String darIri, String uid, String pwd, int timeout) throws URISyntaxException {
        //check TDR credentials
        //see https://stackoverflow.com/questions/21574478/what-is-the-difference-between-closeablehttpclient-and-httpclient-in-apache-http
        try(CloseableHttpClient httpClient = BridgeHelper.createHttpClient((new IRI(darIri).toURI()), uid, pwd, timeout)){
            HttpGet httpGet = new HttpGet(darIri);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            return response.getStatusLine().getStatusCode();
        } catch (IOException e) {
            return org.apache.http.HttpStatus.SC_REQUEST_TIMEOUT;
        }
    }
}
