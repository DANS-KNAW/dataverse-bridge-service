package nl.knaw.dans.bridge.service.controller;

import nl.knaw.dans.bridge.service.OrderedSpringRunner;
import nl.knaw.dans.bridge.service.api.controller.ArchivingApiController;
import nl.knaw.dans.bridge.service.api.controller.AuditlogApiController;
import nl.knaw.dans.bridge.service.api.controller.DarApiController;
import nl.knaw.dans.bridge.service.api.controller.PluginApiController;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(OrderedSpringRunner.class)
@Suite.SuiteClasses({DarApiControllerTest.class, PluginApiControllerTest.class, ArchivingApiControllerTest.class, AuditlogApiControllerTest.class})
@SpringBootTest
@TestPropertySource(locations="file:./src/test/resources/application-test.properties")
public abstract class ApiControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    Environment env;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected DarApiController darApiController;

    @Autowired
    private PluginApiController pluginApiController;

    @Autowired
    private ArchivingApiController archivingApiController;

    @Autowired
    private AuditlogApiController auditlogApiController;



    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(this.darApiController, this.pluginApiController, this.archivingApiController, this.auditlogApiController).build();// Standalone context
    }
}