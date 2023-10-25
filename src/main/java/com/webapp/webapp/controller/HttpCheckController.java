package com.webapp.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.webapp.model.HttpCheck;
import com.webapp.webapp.service.HttpCheckService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
public class HttpCheckController {

	Logger logger = LoggerFactory.getLogger(HttpCheckController.class);

	@Autowired
	HttpCheckService httpCheckService;

	@GetMapping("/healthz")
	public ResponseEntity<Object> checkHealthz() {
		logger.info("ping for app health check");
		return new ResponseEntity<>(HttpStatusCode.valueOf(200));
	}

	@PostMapping("v1/http-check")
	public ResponseEntity<Object> createHttpCheck(@RequestBody HttpCheck httpCheck) {
		return httpCheckService.createHttpCheck(httpCheck);

	}

	@GetMapping("v1/http-check")
	public ResponseEntity<Object> getAllHttpCheck() {
		return httpCheckService.getAllHttpCheck();

	}

	@GetMapping("v1/http-check/{id}")
	public ResponseEntity<Object> getHttpCheckById(@PathVariable String id) {
		return httpCheckService.getHttpCheckById(id);

	}

	@PutMapping("v1/http-check/{id}")
	public ResponseEntity<Object> updateHttpCheckById(@PathVariable String id, @RequestBody HttpCheck httpCheck) {
		return httpCheckService.updateHttpCheckById(id, httpCheck);

	}

	@DeleteMapping("v1/http-check/{id}")
	public ResponseEntity<Object> deleteHttpCheckById(@PathVariable String id) {
		return httpCheckService.deleteHttpCheckById(id);

	}

	@GetMapping("v1/actuator/health/liveness")
	public String liveness() {
		return "Liveness probe successful";
	}

	@GetMapping("v1/actuator/health/readiness")
	public String readiness() {
		return "Readiness probe successful";
	}

}
