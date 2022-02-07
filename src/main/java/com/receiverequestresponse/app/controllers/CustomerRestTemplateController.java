package com.receiverequestresponse.app.controllers;

import com.receiverequestresponse.app.entities.CustomerDTO;
import com.receiverequestresponse.app.entities.CustomerEntity;
import com.receiverequestresponse.app.services.CustomerRestTemplateService;
import com.receiverequestresponse.app.utils.WebHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@ControllerAdvice
@RestController
@Tag(name = "Receiver Request Response App Sample - Rest Template")
@Schema(implementation = CustomerDTO.class)
@CrossOrigin(origins = "*")
public class CustomerRestTemplateController extends WebHandler {

    private final CustomerRestTemplateService customerRestTemplateService;

    @Autowired
    public CustomerRestTemplateController(CustomerRestTemplateService customerRestTemplateService) {
        this.customerRestTemplateService = customerRestTemplateService;
    }

    /**
     * Create Customer
     */
    @Operation(summary = "Add new customer by Rest Template")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))
            }),
            @ApiResponse(responseCode = "302", description = "Customer name already exists", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "400", description = "Missing body request", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "500", description = "Server error", content = {
                    @Content(mediaType = "text")
            })
    })
    @PostMapping(path = "/api/customers/rest_template")
    public Object createByRestTemplate(HttpServletRequest headers, @Valid @RequestBody(required = false) CustomerEntity customer) {
        return customerRestTemplateService.save(headers, customer);
    }

    /**
     * Read Customer
     */
    @Operation(summary = "Get customer by Rest Template")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, read one customer", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "500", description = "Server error", content = {
                    @Content(mediaType = "text")
            })
    })
    @GetMapping(path = "/api/customers/rest_template/{customer_id}")
    public ResponseEntity<?> readByRestTemplate(HttpServletRequest headers, @PathVariable("customer_id") String customer_id) {
        return customerRestTemplateService.findById(headers, customer_id);
    }

    /**
     * Read All Customers
     */
    @Operation(summary = "Get all customers by Rest Template")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, read all customers", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "404", description = "Customers not found", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "500", description = "Server error", content = {
                    @Content(mediaType = "text")
            })
    })
    @GetMapping(path = "/api/customers/rest_template")
    public ResponseEntity<?> readAllByRestTemplate(HttpServletRequest headers) {
        return customerRestTemplateService.findAll(headers);
    }

    /**
     * Update Customer
     */
    @Operation(summary = "Update an customer by Rest Template")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, Customer updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Missing body request", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "406", description = "Update is not correct, because it should be total data update", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "500", description = "Server error", content = {
                    @Content(mediaType = "text")
            })
    })
    @PutMapping(path = "/api/customers/rest_template/{customer_id}")
    public Object updateByRestTemplate(
            HttpServletRequest headers,
            @PathVariable("customer_id") String customer_id,
            @Valid @RequestBody(required = false) JSONObject customer_data) {
        return customerRestTemplateService.update(headers, customer_id, customer_data);
    }

    /**
     * Delete Customer
     */
    @Operation(summary = "Delete an customer by Rest Template")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, customer deleted", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "500", description = "Server error", content = {
                    @Content(mediaType = "text")
            })
    })
    @DeleteMapping(path = "/api/customers/rest_template/{customer_id}")
    public ResponseEntity<?> deleteByRestTemplate(HttpServletRequest headers, @PathVariable("customer_id") String customer_id) {
        return customerRestTemplateService.delete(headers, customer_id);
    }

    /**
     * Patch Customer
     */
    @Operation(summary = "Fix/Patch an customer by Rest Template")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, Customer patched", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Missing body request", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "406", description = "Patch is not correct, because it should be partial update", content = {
                    @Content(mediaType = "text")
            }),
            @ApiResponse(responseCode = "500", description = "Server error", content = {
                    @Content(mediaType = "text")
            })
    })
    @PatchMapping(path = "/api/customers/rest_template/{customer_id}")
    public Object patchByRestTemplate(
            HttpServletRequest headers,
            @PathVariable("customer_id") String customer_id,
            @Valid @RequestBody(required = false) JSONObject customer_data) {
        return customerRestTemplateService.patch(headers, customer_id, customer_data);
    }

    /**
     * Exceptions
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handlerRootWebClientResponseException(HttpRequestMethodNotSupportedException ex) {
        return consumerError(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handlerRootWebClientRequestException(RuntimeException ex) {
        return applicationError(500, ex.getMessage());
    }

}
