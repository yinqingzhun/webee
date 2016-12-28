package com.yqz.websolution.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
public class HomeController {
	@RequestMapping(value = { "/home/index", "/" }, method = RequestMethod.GET)
	public String Index(Model model) {
		model.addAttribute("msg", "welcome to spring mvc world!!");
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

	@RequestMapping(value = "/home/welcome/{name:.+}", method = RequestMethod.GET)
	public ModelAndView hello(@PathVariable("name") String name) {

		ModelAndView model = new ModelAndView();
		model.setViewName("hello");
		model.addObject("message", name);

		return model;

	}

}
