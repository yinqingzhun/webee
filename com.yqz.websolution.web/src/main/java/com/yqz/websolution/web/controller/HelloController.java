package com.yqz.websolution.web.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yqz.websolution.web.model.Result;

@RestController
public class HelloController {

	@RequestMapping(value = "/api/hello", method = RequestMethod.GET)
	public String Index() {
		String info="hello,spring mvc";
		//HashMap<String, String> map = new HashMap<>();
		//map.put("info", "hello,spring mvc");
		//return Result.buildSuccessResult(map);
		return info;
	}
}
