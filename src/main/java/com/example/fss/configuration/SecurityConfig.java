package com.example.fss.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${fuelstation.username}")
    private String username;

    @Value("${fuelstation.password}")
    private String password;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**").hasRole("ADMIN")
                .requestMatchers(EndpointRequest.to("info", "health")).permitAll()
                .and()
                .httpBasic();
    }

    @Bean
    @Override
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(adminUser());
    }

    private UserDetails adminUser() {
        return User
                .withUsername(username)
                .password("{noop}" + password)
                .roles("ADMIN")
                .build();
    }
}
