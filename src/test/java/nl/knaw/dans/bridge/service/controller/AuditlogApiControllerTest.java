package nl.knaw.dans.bridge.service.controller;

import nl.knaw.dans.bridge.service.Order;
import org.junit.Before;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuditlogApiControllerTest extends ApiControllerTest{


    @Before
    public void init() {

    }

    @Test
    @Order(order = 1)
    public void getAll() throws Exception {
        mockMvc.perform(get("/auditlog/get-all").accept("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(order = 2)
    public void getById() throws Exception {
        mockMvc.perform(get("/auditlog/1").accept("application/json"))
                .andExpect(status().isNotFound());
    }
}