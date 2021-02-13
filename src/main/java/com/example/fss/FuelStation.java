package com.example.fss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.fss")
@EnableCaching
public class FuelStation {

	public static void main(String[] args) {
		SpringApplication.run(FuelStation.class, args);
	}

}
