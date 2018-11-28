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
package nl.knaw.dans.bridge.service.controller;

import nl.knaw.dans.bridge.service.Order;
import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ArchivingApiControllerTest extends ApiControllerTest{

    @Ignore
    @Test
    @Order(order = 1)
    public void ingestToDar() throws Exception {
        File initialFile = new File("src/test/resources/demo-ingest-data.json");
        if (!initialFile.exists())
            assert false;
        byte[] bytes = FileUtils.readFileToByteArray(initialFile);
        mockMvc.perform(post("/archiving")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("api_key", "0k31nd@rt0")
                .content(bytes))
                .andExpect(status().isCreated());
    }

    @Ignore
    @Test
    @Order(order = 2)
    public void getArchivingState() throws Exception {
        final MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("srcMetadataUrl","http://ddvn.dans.knaw.nl:8080/api/datasets/export?exporter=ddi&persistentId=hdl:10122004/AKMI");
        multiValueMap.add("srcMetadataVersion","1.0");
        multiValueMap.add("targetDarName", "DEMO");
        mockMvc.perform(get("/archiving/state")
                .params(multiValueMap).accept("application/json"))
                .andExpect(status().isOk());
    }
}