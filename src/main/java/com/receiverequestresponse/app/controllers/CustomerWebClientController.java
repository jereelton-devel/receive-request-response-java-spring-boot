package com.receiverequestresponse.app.controllers;

import com.receiverequestresponse.app.entities.CustomerDTO;
import com.receiverequestresponse.app.entities.CustomerEntity;
import com.receiverequestresponse.app.services.CustomerWebClientService;
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
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@ControllerAdvice
@RestController
@Tag(name = "Receiver Request Response App Sample - WebClient")
@Schema(implementation = CustomerDTO.class)
@CrossOrigin(origins = "*")
public class CustomerWebClientController extends WebHandler {

    private final CustomerWebClientService customerWebClientService;

    @Autowired
    public CustomerWebClientController(CustomerWebClientService customerWebClient) {
        this.customerWebClientService = customerWebClient;
    }

    /**
     * Create Customer
     */
    @Operation(summary = "Add new customer by Web Client")
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
    @PostMapping(path = "/api/customers/web_client")
    public Mono<Object> createByWebClient(HttpServletRequest headers, @Valid @RequestBody(required = false) CustomerEntity customer) {
        return customerWebClientService.save(headers, customer);
    }

    /**
     * Read Customer
     */
    @Operation(summary = "Get customer by Web Client")
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
    @GetMapping(path = "/api/customers/web_client/{customer_id}")
    public Mono<Object> readByWebClient(HttpServletRequest headers, @PathVariable("customer_id") String customer_id) {
        return customerWebClientService.findById(headers, customer_id);
    }

    /**
     * Read All Customers
     */
    @Operation(summary = "Get all customers by Web Client")
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
    @GetMapping(path = "/api/customers/web_client")
    public Flux<Object> readAllByWebClient(HttpServletRequest headers) {
        return customerWebClientService.findAll(headers);
    }

    /**
     * Update Customer
     */
    @Operation(summary = "Update an customer by Web Client")
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
    @PutMapping(path = "/api/customers/web_client/{customer_id}")
    public Mono<Object> updateByWebClient(
            HttpServletRequest headers,
            @PathVariable("customer_id") String customer_id,
            @Valid @RequestBody(required = false) JSONObject customer_data
    ) {
        return customerWebClientService.update(headers, customer_id, customer_data);
    }

    /**
     * Delete Customer
     */
    @Operation(summary = "Delete an customer by Web Client")
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
    @DeleteMapping(path = "/api/customers/web_client/{customer_id}")
    public Mono<Object> deleteByWebClient(HttpServletRequest headers, @PathVariable("customer_id") String customer_id) {
        return customerWebClientService.deleteById(headers, customer_id);
    }

    /**
     * Patch Customer
     */
    @Operation(summary = "Fix/Patch an customer by Web Client")
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
    @PatchMapping(path = "/api/customers/web_client/{customer_id}")
    public Mono<Object> patchByWebClient(
            HttpServletRequest headers,
            @PathVariable("customer_id") String customer_id,
            @Valid @RequestBody(required = false) JSONObject customer_data
    ) {
        return customerWebClientService.patch(headers, customer_id, customer_data);
    }

    /**
     * Exceptions
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handlerWebClientResponseException(HttpRequestMethodNotSupportedException ex) {
        return consumerError(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<?> handlerWebClientResponseException(WebClientResponseException ex) {
        return vendorError(ex.getRawStatusCode(), ex.getResponseBodyAsString());
    }

    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<?> handlerWebClientRequestException(WebClientRequestException ex) {
        return applicationError(500, ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handlerWebClientRequestException(RuntimeException ex) {
        return applicationError(500, ex.getMessage());
    }

}
