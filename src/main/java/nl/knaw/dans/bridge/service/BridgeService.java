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
package nl.knaw.dans.bridge.service;

import nl.knaw.dans.bridge.service.api.config.PluginStorageProperties;
import nl.knaw.dans.bridge.service.generated.api.ArchivingApiController;
import nl.knaw.dans.bridge.service.generated.api.AuditlogApiController;
import nl.knaw.dans.bridge.service.generated.api.DarApiController;
import nl.knaw.dans.bridge.service.generated.api.PluginApiController;
import nl.knaw.dans.bridge.service.generated.io.swagger.Swagger2SpringBoot;
import nl.knaw.dans.bridge.service.generated.io.swagger.config.SwaggerDocumentationConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
    @Author Eko Indarto
 */

@EnableTransactionManagement
@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties({
        PluginStorageProperties.class
})
@ComponentScan(basePackages = { "nl.knaw.dans.bridge.service.generated.io.swagger", "nl.knaw.dans.bridge.service.generated.api"
        , "nl.knaw.dans.bridge.service.api", "nl.knaw.dans.bridge.service.handler", "nl.knaw.dans.bridge.service.db"
        , "nl.knaw.dans.bridge.service.util"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                value = {Swagger2SpringBoot.class, SwaggerDocumentationConfig.class, ArchivingApiController.class, PluginApiController.class
                                , DarApiController.class, AuditlogApiController.class}))

public class BridgeService implements CommandLineRunner {

    @Override
    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

    public static void main(String[] args) throws Exception {
        new SpringApplication(BridgeService.class).run(args);
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
