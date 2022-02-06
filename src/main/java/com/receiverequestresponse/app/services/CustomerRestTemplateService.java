package com.receiverequestresponse.app.services;

import com.receiverequestresponse.app.entities.CustomerEntity;
import com.receiverequestresponse.app.utils.Helpers;
import com.receiverequestresponse.app.utils.HttpHandler;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Service
public class CustomerRestTemplateService extends HttpHandler {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl;
    private final String baseUri;

    @Autowired
    public CustomerRestTemplateService() {
        baseUrl = Helpers.loadProps().getProperty("application.base-url-remote");
        baseUri = Helpers.loadProps().getProperty("application.base-uri-remote");
    }

    private ResponseEntity<?> dispatch(String method, String customer_id, HttpEntity<?> httpEntity) {
        try {

            if (!customer_id.equals("")) {
                customer_id = customer_id.replaceFirst("/", "");
                customer_id = "/"+customer_id;
            }

            String url = baseUrl+baseUri+customer_id;

            switch (method) {
                case "POST":
                    return restTemplate.postForEntity(url, httpEntity, Object.class);
                case "GET":
                    return restTemplate.exchange(url, HttpMethod.GET, httpEntity, Object.class);
                case "PUT":
                    return restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Object.class);
                case "DELETE":
                    return restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, Object.class);
                case "PATCH":
                    restTemplate.setRequestFactory(httpClientFactory());
                    return restTemplate.exchange(url, HttpMethod.PATCH, httpEntity, Object.class);
            }

        } catch (HttpServerErrorException sex) {
            return httpRequestServerException(sex);
        } catch (HttpClientErrorException cex) {
            return httpRequestClientException(cex);
        } catch (Exception gex) {
            return appException(gex);
        }

        return ResponseEntity.internalServerError().build();
    }

    private ResponseEntity<?> search(String customer_id) {
        HttpEntity<String> httpEntity = new HttpEntity<>(httpRequestHeaders());
        return dispatch("GET", customer_id, httpEntity);
    }

    public ResponseEntity<?> findById(HttpServletRequest headers, String customer_id) {
        if (!AccessControlService.authorization(headers)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        return search(customer_id);
    }

    public ResponseEntity<?> findAll(HttpServletRequest headers) {
        if (!AccessControlService.authorization(headers)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        return search("");
    }

    public Object save(HttpServletRequest headers, CustomerEntity customer) {
        if (!AccessControlService.authorization(headers)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        HttpEntity<CustomerEntity> httpEntity = new HttpEntity<>(customer, httpRequestHeaders());
        return dispatch("POST", "", httpEntity);
    }

    public Object update(HttpServletRequest headers, String customer_id, JSONObject customer_data) {
        if (!AccessControlService.authorization(headers)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(customer_data, httpRequestHeaders());
        return dispatch("PUT", customer_id, httpEntity);
    }

    public ResponseEntity<?> delete(HttpServletRequest headers, String customer_id) {
        if (!AccessControlService.authorization(headers)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        HttpEntity<String> httpEntity = new HttpEntity<>(httpRequestHeaders());
        return dispatch("DELETE", customer_id, httpEntity);
    }

    public Object patch(HttpServletRequest headers, String customer_id, JSONObject customer_data) {
        if (!AccessControlService.authorization(headers)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(customer_data, httpRequestHeaders());
        return dispatch("PATCH", customer_id, httpEntity);
    }

}
