package com.receiverequestresponse.app.utils;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
                .extractProps()
                .getProperty("application.basic-authorization-remote")
                .replaceFirst("Basic ", ""));

        return httpHeaders;

    }

    protected void setResponseClientError(String strMessage, JSONObject jsonMessage) {
        jsonResponse.appendField("status", "error");
        if (!strMessage.equals("")) {
            jsonResponse.appendField("responseText", strMessage);
        }
        jsonResponse.appendField("responseJson", jsonMessage);
        jsonResponse.appendField("origin", "application");
    }

    protected ResponseEntity<?> httpRequestClientException(HttpClientErrorException except) {

        int statusCodeRaw = except.getRawStatusCode();
        String statusCode = String.valueOf(except.getStatusCode());
        String strMessage = except.getResponseBodyAsString();
        JSONObject jsonMessage = Helpers.stringToJson(strMessage);

        setResponseClientError(strMessage, jsonMessage);

        return ResponseEntity.status(HttpStatus.valueOf(statusCodeRaw)).body(jsonResponse);

    }

    protected void setResponseServerError(String strMessage, JSONObject jsonMessage) {
        jsonResponse.appendField("status", "error");
        jsonResponse.appendField("about", "This error is from server");
        if (!strMessage.equals("")) {
            jsonResponse.appendField("responseText", strMessage);
        }
        jsonResponse.appendField("responseJson", jsonMessage);
        jsonResponse.appendField("origin", "server");
    }

    protected ResponseEntity<?> httpRequestServerException(HttpServerErrorException except) {
        int statusCodeRaw = except.getRawStatusCode();
        String statusCode = String.valueOf(except.getStatusCode());
        String strMessage = except.getResponseBodyAsString();
        JSONObject jsonMessage = Helpers.stringToJson(strMessage);

        setResponseServerError(strMessage, jsonMessage);

        return ResponseEntity.status(HttpStatus.valueOf(statusCodeRaw)).body(jsonResponse);

    }

    protected void setResponseSuccess(Object message, Object data) {
        jsonResponse.appendField("data", data);
        jsonResponse.appendField("message", message);
        jsonResponse.appendField("status", "success");
    }

}
