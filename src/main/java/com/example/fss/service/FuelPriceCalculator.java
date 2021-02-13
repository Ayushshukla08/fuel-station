package com.example.fss.service;

import com.example.fss.domain.ArrivalInfo;
import com.example.fss.domain.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Calculate the fuel price at the fuel station
 */
@Service
@Slf4j
public class FuelPriceCalculator {

    /**
     * Map which stores Car info (Key - City name, Value - FuelStation Object)
     */
    Map<String, ArrivalInfo> carArrivalMap = new HashMap<String, ArrivalInfo>();
    @Autowired
    private FuelPriceClient fuelPriceClient;

    /**
     * When the fuellid is open, the filling of fuel at the fuel station will start. Stop filling when lid is closed.
     * ASSUMPTIONS-1 :: Only one fuel machine is available which means
     * FUEL STATION IN ONE CITY CAN SERVICE ONLY ONE CAR AT A TIME
     * ASSUMPTION-2 :: When the fuellid is closed that is when the tank is full and we give the bill reading to car.
     *
     * @param car
     */
    public void processMessage(Car car) { // Interface
        log.info("message consumed now :: {}", car.toString());

        //If the lid is already open, continue filling till it is closed
        if (car.getFuellid() && !carArrivalMap.containsKey(car.getCity())) {

            Double price = fuelPriceClient.getFuelPrice(car.getCity());
            log.info("Price for city {} is {} ", car.getCity(), price);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            ArrivalInfo arrivalInfo = new ArrivalInfo(price, timestamp.getTime() / 1000);

            carArrivalMap.put(car.getCity(), arrivalInfo);
            // If lid is closed that means tank is full and we can calculate the price now
        } else if (!car.getFuellid() && carArrivalMap.containsKey(car.getCity())) {
            computeTheFuelPrice(car.getCity(), carArrivalMap.get(car.getCity()));
            carArrivalMap.remove(car.getCity());
        } else {
            log.info("Lid is closed and was not opened previously. Wait till the lid is open to start filling.");
        }

    }

    /**
     * Compute the fuel price and total fuel filled based on the Price for that city
     *
     * @param city        name
     * @param arrivalInfo fuelstation object
     */
    public void computeTheFuelPrice(String city, ArrivalInfo arrivalInfo) {
        Double price = 0.0;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long currentTimestamp = timestamp.getTime() / 1000; //convert to seconds
        price = arrivalInfo.getFuelPrice() * ((currentTimestamp - arrivalInfo.getArrivalTime()) / 30);
        Double fuel = price / arrivalInfo.getFuelPrice();
        generateBill(city, arrivalInfo.getFuelPrice(), price, fuel);
    }

    /**
     * Bill generator
     *
     * @param city          name
     * @param pricePerLitre fuel price per litre
     * @param price         total bill
     * @param fuelConsumed  total fuel consumed
     */
    public void generateBill(String city, Double pricePerLitre, Double price, Double fuelConsumed) {
        log.info("******************");
        log.info("GENERATING BILL");
        log.info("CITY: {}", city);
        log.info("NO OF LITRES FILLED: {}", fuelConsumed);
        log.info("PRICE PER LITRE: {}", pricePerLitre);
        log.info("TOTAL PRICE: {}", price);
        log.info("*****************");
    }
}
