package com.fglapi.fglapi;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FglapiApplication {

	public static void main(final String[] args) {
		SpringApplication.run(FglapiApplication.class, args);
		DatabaseController db = new DatabaseController();
		db._dbTest();
	}

}
