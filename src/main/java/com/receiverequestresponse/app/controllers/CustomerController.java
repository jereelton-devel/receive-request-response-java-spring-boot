package com.receiverequestresponse.app.controllers;

import com.receiverequestresponse.app.entities.CustomerDTO;
import com.receiverequestresponse.app.utils.WebHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RestController
@Tag(name = "Receiver Request Response App Sample")
@Schema(implementation = CustomerDTO.class)
@CrossOrigin(origins = "*")
public class CustomerController extends WebHandler {

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
        return consumerError(HttpStatus.METHOD_NOT_ALLOWED, "Wrong request");
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
    public ResponseEntity<?> rejectToGetAndPost() {
        return consumerError(HttpStatus.METHOD_NOT_ALLOWED, "Wrong request");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handlerRootWebClientResponseException(HttpRequestMethodNotSupportedException ex) {
        return consumerError(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handlerRootWebClientRequestException(RuntimeException ex) {
        return applicationError(500, ex.getMessage());
    }

}
