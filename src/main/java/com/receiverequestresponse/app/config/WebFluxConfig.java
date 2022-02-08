package com.receiverequestresponse.app.config;

import com.receiverequestresponse.app.utils.Helpers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

    @Bean
    public WebClient getWebClient() {

        String baseUrlProp = Helpers.loadProps().getProperty("application.base-url-remote");
        String basicAuth = Helpers.loadProps().getProperty("application.basic-authorization-remote");

        return WebClient.builder()
                .baseUrl(baseUrlProp)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, basicAuth)
                .build();

    }

    private ExchangeFilterFunction logResponseStatus() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            System.out.println("Response:  " + clientResponse);
            System.out.println("Response Status:  " + clientResponse.statusCode());
            System.out.println("Response Raw Status:  " + clientResponse.rawStatusCode());
            System.out.println("Response Headers:  " + clientResponse.headers().asHttpHeaders());
            System.out.println("Response Reason:  " + clientResponse.statusCode().getReasonPhrase());
            return Mono.just(clientResponse);
        });
    }

}
