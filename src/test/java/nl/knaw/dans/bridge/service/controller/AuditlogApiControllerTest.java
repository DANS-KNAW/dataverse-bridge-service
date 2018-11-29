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