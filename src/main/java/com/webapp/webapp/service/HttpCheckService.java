package com.webapp.webapp.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.webapp.webapp.model.HttpCheck;
import com.webapp.webapp.repo.HttpCheckRepository;

import java.net.URI;

@Service
public class HttpCheckService {

	Logger logger = LoggerFactory.getLogger(HttpCheckService.class);

	@Autowired
	private HttpCheckRepository httpCheckRepository;

	@Autowired
	CustomResourceService cResourceService;

	@Autowired()
	RestTemplate restTemplate;

	public ResponseEntity<Object> createHttpCheck(HttpCheck httpCheck) {
		try {
			getDataFromExternalService(httpCheck);
			httpCheck.setUri(httpCheck.getUri());
			httpCheck.setIs_paused(httpCheck.isIs_paused());
			httpCheck.setNum_retries(httpCheck.getNum_retries());
			httpCheck.setName(httpCheck.getName());
			httpCheck.setUptime_sla(httpCheck.getUptime_sla());
			httpCheck.setResponse_time_sla(httpCheck.getResponse_time_sla());
			httpCheck.setUse_ssl(httpCheck.isUse_ssl());
			httpCheck.setResponse_status_code(httpCheck.getResponse_status_code());
			httpCheck.setCheck_interval_in_seconds(httpCheck.getCheck_interval_in_seconds());
			httpCheck.setCheck_created(OffsetDateTime.now(ZoneOffset.UTC).toString());
			httpCheck.setCheck_updated(OffsetDateTime.now(ZoneOffset.UTC).toString());

			httpCheckRepository.save(httpCheck);
			cResourceService.applyCRDAndCreateCustomResource(httpCheck);
			return new ResponseEntity<>(httpCheck, HttpStatusCode.valueOf(201));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
		}
	}

	@Async
	public void getDataFromExternalService(HttpCheck httpCheck) {
		long startTime = System.currentTimeMillis();
		URI uri = UriComponentsBuilder.fromUriString(httpCheck.getUri()).build().toUri();

		ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		int responseTimeInt = (int) responseTime;

		HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
		int codeValue = statusCode.value();

		System.out.println("Response Time: " + responseTime + " ms");
		httpCheck.setResponse_time_sla(responseTimeInt);
		System.out.println("Status Code: " + codeValue);
		httpCheck.setResponse_status_code(codeValue);
	}

	public ResponseEntity<Object> getAllHttpCheck() {
		try {
			cResourceService.getAllResources();
			return new ResponseEntity<>(httpCheckRepository.findAll(), HttpStatusCode.valueOf(200));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
		}
	}

	public ResponseEntity<Object> getHttpCheckById(String id) {
		try {
			HttpCheck httpCheck = httpCheckRepository.findById(id).get();
			if (httpCheck == null)
				return new ResponseEntity<>(HttpStatusCode.valueOf(404));
			return new ResponseEntity<>(httpCheck, HttpStatusCode.valueOf(200));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
		}
	}

	public ResponseEntity<Object> updateHttpCheckById(String id, HttpCheck httpCheckBody) {
		try {
			HttpCheck httpCheck = httpCheckRepository.findById(id).get();
			if (httpCheck == null)
				return new ResponseEntity<>(HttpStatusCode.valueOf(404));
			getDataFromExternalService(httpCheck);
			httpCheck.setName(httpCheckBody.getName());
			httpCheck.setUri(httpCheckBody.getUri());
			httpCheck.setIs_paused(httpCheckBody.isIs_paused());
			httpCheck.setNum_retries(httpCheckBody.getNum_retries());
			httpCheck.setUptime_sla(httpCheckBody.getUptime_sla());
			httpCheck.setResponse_time_sla(httpCheckBody.getResponse_time_sla());
			httpCheck.setUse_ssl(httpCheckBody.isUse_ssl());
			httpCheck.setResponse_status_code(httpCheckBody.getResponse_status_code());
			httpCheck.setCheck_interval_in_seconds(httpCheckBody.getCheck_interval_in_seconds());
			httpCheck.setCheck_updated(OffsetDateTime.now(ZoneOffset.UTC).toString());

			httpCheckRepository.save(httpCheck);
			cResourceService.updateCustomResource(httpCheck, id);
			return new ResponseEntity<>(HttpStatusCode.valueOf(204));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatusCode.valueOf(400));
		}
	}

	public ResponseEntity<Object> deleteHttpCheckById(String id) {
		try {
			HttpCheck httpCheck = httpCheckRepository.findById(id).get();
			if (httpCheck == null)
				return new ResponseEntity<>(HttpStatusCode.valueOf(404));
			httpCheckRepository.deleteById(id);
			cResourceService.deleteCr(id);
			return new ResponseEntity<>(HttpStatusCode.valueOf(204));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
		}
	}

}
