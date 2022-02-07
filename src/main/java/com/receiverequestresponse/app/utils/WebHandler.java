package com.receiverequestresponse.app.utils;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class WebHandler {

    protected final JSONObject jsonResponse;

    @Autowired
    protected WebHandler() {
        this.jsonResponse = new JSONObject();
    }

    private JSONObject defJsonResponse(Integer status, String message, String origin) {
        jsonResponse.clear();
        jsonResponse.appendField("status", "error");
        jsonResponse.appendField("code", status);
        if (!origin.equals("vendor")) {
            jsonResponse.appendField("message", message);
        } else {
            jsonResponse.appendField("data", Helpers.stringToJson(message));
        }
        jsonResponse.appendField("origin", origin);
        return jsonResponse;
    }

    private JSONObject setConsumerError(HttpStatus status, String message) {
        return defJsonResponse(status.value(), message, "consumer");
    }

    private JSONObject setVendorError(Integer status, String message) {
        return defJsonResponse(status, message, "vendor");
    }

    private JSONObject setApplicationError(Integer status, String message) {
        return defJsonResponse(status, message, "application");
    }

    protected ResponseEntity<?> consumerError(HttpStatus status, String data) {
        return ResponseEntity.status(status).body(setConsumerError(status, data));
    }

    protected ResponseEntity<?> vendorError(Integer status, String data) {
        return ResponseEntity.status(status).body(setVendorError(status, data));
    }

    protected ResponseEntity<?> applicationError(Integer status, String data) {
        return ResponseEntity.status(status).body(setApplicationError(status, data));
    }

    protected Flux<Object> consumerErrorFlux(HttpStatus status, String data) {
        return Flux.just(setConsumerError(status, data));
    }

    protected Mono<Object> consumerErrorMono(HttpStatus status, String data) {
        return Mono.just(setConsumerError(status, data));
    }

}
