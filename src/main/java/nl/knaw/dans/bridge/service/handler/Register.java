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
package nl.knaw.dans.bridge.service.handler;

import nl.knaw.dans.bridge.plugin.lib.common.*;
import nl.knaw.dans.bridge.plugin.lib.exception.BridgeException;
import nl.knaw.dans.bridge.service.db.dao.ArchivingAuditlogDao;
import nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog;
import nl.knaw.dans.bridge.service.generated.model.DarData;
import nl.knaw.dans.bridge.service.generated.model.IngestData;
import org.apache.abdera.i18n.iri.IRI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class Register {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private ArchivingAuditlogDao archivingAuditlogDao;

    public IResponseData request(IngestData ingestData, String darIri, List<XslTransformer> xslConverterList, IAction action, ArchivingAuditLog archivingAuditLog) throws BridgeException {
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

        IResponseData responseDataHolder = action.execute(destinationDar
                , Optional.empty(), transformResult);
        if (responseDataHolder != null) {
            log.info("Intermediate saving the response data information.");
            archivingAuditLog.setState(responseDataHolder.getState().get().toString());
            archivingAuditLog.setLog(responseDataHolder.getResponse());
            archivingAuditlogDao.update(archivingAuditLog);
        }
        return responseDataHolder;
    }
}
