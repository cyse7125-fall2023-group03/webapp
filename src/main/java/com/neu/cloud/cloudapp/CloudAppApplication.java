package com.neu.cloud.cloudapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class CloudAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudAppApplication.class, args);
	}

}
