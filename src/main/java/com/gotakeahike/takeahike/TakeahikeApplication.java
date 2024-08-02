package com.gotakeahike.takeahike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TakeahikeApplication {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(TakeahikeApplication.class);
		application.setAddCommandLineProperties(false);
		application.run(args);
	}

}