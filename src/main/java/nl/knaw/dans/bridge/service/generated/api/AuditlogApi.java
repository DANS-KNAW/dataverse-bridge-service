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
/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.0).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package nl.knaw.dans.bridge.service.generated.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2018-11-13T20:06:05.738+01:00[Europe/Amsterdam]")

@Api(value = "auditlog", description = "the auditlog API")
public interface AuditlogApi {

    Logger log = LoggerFactory.getLogger(AuditlogApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Deletes all Auditlog", nickname = "deleteAll", notes = "", tags={ "Archiving Auditlog", })
    @ApiResponses(value = { 
        @ApiResponse(code = 400, message = "Invalid api_key supplied"),
        @ApiResponse(code = 404, message = "Auditlog not found") })
    @RequestMapping(value = "/auditlog/delete-all",
        method = RequestMethod.DELETE)
    default ResponseEntity<Void> deleteAll(@ApiParam(value = "" ,required=true) @RequestHeader(value="api_key", required=true) String apiKey) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuditlogApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Deletes an Auditlog", nickname = "deleteById", notes = "", tags={ "Archiving Auditlog", })
    @ApiResponses(value = { 
        @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 404, message = "Auditlog not found") })
    @RequestMapping(value = "/auditlog/{id}",
        method = RequestMethod.DELETE)
    default ResponseEntity<Void> deleteById(@ApiParam(value = "" ,required=true) @RequestHeader(value="api_key", required=true) String apiKey,@ApiParam(value = "Auditlog id to delete",required=true) @PathVariable("id") Long id) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuditlogApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Delete records filtered by its state", nickname = "deleteFilteredByState", notes = "", tags={ "Archiving Auditlog", })
    @ApiResponses(value = { 
        @ApiResponse(code = 400, message = "Invalid api_key supplied"),
        @ApiResponse(code = 404, message = "Auditlog not found") })
    @RequestMapping(value = "/auditlog/delete/{state}",
        method = RequestMethod.DELETE)
    default ResponseEntity<Void> deleteFilteredByState(@ApiParam(value = "" ,required=true) @RequestHeader(value="api_key", required=true) String apiKey,@ApiParam(value = "Auditlog state to delete",required=true) @PathVariable("state") String state) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuditlogApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Operation to retrive all Archived datasets", nickname = "getAll", notes = "Operation to retrive all Archived datasets.", response = nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog.class, responseContainer = "List", tags={ "Archiving Auditlog", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog.class, responseContainer = "List") })
    @RequestMapping(value = "/auditlog/get-all",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<List<nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog>> getAll() {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuditlogApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "todo", nickname = "getById", notes = "todo", response = nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog.class, tags={ "Archiving Auditlog", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Dataset succesfully created.", response = nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog.class) })
    @RequestMapping(value = "/auditlog/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog> getById(@ApiParam(value = "todo",required=true) @PathVariable("id") Long id) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuditlogApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "todo", nickname = "getByState", notes = "todo", response = nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog.class, responseContainer = "List", tags={ "Archiving Auditlog", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog.class, responseContainer = "List") })
    @RequestMapping(value = "/auditlog/filtered-by-state/{state}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<List<nl.knaw.dans.bridge.service.db.domain.ArchivingAuditLog>> getByState(@ApiParam(value = "todo",required=true) @PathVariable("state") String state) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default AuditlogApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
