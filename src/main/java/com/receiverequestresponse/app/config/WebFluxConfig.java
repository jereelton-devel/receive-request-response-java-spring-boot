package com.receiverequestresponse.app.config;

import com.receiverequestresponse.app.utils.Helpers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

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
}
