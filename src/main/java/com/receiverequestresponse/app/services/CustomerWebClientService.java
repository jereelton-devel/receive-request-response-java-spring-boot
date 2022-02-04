package com.receiverequestresponse.app.services;

import com.receiverequestresponse.app.entities.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;

@Service
public class CustomerWebClientService {

    @Autowired
    WebClient webClient;

    public Mono<Object> findById(HttpServletRequest headers, String customer_id) {

        return webClient
                .get()
                .uri("/api/customers/"+customer_id)
                .retrieve()
                .bodyToMono(Object.class)
                .timeout(Duration.ofSeconds(10));

    }

    public Flux<Object> findAll(HttpServletRequest headers) {

        return webClient
                .get()
                .uri("/api/customers")
                .retrieve()
                .bodyToFlux(Object.class)
                .timeout(Duration.ofSeconds(10));

    }

    public Mono<Object> save(HttpServletRequest headers, CustomerEntity customer) {

        return webClient
                .post()
                .uri("/api/customers/")
                .body(Mono.just(customer), CustomerEntity.class)
                .retrieve()
                .bodyToMono(Object.class)
                .timeout(Duration.ofSeconds(10));

    }
}
