package com.yqz.websolution.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yqz.websolution.service.HomeService;

@Service
public class HelloServerImpl implements HomeService {

	@Value("${developer.name}")
	private String devName;

	@Override
	public String hello() {
		return "hello! by " + devName;
	}

}
