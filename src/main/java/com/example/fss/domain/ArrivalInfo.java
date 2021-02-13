package com.example.fss.domain;

import lombok.Data;

/**
 * This class represents the arrival info of the car
 *
 */
@Data
public class ArrivalInfo {
    /**
     * fuel price at time of arrival
     */
    private Double fuelPrice;

    /**
     * arrival time of car. This value is in epoch
     */
    private Long arrivalTime;


    public ArrivalInfo(Double fuelPrice, Long arrivalTime) {
        this.fuelPrice = fuelPrice;
        this.arrivalTime = arrivalTime;
    }
}
