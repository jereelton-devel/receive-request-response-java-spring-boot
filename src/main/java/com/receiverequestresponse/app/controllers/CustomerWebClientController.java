package com.receiverequestresponse.app.controllers;

import com.receiverequestresponse.app.entities.CustomerDTO;
import com.receiverequestresponse.app.entities.CustomerEntity;
import com.receiverequestresponse.app.services.CustomerWebClientService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Tag(name = "Receiver Request Response App Sample - WebClient")
@Schema(implementation = CustomerDTO.class)
@CrossOrigin(origins = "*")
public class CustomerWebClientController {

    private final CustomerWebClientService customerWebClientService;

    @Autowired
    public CustomerWebClientController(CustomerWebClientService customerWebClient) {
        this.customerWebClientService = customerWebClient;
    }

    @PostMapping(path = "/api/customers/web_client")
    public Mono<Object> createByWebClient(HttpServletRequest headers, @Valid @RequestBody(required = false) CustomerEntity customer) {
        return customerWebClientService.save(headers, customer);
    }

    @GetMapping(path = "/api/customers/web_client/{customer_id}")
    public Mono<Object> readByWebClient(HttpServletRequest headers, @PathVariable("customer_id") String customer_id) {
        return customerWebClientService.findById(headers, customer_id);
    }

    @GetMapping(path = "/api/customers/web_client")
    public Flux<Object> readByWebClient(HttpServletRequest headers) {
        return customerWebClientService.findAll(headers);
    }

    @PutMapping(path = "/api/customers/web_client/{customer_id}")
    public Mono<Object> updateByWebClient(
            HttpServletRequest headers,
            @PathVariable("customer_id") String customer_id,
            @Valid @RequestBody(required = false) JSONObject customer_data
    ) {
        return customerWebClientService.update(headers, customer_id, customer_data);
    }

    @DeleteMapping(path = "/api/customers/web_client/{customer_id}")
    public Mono<Object> deleteByWebClient(HttpServletRequest headers, @PathVariable("customer_id") String customer_id) {
        return customerWebClientService.deleteById(headers, customer_id);
    }

    @PatchMapping(path = "/api/customers/web_client/{customer_id}")
    public Mono<Object> patchByWebClient(
            HttpServletRequest headers,
            @PathVariable("customer_id") String customer_id,
            @Valid @RequestBody(required = false) JSONObject customer_data
    ) {
        return customerWebClientService.patch(headers, customer_id, customer_data);
    }

}
