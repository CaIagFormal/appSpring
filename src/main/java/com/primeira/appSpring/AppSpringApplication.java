package com.primeira.appSpring;

import com.primeira.appSpring.controller.C_Arduino;
import com.primeira.appSpring.controller.C_EmuArduino;
import com.primeira.appSpring.controller.C_SerialArduino;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AppSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppSpringApplication.class, args);
		C_Arduino arduino = new C_EmuArduino();

		arduino.initialize();
		if (arduino.isInit_success()) {
			arduino.run();
		}
	}

}
