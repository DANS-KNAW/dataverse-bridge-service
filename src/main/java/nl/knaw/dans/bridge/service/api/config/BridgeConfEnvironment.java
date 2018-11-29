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
package nl.knaw.dans.bridge.service.api.config;

import nl.knaw.dans.bridge.plugin.lib.common.DarPluginConf;
import nl.knaw.dans.bridge.service.util.PluginRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/*
    @author Eko Indarto
    This class is needed since the Spring's autowiring happens too late.

    Note: System.exit convention
    https://docs.oracle.com/javase/7/docs/api/java/lang/Runtime.html#exit(int)

    System.exit(0) or EXIT_SUCCESS;  ---> Success
    System.exit(1) or EXIT_FAILURE;  ---> Exception
    System.exit(-1) or EXIT_ERROR;   ---> Error
 */
@Configuration
public class BridgeConfEnvironment implements EnvironmentAware {
    private static final Logger LOG = LoggerFactory.getLogger(BridgeConfEnvironment.class);
    private static Environment env;
    private static final List<DarPluginConf> pluginList = new ArrayList<>();
    private static final Map<String, String> darTarget = new HashMap<>();
    private String activeProfile = null;

    @Override
    public void setEnvironment(Environment env) {
        BridgeConfEnvironment.env = env;
        if (env.getActiveProfiles().length == 0) {
            LOG.warn("# NO profile is given. Please start the application with a profile, eg: '-Dspring.profiles.active=dev'");
        } else {
            activeProfile = env.getActiveProfiles()[0];//takes only the first one.
            LOG.info("#        Starting Dataverse Bridge Using '{}' profile.       #", activeProfile);
        }
        LOG.info("###############################################################");
        checkingRequiredProperties();
        checkingRequiredDirs();
        registerPlugins();
        LOG.info("#                                                             #");
        LOG.info("###############################################################");
    }

    private void checkingRequiredProperties() {
        LOG.info("Checking required properties....");
        List<String> requiredProperties = Arrays.asList("bridge.base.path", "bridge.apps.support.email.from", "spring.mail.host", "bridge.apikey", "bridge.temp.dir.bags");
        requiredProperties.forEach(x -> {
            LOG.info("# Property name: {}  value: {}", x, env.getProperty(x));
            if(env.getProperty(x) == null || Objects.requireNonNull(env.getProperty(x)).isEmpty()){
                LOG.error("'{}' not found.", x);
                System.exit(1);
            }
        });
    }

    public static List<DarPluginConf> getPluginList() {
        return pluginList;
    }

    private void checkingRequiredDirs() {
        //The following folders are required.
        LOG.info("#                Check the required directories               #");
        List<String> requiredDirs = Arrays.asList("static/xsl", env.getProperty("bridge.database.location"), "config", env.getProperty("bridge.plugins.dir")
                                                    , env.getProperty("bridge.temp.dir.bags"));
        requiredDirs.forEach(dir -> {
            LOG.info("# Directory name: {}", dir);
            Path path = Paths.get(dir);
            if (!Files.exists(path)) {
                try {
                    Path reqDirPath = Files.createDirectories(path);
                    if (reqDirPath == null) {
                        LOG.error("Failed to create directory: {}", path);
                        System.exit(1);
                    }
                } catch (IOException e) {
                    LOG.error("[IOException] - [{}], msg: {}", dir, e.getMessage());
                    System.exit(1);
                }
            }else {
                LOG.info("#     '{}' directory found. ", dir);
            }
        });
    }
    private void registerPlugins(){
        LOG.info("#                                                             #");
        LOG.info("#                 Trying to register plugin...                #");
        try {
            //Eko says: of course we should check many thing here, eq: dir exist, files extension, structures, etc!
            //Checking whether the plugins directory exist or not, is already done by checkingRequiredDirs();
            File pluginsBaseDir = new File(Objects.requireNonNull(env.getProperty("bridge.plugins.dir")));
            File pluginsDir[] = pluginsBaseDir.listFiles(pathname -> !pathname.isFile());
            if (pluginsDir != null) {
                for (File darPluginDir : pluginsDir) {
                    DarPluginConf darPluginConf = PluginRegisterService.attachDarPlugin(darPluginDir);
                    if (darPluginConf == null) break;

                    pluginList.add(darPluginConf);
                }
            }

            LOG.info("# Number of plugin: {}", pluginList.size());

        } catch (MalformedURLException e) {
            LOG.error("Fail to start the Bridge Service.....");
            LOG.error("MalformedURLException, cause by: {}", e.getMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            LOG.error("Fail to start the Bridge Service.....");
            LOG.error("plugins directory doesn't exist" );
            LOG.error("FileNotFoundException, cause by: {}", e.getMessage());
            System.exit(1);//Yes, the application will terminated!
        }
        if (pluginList.isEmpty()) {
            LOG.warn("No plugin is registered. Adding at least one plugin is required, otherwise you cannot use this application");
            LOG.warn("Adding plugin can be done by uploading the plugins in zip format through bridge api");
        }
    }
}