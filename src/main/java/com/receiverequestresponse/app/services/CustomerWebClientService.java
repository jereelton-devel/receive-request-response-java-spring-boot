package com.receiverequestresponse.app.services;

import com.receiverequestresponse.app.entities.CustomerEntity;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;

@Service
public class CustomerWebClientService {

    private static final int LOCAL_TIMER = 10;

    @Autowired
    WebClient webClient;

    public Mono<Object> findById(HttpServletRequest headers, String customer_id) {

        return webClient
                .get()
                .uri("/api/customers/"+customer_id)
                .retrieve()
                .bodyToMono(Object.class)
                .timeout(Duration.ofSeconds(LOCAL_TIMER));

    }

    public Flux<Object> findAll(HttpServletRequest headers) {

        return webClient
                .get()
                .uri("/api/customers")
                .retrieve()
                .bodyToFlux(Object.class)
                .timeout(Duration.ofSeconds(LOCAL_TIMER));

    }

    public Mono<Object> save(HttpServletRequest headers, CustomerEntity customer) {

        return webClient
                .post()
                .uri("/api/customers/")
                .body(Mono.just(customer), CustomerEntity.class)
                .retrieve()
                .bodyToMono(Object.class)
                .timeout(Duration.ofSeconds(LOCAL_TIMER));

    }

    public Mono<Object> update(HttpServletRequest headers, String customer_id, JSONObject customer_data) {

        return webClient
                .put()
                .uri("/api/customers/"+customer_id)
                .body(Mono.just(customer_data), CustomerEntity.class)
                .retrieve()
                .bodyToMono(Object.class)
                .timeout(Duration.ofSeconds(LOCAL_TIMER));

    }
}
