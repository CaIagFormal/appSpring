package com.primeira.appSpring;

//import com.primeira.appSpring.controller.ArduinoSerialCommunication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AppSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppSpringApplication.class, args);
		//ArduinoSerialCommunication arduino = new ArduinoSerialCommunication();
		//arduino.initialize();
	}

}
