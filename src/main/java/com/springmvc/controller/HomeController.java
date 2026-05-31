package com.springmvc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
	// API 주소 변수
	@Value("${kakao.javascript-key}")
	private String js_apikey;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("js_apikey",js_apikey);
		return "home";
	}
	
}