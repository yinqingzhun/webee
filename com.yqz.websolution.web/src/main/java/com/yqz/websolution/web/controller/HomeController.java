package com.yqz.websolution.web.controller;

import java.util.HashMap;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController implements EnvironmentAware{
	Environment env=null;
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String Index(Model model) {
		model.addAttribute("msg", "welcome to spring mvc world!!");
		return "index2";
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.env=environment;
		
	}
}
