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

    private final CredentialStore credentialStore;

    public HeaderRequestInterceptor(CredentialStore credentialStore) {
        this.credentialStore = credentialStore;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().set("Authorization", "Basic " + getBase64Creds());
        return execution.execute(request, body);
    }

    /**
     * Create Authorization header user/password value.
     */
    public String getBase64Creds() {
        String auth = credentialStore.getUserName() + ":" + credentialStore.getPassword();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
        return new String(encodedAuth);
    }
}
