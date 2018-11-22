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