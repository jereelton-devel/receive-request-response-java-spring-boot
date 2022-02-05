package com.receiverequestresponse.app.services;

import com.receiverequestresponse.app.entities.CustomerEntity;
import com.receiverequestresponse.app.utils.HttpHandler;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Service
public class CustomerRestTemplateService extends HttpHandler {

    private ResponseEntity<?> search(String url) {

        HttpEntity<String> httpEntity = new HttpEntity<>(httpRequestHeaders());
        RestTemplate restTemplate = new RestTemplate();

        try {
            return restTemplate.exchange(url, HttpMethod.GET, httpEntity, Object.class);
        } catch (HttpServerErrorException ex) {
            return httpRequestServerException(ex);
        }

    }

    public ResponseEntity<?> findById(HttpServletRequest headers, String customer_id) {
        String url = "http://localhost:9009/api/customers/"+customer_id;
        return search(url);
    }

    public ResponseEntity<?> findAll(HttpServletRequest headers) {
        String url = "http://localhost:9009/api/customers";
        return search(url);
    }

    public Object save(HttpServletRequest headers, CustomerEntity customer) {

        String url = "http://localhost:9009/api/customers";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<CustomerEntity> httpEntity = new HttpEntity<>(customer, httpRequestHeaders());

        try {
            return restTemplate.postForEntity(url, httpEntity, Object.class);
        } catch (HttpServerErrorException sex) {
            return httpRequestServerException(sex);
        } catch (HttpClientErrorException cex) {
            return httpRequestClientException(cex);
        } catch (Exception gex) {
            return null;
        }

    }

    public Object update(HttpServletRequest headers, String customer_id, JSONObject customer_data) {

        String url = "http://localhost:9009/api/customers/"+customer_id;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(customer_data, httpRequestHeaders());

        try {
            return restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Object.class);
        } catch (HttpServerErrorException ex) {
            return httpRequestServerException(ex);
        }
    }

}
