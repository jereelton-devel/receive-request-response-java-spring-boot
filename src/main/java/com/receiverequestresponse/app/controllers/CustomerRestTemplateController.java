package com.receiverequestresponse.app.controllers;

import com.receiverequestresponse.app.entities.CustomerDTO;
import com.receiverequestresponse.app.entities.CustomerEntity;
import com.receiverequestresponse.app.services.CustomerRestTemplateService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Tag(name = "Receiver Request Response App Sample - Rest Template")
@Schema(implementation = CustomerDTO.class)
@CrossOrigin(origins = "*")
public class CustomerRestTemplateController {

    private final CustomerRestTemplateService customerRestTemplateService;

    @Autowired
    public CustomerRestTemplateController(CustomerRestTemplateService customerRestTemplateService) {
        this.customerRestTemplateService = customerRestTemplateService;
    }

    @PostMapping(path = "/api/customers/rest_template")
    public Object createByRestTemplate(HttpServletRequest headers, @Valid @RequestBody(required = false) CustomerEntity customer) {
        return customerRestTemplateService.save(headers, customer);
    }

    @GetMapping(path = "/api/customers/rest_template/{customer_id}")
    public ResponseEntity<?> readByRestTemplate(HttpServletRequest headers, @PathVariable("customer_id") String customer_id) {
        return customerRestTemplateService.findById(headers, customer_id);
    }

    @GetMapping(path = "/api/customers/rest_template")
    public ResponseEntity<?> readByRestTemplate(HttpServletRequest headers) {
        return customerRestTemplateService.findAll(headers);
    }

    @PutMapping(path = "/api/customers/rest_template/{customer_id}")
    public Object updateByRestTemplate(
            HttpServletRequest headers,
            @PathVariable("customer_id") String customer_id,
            @Valid @RequestBody(required = false) JSONObject customer_data) {
        return customerRestTemplateService.update(headers, customer_id, customer_data);
    }

    @DeleteMapping(path = "/api/customers/rest_template/{customer_id}")
    public ResponseEntity<?> deleteByRestTemplate(HttpServletRequest headers, @PathVariable("customer_id") String customer_id) {
        return customerRestTemplateService.delete(headers, customer_id);
    }

    @PatchMapping(path = "/api/customers/rest_template/{customer_id}")
    public Object patchByRestTemplate(
            HttpServletRequest headers,
            @PathVariable("customer_id") String customer_id,
            @Valid @RequestBody(required = false) JSONObject customer_data) {
        return customerRestTemplateService.patch(headers, customer_id, customer_data);
    }

}
