package nl.knaw.dans.bridge.service.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DarApiControllerTest extends ApiControllerTest{

    @Test
    public void getAllDarIri() throws Exception {
        mockMvc.perform(get("/dar/get-all").accept("application/json"))
                .andExpect(status().isOk());
    }
}