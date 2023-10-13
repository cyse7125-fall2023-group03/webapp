package com.neu.cloud.cloudapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping("/healthz")
	public ResponseEntity<Object> checkHealthz() {
		logger.info("ping for app health check");
		return new ResponseEntity<>(HttpStatusCode.valueOf(200));
	}


}
