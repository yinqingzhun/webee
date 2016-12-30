package com.yqz.websolution.web.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yqz.websolution.model.Family;
import com.yqz.websolution.model.Person;
import com.yqz.websolution.model.Person.Sex;
import com.yqz.websolution.model.Result;

@RestController
public class HelloController {

	@RequestMapping(value = "/api/hello", method = RequestMethod.GET)
	public Result<?> index() {
		String info = "hello,spring mvc";
		HashMap<String, String> map = new HashMap<>();
		map.put("info", info);
		return Result.buildSuccessResult(map);

	}

	@RequestMapping(value = "/api/hello/Family", method = RequestMethod.GET)
	public Family family() {
		ArrayList<Person> list = new ArrayList<>();
		Person p = new Person();
		p.setAge(25);
		p.setName("Jack");
		p.setSex(Sex.Male);
		list.add(p);

		p = new Person();
		p.setAge(22);
		p.setName("Rose");
		p.setSex(Sex.Female);
		list.add(p);

		return  new Family(list) ;

	}

	@RequestMapping(value = "/api/hello/someone", method = RequestMethod.GET)
	public Result<?> someone() {
		Person p = new Person();
		p.setAge(25);
		p.setName("Jack");
		p.setSex(Sex.Male);

		return Result.buildSuccessResult(p);
	}
}
