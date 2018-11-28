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

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PluginApiControllerTest extends ApiControllerTest{

    @Test
    public void getAllPluginsOk() throws Exception {
        mockMvc.perform(get("/plugin/get-all").accept("application/json"))
                .andExpect(status().isOk());
    }

    @Ignore
    @Test
    public void getAllPlugins_DemoPlugin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/plugin/get-all").accept("application/json"))
                .andReturn();

        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String contentType  = mockHttpServletResponse.getContentType();
        String content = mockHttpServletResponse.getContentAsString();
        JSONArray jsonArr = new JSONArray(content);
        JSONObject jsonObject = jsonArr.getJSONObject(0);
        String darName = jsonObject.getString("darName");
        String actionClassName = jsonObject.getString("actionClassName");

        assertEquals("application/json;charset=UTF-8", contentType) ;
        assertEquals(2, jsonArr.length());
        assertEquals("DEMO", darName);
        assertEquals("nl.knaw.dans.dataverse.bridge.plugin.demo.DemoAction", actionClassName);

    }
}