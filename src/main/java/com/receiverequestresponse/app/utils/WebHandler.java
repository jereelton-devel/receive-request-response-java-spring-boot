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
import org.springframework.web.reactive.function.client.WebClientResponseException;

public abstract class WebHandler {

    protected final JSONObject jsonResponse;

    @Autowired
    protected WebHandler() {
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

    protected JSONObject setResponseClientError(String strMessage, JSONObject jsonMessage) {
        jsonResponse.clear();
        jsonResponse.appendField("status", "error");
        if (!strMessage.equals("") && jsonMessage.size() == 0) {
            jsonResponse.appendField("responseText", strMessage);
        }
        jsonResponse.appendField("responseJson", jsonMessage);
        jsonResponse.appendField("origin", "application");
        return jsonMessage;
    }

    protected ResponseEntity<?> httpRequestClientException(HttpClientErrorException except) {

        int statusCodeRaw = except.getRawStatusCode();
        String statusCode = String.valueOf(except.getStatusCode());
        String strMessage = except.getResponseBodyAsString();
        JSONObject jsonMessage = Helpers.stringToJson(strMessage);

        return ResponseEntity.status(HttpStatus.valueOf(statusCodeRaw)).body(
                setResponseClientError(strMessage, jsonMessage)
        );

    }

    protected JSONObject setResponseServerError(String strMessage, JSONObject jsonMessage) {
        jsonResponse.clear();
        jsonResponse.appendField("status", "error");
        jsonResponse.appendField("about", "This error is from server");
        if (!strMessage.equals("") && jsonMessage.size() == 0) {
            jsonResponse.appendField("responseText", strMessage);
        }
        jsonResponse.appendField("responseJson", jsonMessage);
        jsonResponse.appendField("origin", "server");
        return jsonMessage;
    }

    protected ResponseEntity<?> webClientResponseException(WebClientResponseException except) {
        int statusCodeRaw = except.getRawStatusCode();
        String statusCode = String.valueOf(except.getStatusCode());
        String strMessage = except.getResponseBodyAsString();
        JSONObject jsonMessage = Helpers.stringToJson(strMessage);

        return ResponseEntity.status(HttpStatus.valueOf(statusCodeRaw)).body(
                setResponseServerError(strMessage, jsonMessage)
        );

    }

    protected JSONObject setAppException(Exception exceptionMessage) {
        jsonResponse.clear();
        jsonResponse.appendField("status", "error");
        jsonResponse.appendField("message", "Occurs an error not expected");
        jsonResponse.appendField("exception", exceptionMessage.toString());
        return jsonResponse;
    }

    protected ResponseEntity<?> appException(Exception except) {
        return ResponseEntity.status(HttpStatus.valueOf(500)).body(setAppException(except));

    }

    protected void setResponseSuccess(Object message, Object data) {
        jsonResponse.clear();
        jsonResponse.appendField("data", data);
        jsonResponse.appendField("message", message);
        jsonResponse.appendField("status", "success");
    }

}
