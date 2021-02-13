package com.example.fss.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfiguration {

    /**
     * Create a rest template for communication between apps
     * It has an interceptor to add basic auth credentials
     *
     * @return customized rest template for secure communication between apps
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
