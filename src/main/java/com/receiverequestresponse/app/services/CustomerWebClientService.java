package com.receiverequestresponse.app.services;

import com.receiverequestresponse.app.entities.CustomerEntity;
import com.receiverequestresponse.app.utils.Helpers;
import com.receiverequestresponse.app.utils.WebHandler;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;

@Service
public class CustomerWebClientService extends WebHandler {

    private static final int LOCAL_TIMER = 10;
    private final String baseUri;

    @Autowired
    public CustomerWebClientService() {
        baseUri = Helpers.loadProps().getProperty("application.base-uri-remote");
    }

    @Autowired
    WebClient webClient;

    private Flux<Object> retrieveSimpleFlux(WebClient.RequestHeadersSpec<?> wc) {
        return wc
                .retrieve()
                .bodyToFlux(Object.class)
                .timeout(Duration.ofSeconds(LOCAL_TIMER));
    }

    private Mono<Object> retrieveSimpleMono(WebClient.RequestHeadersSpec<?> wc) {
        return wc
                .retrieve()
                .bodyToMono(Object.class)
                .timeout(Duration.ofSeconds(LOCAL_TIMER));
    }

    private Mono<Object> retrieveBodyMono(WebClient.RequestBodySpec wc, Object data) {
        return wc
                .body(Mono.just(data), CustomerEntity.class)
                .retrieve()
                .bodyToMono(Object.class)
                .timeout(Duration.ofSeconds(LOCAL_TIMER));
    }

    public Flux<Object> findAll(HttpServletRequest headers) {
        if (!AccessControlService.authorization(headers)) {
            return consumerErrorFlux(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        WebClient.RequestHeadersSpec<?> wc = webClient.get().uri(baseUri);
        return retrieveSimpleFlux(wc);
    }

    public Mono<Object> findById(HttpServletRequest headers, String customer_id) {
        if (!AccessControlService.authorization(headers)) {
            return consumerErrorMono(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        WebClient.RequestHeadersSpec<?> wc = webClient.get().uri(baseUri + "/" + customer_id);
        return retrieveSimpleMono(wc);
    }

    public Mono<Object> deleteById(HttpServletRequest headers, String customer_id) {
        if (!AccessControlService.authorization(headers)) {
            return consumerErrorMono(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        WebClient.RequestHeadersSpec<?> wc = webClient.delete().uri(baseUri + "/" + customer_id);
        return retrieveSimpleMono(wc);
    }

    public Mono<Object> save(HttpServletRequest headers, CustomerEntity customer) {
        if (!AccessControlService.authorization(headers)) {
            return consumerErrorMono(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        WebClient.RequestBodySpec wc = webClient.post().uri(baseUri);
        return retrieveBodyMono(wc, customer);
    }

    public Mono<Object> update(HttpServletRequest headers, String customer_id, JSONObject customer_data) {
        if (!AccessControlService.authorization(headers)) {
            return consumerErrorMono(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        WebClient.RequestBodySpec wc = webClient.put().uri(baseUri + "/" + customer_id);
        return retrieveBodyMono(wc, customer_data);
    }

    public Mono<Object> patch(HttpServletRequest headers, String customer_id, JSONObject customer_data) {
        if (!AccessControlService.authorization(headers)) {
            return consumerErrorMono(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        WebClient.RequestBodySpec wc = webClient.patch().uri(baseUri + "/" + customer_id);
        return retrieveBodyMono(wc, customer_data);
    }

}
