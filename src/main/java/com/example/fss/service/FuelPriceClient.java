package com.example.fss.service;

import com.example.fss.configuration.CacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * This class communicates with the price-service to
 * get the fuel price for a city
 */
@Service
public class FuelPriceClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Rest call to price-service to get fuel price.
     * This call would be made only when the fuel price is not available in Cache OR
     * when request for same city is made after 24 hours duration
     *
     * @param city name
     * @return fuel price
     */
    @Cacheable(CacheConfig.PRICE_CACHE)
    public Double getFuelPrice(String city) {
        String fooResourceUrl
                = "http://localhost:9000/api/getPrice/" + city;
        ResponseEntity<Double> response
                = restTemplate.getForEntity(fooResourceUrl, Double.class);

        return response.getBody();
    }
}
