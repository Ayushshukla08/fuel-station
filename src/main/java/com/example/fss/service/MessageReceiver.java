package com.example.fss.service;

import com.example.fss.domain.Car;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

/**
 * Class to consume RabbitMQ message
 */
@Service
@Slf4j
public class MessageReceiver implements MessageListener {

    @Autowired
    private FuelPriceCalculator fuelPriceCalculatorImpl;

    /**
     * Method to consume RabbitMQ message from Event-Generator service
     *
     * @param message Message received from the RMQ
     */
    @SneakyThrows
    public void onMessage(Message message) {
        log.info("Consuming message :: {}", message.getBody());
        Car car = (Car) getObject(message.getBody());
        fuelPriceCalculatorImpl.processMessage(car);
    }

    /**
     * De-serialize the byte array to object
     *
     * @param byteArr Message body
     * @return Message converted to Object type
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Object getObject(byte[] byteArr) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(byteArr);
        ObjectInput in = new ObjectInputStream(bis);
        return in.readObject();
    }

}
