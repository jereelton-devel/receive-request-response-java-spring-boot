package com.receiverequestresponse.app.services;

import com.receiverequestresponse.app.entities.CustomerEntity;
import com.receiverequestresponse.app.utils.Helpers;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Service
public class CustomerRestTemplateService {

    private HttpHeaders requestHeaders() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBasicAuth(Helpers
                .extractProps()
                .getProperty("application.basic-authorization-remote")
                .replaceFirst("Basic ", ""));

        return httpHeaders;

    }

    private ResponseEntity<?> search(String url) {

        HttpEntity<String> httpEntity = new HttpEntity<>(requestHeaders());
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, Object.class);
        //return restTemplate.exchange(url, HttpMethod.GET, httpEntity, CustomerDTO[].class);

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

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBasicAuth(Helpers
                .extractProps()
                .getProperty("application.basic-authorization-remote")
                .replaceFirst("Basic ", ""));

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("id", customer.getId());
        map.add("name", customer.getName());
        map.add("active", customer.getActive());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpHeaders);

        return restTemplate.postForObject(url, request, Object.class);

        //return restTemplate.exchange(url, HttpMethod.POST, request, Object.class);

    }
}
