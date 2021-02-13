package com.example.fss.configuration;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * This Interceptor adds basic auth headers to all the rest calls between services.
 */
public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().set("Authorization", "Basic " + getBase64Creds());
        return execution.execute(request, body);
    }

    /**
     * Create Authorization header user/password value.
     */
    public String getBase64Creds() {
        //Hardcoding Credentials here for simplicity. But For a production app, credentials should be injected as Environment variables
        String auth = "ayush" + ":" + "ayush";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
        return new String(encodedAuth);
    }
}
