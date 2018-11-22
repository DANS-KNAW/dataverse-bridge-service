package nl.knaw.dans.bridge.service.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nl.knaw.dans.bridge.service.api.config.BridgeConfEnvironment;
import nl.knaw.dans.bridge.service.generated.api.DarApi;
import nl.knaw.dans.bridge.service.generated.model.DarIri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-08-31T22:37:43.418+02:00")

@Controller
public class DarApiController implements DarApi {
    @Autowired
    private Environment env;

    @Autowired
    private BridgeConfEnvironment bcenv;

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public DarApiController(ObjectMapper objectMapper, HttpServletRequest request) {
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
    @ApiOperation(value = "Operation to retrive all DAR IRI", nickname = "getAllDarIri", notes = "Operation to retrive all DAR IRI", response = DarIri.class, responseContainer = "List", tags={ "DAR IRI", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = DarIri.class, responseContainer = "List") })
    @RequestMapping(value = "/dar/get-all",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<List<DarIri>> getAllDarIri() {
        if (getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    List<DarIri> darIris = new ArrayList<>();
//                    bcenv.getDarTarget().forEach((k, v) -> {
//                        DarIri darIri = new DarIri();
//                        darIri.setDarName(k);
//                        darIri.setIri(v);
//                        darIris.add(darIri);
//                    });
                    return new ResponseEntity<>(getObjectMapper().get().readValue(objectMapper.writeValueAsString(darIris), List.class), HttpStatus.OK);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in public DarApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
