package com.yqz.websolution.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.yqz.websolution.model.exception.UnAuthenticationException;

@Controller
public class HomeController {
	@Value("${jdbc.jdbcUrl}")
	private String mysqlUrl;

	@RequestMapping(value = { "/home/index", "/" }, method = RequestMethod.GET)
	public String Index(Model model) {
		model.addAttribute("msg", "welcome to spring mvc world!!" + mysqlUrl);
		return "index2";
	}

	@RequestMapping("/home/xmlViewResolver")
	public String testXmlViewResolver() {
		return "internalResource";
	}

	@RequestMapping(value = "/home/welcome", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Spring 3 MVC Hello World");
		return "hello";
	}

	@RequestMapping(value = "/home/Welcome/{name:.+}", method = RequestMethod.GET)
	public ModelAndView hello(@PathVariable("name") String name) {

		ModelAndView model = new ModelAndView();
		model.setViewName("hello");
		model.addObject("message", name);

		return model;

	}

	@RequestMapping(value = "/home/method", method = RequestMethod.GET)
	public ResponseEntity<?> method(HttpEntity<String> entity) throws UnAuthenticationException {
		int i = 1 / 0;
		if (!entity.getHeaders().containsKey("ticket"))
			throw new UnAuthenticationException();
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler({ NullPointerException.class })
	public String handleException(HttpServletRequest request, HttpServletResponse response, Exception e,
			org.springframework.ui.Model model) {
		model.addAttribute("message", "internal server error:500");
		return "error500";
	}

}
