package com.webapp.webapp.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.webapp.webapp.model.HttpCheck;
import com.webapp.webapp.repo.HttpCheckRepository;

@Service
public class HttpCheckService {

	Logger logger = LoggerFactory.getLogger(HttpCheckService.class);

	@Autowired
	private HttpCheckRepository httpCheckRepository;

	public ResponseEntity<Object> createHttpCheck(HttpCheck httpCheck) {
		try {
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
			return new ResponseEntity<>(httpCheck, HttpStatusCode.valueOf(201));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
		}
	}

	public ResponseEntity<Object> getAllHttpCheck() {
		try {
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
			return new ResponseEntity<>(HttpStatusCode.valueOf(204));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
		}
	}

}
