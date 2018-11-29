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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nl.knaw.dans.bridge.plugin.lib.common.DarPluginConf;
import nl.knaw.dans.bridge.plugin.lib.exception.BridgeException;
import nl.knaw.dans.bridge.service.api.config.BridgeConfEnvironment;
import nl.knaw.dans.bridge.service.generated.api.PluginApi;
import nl.knaw.dans.bridge.service.generated.model.Plugin;
import nl.knaw.dans.bridge.service.generated.model.XslSource;
import nl.knaw.dans.bridge.service.util.PluginRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-08-25T13:23:02.972+02:00")

@Controller
public class PluginApiController implements PluginApi {
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    @Autowired
    private Environment env;
    @Autowired
    private BridgeConfEnvironment bcenv;
    @Autowired
    private PluginRegisterService pluginRegisterService;

    @org.springframework.beans.factory.annotation.Autowired
    public PluginApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
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
    @ApiOperation(value = "Uploads plugin", nickname = "uploadPlugin", notes = "Add a new plugin. The existing plugin with the same name will be overwritten.", tags = {"Plugins",})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Plugin succesfully added."),
            @ApiResponse(code = 400, message = "Plugi couldn't have been added."),
            @ApiResponse(code = 405, message = "Invalid input")})
    @RequestMapping(value = "/plugin/{dar-name}",
            consumes = {"multipart/form-data"},
            method = RequestMethod.POST)
    public ResponseEntity<Void> uploadPlugin(@ApiParam(value = "", required = true) @RequestHeader(value = "api_key", required = true) String apiKey, @ApiParam(value = "todo", required = true) @PathVariable("dar-name") String darName, @ApiParam(value = "") @RequestPart("file") MultipartFile zipPlugin) {
        if (getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
                if (env.getProperty("bridge.allow.plugin.upload").equals("true") && !apiKey.equals(env.getProperty("bridge.apikey")))
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

                try {
                    DarPluginConf tdrPluginConf = pluginRegisterService.storePlugin(zipPlugin.getInputStream(), zipPlugin.getOriginalFilename(), darName);
                    //register plugin
                    bcenv.getPluginList().add(tdrPluginConf);
                    return new ResponseEntity<>(HttpStatus.OK);
                } catch (BridgeException e) {
                    log.error("Error occured on {}, msg {} ", e.getClassName(), e.getMessage());
                } catch (IOException e) {
                    log.error("uploadPlugin - IOException, msg: ", e.getMessage());
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default PluginApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    @ApiOperation(value = "List of plugins", nickname = "getAllPlugins", notes = "List of plugins", response = Plugin.class, responseContainer = "List", tags = {"Plugins",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Plugin.class, responseContainer = "List")})
    @RequestMapping(value = "/plugin/get-all",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<Plugin>> getAllPlugins() {
        if (getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    List<DarPluginConf> pluginsList = bcenv.getPluginList();
                    List<Plugin> lp = pluginsList.stream().map(darPluginConf -> {
                        Plugin plugin = new Plugin();
                        plugin.setDarName(darPluginConf.getDarName());
                        plugin.darIri(darPluginConf.getDarIri().toString());
                        plugin.setActionClassName(darPluginConf.getActionClassName());
                        plugin.setPluginLibLocation(darPluginConf.getActionClassLoader().getURLs()[0].toString().replace(System.getProperty("user.dir"), ""));
                        List<XslSource> xss = darPluginConf.getXslSourceList().stream().map(xslSource ->
                        {
                            XslSource xs = new XslSource();
                            xs.name(xslSource.getName());
                            List<nl.knaw.dans.bridge.service.generated.model.XslTransformer> xts = xslSource.getXslTransformerList()
                                    .stream().map(pxt -> {
                                        nl.knaw.dans.bridge.service.generated.model.XslTransformer xt = new nl.knaw.dans.bridge.service.generated.model.XslTransformer();
                                        xt.setXslName(pxt.getName());
                                        xt.setXslUrl(pxt.getUrl().toString());
                                        return xt;
                                    }).collect(Collectors.toList());
                            xs.setXslTransformerList(xts);
                            return xs;
                        }).collect(Collectors.toList());
                        plugin.setXsl(xss);
                        return plugin;
                    }).collect(Collectors.toList());
                    return new ResponseEntity<>(getObjectMapper().get().readValue(objectMapper.writeValueAsString(lp), List.class), HttpStatus.OK);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default PluginApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
