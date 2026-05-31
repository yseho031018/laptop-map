package com.springmvc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
	@Value("${kakao.javascript-key}")
	private String js_apikey;

	@Value("${kakao.rest-api-key}")
	private String rest_apikey;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("js_apikey",js_apikey);
		return "home";
	}
	
}