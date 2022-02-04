package com.receiverequestresponse.app.controllers;

import com.receiverequestresponse.app.entities.AddressEntity;
import com.receiverequestresponse.app.entities.CustomerDTO;
import com.receiverequestresponse.app.entities.CustomerEntity;
import com.receiverequestresponse.app.services.CustomerRestTemplateService;
import com.receiverequestresponse.app.services.CustomerService;
import com.receiverequestresponse.app.services.CustomerWebClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Receiver Request Response App Sample - WebClient")
@Schema(implementation = CustomerDTO.class)
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerWebClientService customerWebClientService;
    private final CustomerRestTemplateService customerRestTemplateService;

    @Autowired
    public CustomerController(
            CustomerWebClientService customerWebClient,
            CustomerRestTemplateService customerRestTemplateService
            ) {
        this.customerWebClientService = customerWebClient;
        this.customerRestTemplateService = customerRestTemplateService;
    }

    /*Create - By Web Client*/
    @PostMapping(path = "/api/customers/web_client")
    public Mono<Object> createByWebClient(HttpServletRequest headers, @Valid @RequestBody(required = false) CustomerEntity customer) {
        return customerWebClientService.save(headers, customer);
    }

    /*Create - By Rest Template*/
    @PostMapping(path = "/api/customers/rest_template")
    public Object createByRestTemplate(HttpServletRequest headers, @Valid @RequestBody(required = false) CustomerEntity customer) {
        return customerRestTemplateService.save(headers, customer);
    }

    /*Read One - By Web Client*/
    @GetMapping(path = "/api/customers/web_client/{customer_id}")
    public Mono<Object> readByWebClient(HttpServletRequest headers, @PathVariable("customer_id") String customer_id) {
        return customerWebClientService.findById(headers, customer_id);
    }

    /*Read One - By Rest Template*/
    @GetMapping(path = "/api/customers/rest_template/{customer_id}")
    public ResponseEntity<?> readByRestTemplate(HttpServletRequest headers, @PathVariable("customer_id") String customer_id) {
        return customerRestTemplateService.findById(headers, customer_id);
    }

    /*Read All - By Web Client*/
    @GetMapping(path = "/api/customers/web_client")
    public Flux<Object> readByWebClient(HttpServletRequest headers) {
        return customerWebClientService.findAll(headers);
    }

    /*Read All - By Rest Template*/
    @GetMapping(path = "/api/customers/rest_template")
    public ResponseEntity<?> readByRestTemplate(HttpServletRequest headers) {
        return customerRestTemplateService.findAll(headers);
    }

//    private final CustomerService customerService;

//    @Autowired
//    public CustomerController(CustomerService customerService) {
//        this.customerService = customerService;
//    }

//    /**
//     * Create Customer
//     */
//    @Operation(summary = "Add new customer")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Customer created successfully", content = {
//                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))
//            }),
//            @ApiResponse(responseCode = "302", description = "Customer name already exists", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "400", description = "Missing body request", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "500", description = "Server error", content = {
//                    @Content(mediaType = "text")
//            })
//    })
//    @PostMapping(path = "/api/customers")
//    public ResponseEntity<?> create(HttpServletRequest headers, @Valid @RequestBody(required = false) CustomerEntity customer) {
//        return customerService.save(headers, customer);
//    }
//
//    /**
//     * Read All Customers
//     */
//    @Operation(summary = "Get all customers")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "OK, read all customers", content = {
//                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))
//            }),
//            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "404", description = "Customers not found", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "500", description = "Server error", content = {
//                    @Content(mediaType = "text")
//            })
//    })
//    @GetMapping(path = "/api/customers")
//    public ResponseEntity<?> readAll(HttpServletRequest headers) {
//        return customerService.findAll(headers);
//    }
//
//    /**
//     * Read Customer
//     */
//    @Operation(summary = "Get customer by id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "OK, read one customer", content = {
//                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))
//            }),
//            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "404", description = "Customer not found", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "500", description = "Server error", content = {
//                    @Content(mediaType = "text")
//            })
//    })
//    @GetMapping(path = "/api/customers/{customer_id}")
//    public ResponseEntity<?> read(HttpServletRequest headers, @PathVariable("customer_id") String customer_id) {
//        return customerService.findById(headers, customer_id);
//    }
//
//    /**
//     * Update Customer
//     */
//    @Operation(summary = "Update an customer")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "OK, Customer updated", content = {
//                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))
//            }),
//            @ApiResponse(responseCode = "400", description = "Missing body request", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "404", description = "Customer not found", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "406", description = "Update is not correct, because it should be total data update", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "500", description = "Server error", content = {
//                    @Content(mediaType = "text")
//            })
//    })
//    @PutMapping (value = "/api/customers/{customer_id}")
//    public ResponseEntity<?> update(
//            HttpServletRequest headers,
//            @PathVariable("customer_id") String customer_id,
//            @Valid @RequestBody(required = false) JSONObject customer_data
//    ) {
//        return customerService.updateCustomer(headers, customer_id, customer_data);
//    }
//
//    /**
//     * Delete Customer
//     */
//    @Operation(summary = "Delete an customer by id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "OK, customer deleted", content = {
//                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))
//            }),
//            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "404", description = "Customer not found", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "500", description = "Server error", content = {
//                    @Content(mediaType = "text")
//            })
//    })
//    @DeleteMapping (path = "/api/customers/{customer_id}")
//    public ResponseEntity<?> delete(HttpServletRequest headers, @PathVariable("customer_id") String customer_id) {
//        return customerService.deleteCustomer(headers, customer_id);
//    }
//
//    /**
//     * Patch Customer
//     */
//    @Operation(summary = "Fix/Patch an customer")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "OK, Customer patched", content = {
//                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))
//            }),
//            @ApiResponse(responseCode = "400", description = "Missing body request", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "404", description = "Customer not found", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "406", description = "Patch is not correct, because it should be partial update", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "500", description = "Server error", content = {
//                    @Content(mediaType = "text")
//            })
//    })
//    @PatchMapping (path = "/api/customers/{customer_id}")
//    public ResponseEntity<?> patch(
//            HttpServletRequest headers,
//            @PathVariable("customer_id") String customer_id,
//            @Valid @RequestBody(required = false) JSONObject customer_data
//    ) {
//        return customerService.patchCustomer(headers, customer_id, customer_data);
//    }
//
//    /**
//     * Reject Request (not POST|GET)
//     */
//    @Operation(summary = "Reject the Wrong request - not POST|GET")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "405", description = "Wrong Request", content = {
//                    @Content(mediaType = "text")
//            })
//    })
//    @RequestMapping(
//            value = {"/", "/api", "/api/customers"},
//            method = {
//                    RequestMethod.PUT,
//                    RequestMethod.PATCH,
//                    RequestMethod.DELETE,
//                    RequestMethod.HEAD,
//                    RequestMethod.OPTIONS
//            }
//    )
//    public ResponseEntity<?> reject() {
//        return customerService.requestReject();
//    }
//
//    /**
//     * Reject Request (only POST|GET)
//     */
//    @Operation(summary = "Reject the Wrong request to POST|GET")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
//                    @Content(mediaType = "text")
//            }),
//            @ApiResponse(responseCode = "405", description = "Wrong Request POST|GET", content = {
//                    @Content(mediaType = "text")
//            })
//    })
//    @RequestMapping(
//            value = {"/", "/api"},
//            method = {
//                    RequestMethod.POST,
//                    RequestMethod.GET
//            }
//    )
//    public ResponseEntity<?> rejectGet() {
//        return customerService.requestReject();
//    }

}
