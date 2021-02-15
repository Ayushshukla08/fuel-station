package com.example.fss.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfiguration {

    @Autowired
    CredentialStore credentialStore;
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
        interceptors.add(new HeaderRequestInterceptor(credentialStore));
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
