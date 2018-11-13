package nl.knaw.dans.bridge.service.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/*
    @author Eko Indarto
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-04-10T11:02:09.449+02:00")
@Configuration
public class DataverseBridgeDocumentationConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("DANS Bridge to Digital Archive Repository (DAR)")
                .description("A bridge for Archiving Dataverse dataset to Digital Archive Repository (DAR) via the SWORD v2.0 protocol.")
                .license("MIT License")
                .licenseUrl("https://opensource.org/licenses/MIT")
                .termsOfServiceUrl("https://dans.knaw.nl")
                .version("1.0")
                .contact(new Contact("DANS","https://dans.knaw.nl", "eko.indarto@dans.knaw.nl"))
                .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("nl.knaw.dans.bridge.service.api"))
                .build()
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.OffsetDateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }
}
