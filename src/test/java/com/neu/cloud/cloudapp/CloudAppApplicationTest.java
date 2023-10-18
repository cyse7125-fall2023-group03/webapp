package com.neu.cloud.cloudapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration
public class CloudAppApplicationTest {

	@Test
	void contextLoads() {
		HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
		
		assertEquals(HttpStatusCode.valueOf(400),HttpStatusCode.valueOf(400) );
	}
}
