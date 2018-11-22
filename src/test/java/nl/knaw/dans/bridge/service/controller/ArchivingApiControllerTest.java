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