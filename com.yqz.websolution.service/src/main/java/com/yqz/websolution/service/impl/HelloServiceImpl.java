package com.yqz.websolution.service.impl;

import org.springframework.stereotype.Service;

import com.yqz.websolution.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService {

	public HelloServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getWords() {
		return "欢迎你";
	}

}
