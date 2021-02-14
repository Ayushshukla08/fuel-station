package com.example.fss.service;

import com.example.fss.domain.Car;

public interface FuelPriceCalculator {
    /**
     * Process incoming event
     *
     * @param car
     */
    void processMessage(Car car);
}
