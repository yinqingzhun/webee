package com.yqz.websolution.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yqz.websolution.model.Family;
import com.yqz.websolution.model.Person;
import com.yqz.websolution.model.Person.Sex;
import com.yqz.websolution.service.HelloService;
import com.yqz.websolution.model.Result;
import com.yqz.websolution.web.annotation.ApiVersion;

@RestController
@RequestMapping(value = "/{versin:\\d{1,2}}")
@ApiVersion(2)
public class HelloController {
	@Autowired
	HelloService helloService;

	@RequestMapping(value = "/api/hello", method = RequestMethod.GET)
	public Result<?> index() {
		String info = "hello,spring mvc.v2";
		HashMap<String, String> map = new HashMap<>();
		map.put("info", info);
		map.put("words", helloService.getWords());
		return Result.buildSuccessResult(map);

	}

	@ApiVersion(value = 1, weight = 1)
	@RequestMapping(value = "/api/hello", method = RequestMethod.GET)
	public Result<?> index_2() {
		String info = "hello,spring mvc.v1";
		HashMap<String, String> map = new HashMap<>();
		map.put("info", info);
		return Result.buildSuccessResult(map);

	}

	@ApiVersion(value = 1, weight = 1)
	@RequestMapping(value = { "/api/hello/Family", "/api/hello/familyMembers" }, method = { RequestMethod.GET,
			RequestMethod.HEAD }, consumes = { "application/xml", "application/json" }, produces = { "application/xml",
					"application/json" }, headers = { "content-type=application/*" }, name = "method_family")
	public Family family() {
		ArrayList<Person> list = new ArrayList<>();
		Person p = new Person();
		p.setAge(25);
		p.setName("Jack");
		p.setSex(Sex.Male);
		Calendar c = Calendar.getInstance();
		c.set(1985, 5, 13, 14, 32, 23);
		p.setBirthday(c.getTime());
		list.add(p);

		p = new Person();
		p.setAge(22);
		p.setName("Rose");
		p.setSex(Sex.Female);
		c.set(1989, 2, 14, 6, 12, 45);
		p.setBirthday(c.getTime());
		list.add(p);
		// exercise exception handler
		// if (true)
		// throw new NullPointerException();
		return new Family(list);

	}

	@RequestMapping(value = "/api/hello/someone", method = RequestMethod.GET)
	public Result<?> someone() {
		Person p = new Person();
		p.setAge(25);
		p.setName("Jack");
		p.setSex(Sex.Male);

		return Result.buildSuccessResult(p);
	}

	@ExceptionHandler({ NullPointerException.class })
	public Result<?> handleException(HttpServletRequest request, HttpServletResponse response, Exception e,
			org.springframework.ui.Model model) {

		return Result.buildErrorResult(901, "NullPointerException has raised.");
	}
}
