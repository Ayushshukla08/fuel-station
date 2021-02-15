package com.example.fss.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Credentials for secure communication with other apps
 */
@ConfigurationProperties(prefix = "fuelstation")
@Data
@Configuration
public class CredentialStore {

    /**
     * Store the username for basic auth
     */
    private String userName;
    /**
     * Store the password for basic auth
     */
    private String password;

}
