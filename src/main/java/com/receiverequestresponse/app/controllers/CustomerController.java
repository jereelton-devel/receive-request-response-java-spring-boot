package com.receiverequestresponse.app.controllers;

import com.receiverequestresponse.app.entities.CustomerDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Receiver Request Response App Sample")
@Schema(implementation = CustomerDTO.class)
@CrossOrigin(origins = "*")
public class CustomerController {

    /**
     * Reject Request (not POST|GET)
     */
    @Operation(summary = "Reject the Wrong request - not POST|GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "405", description = "Wrong Request", content = {
                    @Content(mediaType = "text")
            })
    })
    @RequestMapping(
            value = {"/", "/api", "/api/customers"},
            method = {
                    RequestMethod.PUT,
                    RequestMethod.PATCH,
                    RequestMethod.DELETE,
                    RequestMethod.HEAD,
                    RequestMethod.OPTIONS
            }
    )
    public ResponseEntity<?> reject() {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED).body("Wrong request");
    }

    /**
     * Reject Request (only POST|GET)
     */
    @Operation(summary = "Reject the Wrong request to POST|GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "405", description = "Wrong Request POST|GET", content = {
                    @Content(mediaType = "text")
            })
    })
    @RequestMapping(
            value = {"/", "/api"},
            method = {
                    RequestMethod.POST,
                    RequestMethod.GET
            }
    )
    public ResponseEntity<?> rejectGet() {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED).body("Wrong request");
    }

}
