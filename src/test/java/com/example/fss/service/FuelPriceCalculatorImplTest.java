package com.example.fss.service;

import com.example.fss.domain.ArrivalInfo;
import com.example.fss.domain.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FuelPriceCalculatorImplTest {

    @Mock
    FuelPriceClient fuelPriceClient;

    @InjectMocks
    @Spy
    FuelPriceCalculatorImpl fuelPriceCalculatorImpl;

    /**
     * When lid is true for the first time
     */
    @Test
    public void test_processMessage_case1() {
        Car car1 = new Car("Bangalore", true);

        when(fuelPriceClient.getFuelPrice(car1.getCity())).thenReturn(70.0);
        fuelPriceCalculatorImpl.processMessage(car1);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ArrivalInfo arrivalInfo = new ArrivalInfo(70.0, timestamp.getTime() / 1000);

        assertEquals(true, fuelPriceCalculatorImpl.carArrivalMap.containsKey(car1.getCity()));
        assertEquals(arrivalInfo, fuelPriceCalculatorImpl.carArrivalMap.get(car1.getCity()));
    }

    /**
     * When lid is closed without even being opened once
     */
    @Test
    public void test_processMessage_case2() {
        Car car1 = new Car("Bangalore", false);
        fuelPriceCalculatorImpl.processMessage(car1);
        assertEquals(true, fuelPriceCalculatorImpl.carArrivalMap.isEmpty());
    }

    /**
     * When lid is closed after being open, calculate the bill
     */
    @Test
    public void test_processMessage_case3() {
        Car car1 = new Car("Bangalore", true);

        when(fuelPriceClient.getFuelPrice(car1.getCity())).thenReturn(70.0);
        fuelPriceCalculatorImpl.processMessage(car1);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ArrivalInfo arrivalInfo = new ArrivalInfo(70.0, timestamp.getTime() / 1000);

        Car car2 = new Car("Bangalore", false);
        fuelPriceCalculatorImpl.processMessage(car2);
        verify(fuelPriceCalculatorImpl).computeTheFuelPrice(car2.getCity(), arrivalInfo);
    }

    /**
     * Compute bill correctly
     */
    @Test
    public void test_computeTheFuelPrice() {
        Car car1 = new Car("Bangalore", true);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long arrivalTime = (timestamp.getTime() / 1000) - 120; // 2 minutes before the current time
        ArrivalInfo arrivalInfo = new ArrivalInfo(70.0, arrivalTime);
        fuelPriceCalculatorImpl.computeTheFuelPrice(car1.getCity(), arrivalInfo);

        Double expectedPrice = 0.0;
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        Long currentTimestamp = timestamp1.getTime() / 1000; //convert to seconds
        expectedPrice = arrivalInfo.getFuelPrice() * ((currentTimestamp - arrivalTime) / 30);
        Double expectedFuel = expectedPrice / arrivalInfo.getFuelPrice();

        verify(fuelPriceCalculatorImpl).generateBill(car1.getCity(), 70.0, expectedPrice, expectedFuel);
    }
}
