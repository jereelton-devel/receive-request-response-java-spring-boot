package com.receiverequestresponse.app.utils;

import net.minidev.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

public abstract class HttpHandler {

    protected final JSONObject jsonResponse;

    @Autowired
    protected HttpHandler() {
        this.jsonResponse = new JSONObject();
    }

    protected HttpHeaders httpRequestHeaders() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBasicAuth(Helpers
                .loadProps()
                .getProperty("application.basic-authorization-remote")
                .replaceFirst("Basic ", ""));

        return httpHeaders;

    }

    protected HttpComponentsClientHttpRequestFactory httpClientFactory() {
        HttpClient httpClient = HttpClientBuilder.create().build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    private JSONObject defaultBodyJsonResponseError(Integer status, String message, String origin) {
        jsonResponse.clear();
        jsonResponse.appendField("status", "error");
        jsonResponse.appendField("code", status);
        JSONObject tryJson = Helpers.stringToJson(message);
        if (!origin.equals("vendor")) {
            if (tryJson.size() == 0) {
                jsonResponse.appendField("response", message);
            } else {
                jsonResponse.appendField("response", tryJson);
            }
        } else {
            if (tryJson.size() == 0) {
                jsonResponse.appendField("vendorResponse", message);
            } else {
                jsonResponse.appendField("vendorResponse", Helpers.stringToJson(message));
            }
        }
        jsonResponse.appendField("origin", origin);
        return jsonResponse;
    }

    private JSONObject setResponseServerError(Integer status, String message) {
        return defaultBodyJsonResponseError(status, message, "vendor");
    }

    private JSONObject setResponseClientError(Integer status, String message) {
        return defaultBodyJsonResponseError(status, message, "application");
    }

    private JSONObject setAppException(Integer status, String message) {
        return defaultBodyJsonResponseError(status, message, "consumer");
    }

    protected ResponseEntity<?> httpRequestServerException(HttpServerErrorException except) {
        int statusCodeRaw = except.getRawStatusCode();
        String statusCode = String.valueOf(except.getStatusCode());
        String strMessage = except.getResponseBodyAsString();
        JSONObject jsonMessage = Helpers.stringToJson(strMessage);

        return ResponseEntity.status(HttpStatus.valueOf(statusCodeRaw))
                .body(setResponseServerError(statusCodeRaw, strMessage));

    }

    protected ResponseEntity<?> httpRequestClientException(HttpClientErrorException except) {
        int statusCodeRaw = except.getRawStatusCode();
        String statusCode = String.valueOf(except.getStatusCode());
        String strMessage = except.getResponseBodyAsString();
        JSONObject jsonMessage = Helpers.stringToJson(strMessage);

        return ResponseEntity.status(HttpStatus.valueOf(statusCodeRaw))
                .body(setResponseClientError(statusCodeRaw, strMessage));

    }

    protected ResponseEntity<?> appException(Exception except) {
        return ResponseEntity.status(500).body(setAppException(500, except.getMessage()));
    }

}
