package com.example.fss.domain;
import lombok.Data;

import java.io.Serializable;

@Data
public class Car implements Serializable {
    /**
     * city name where car arrived
     */
    private String city;

    /**
     * fuellid open/closed
     */
    private Boolean fuellid;

    public Car(String city, Boolean fuellid) {
        this.city = city;
        this.fuellid = fuellid;
    }

    @Override
    public String toString() {
        return "City{" +
                "city='" + city + '\'' +
                ", fuellid=" + fuellid +
                '}';
    }
}
