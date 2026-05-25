package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/places")
public class PlaceController {

	@GetMapping
	public String list() {
		return "placeList";
	}

	@GetMapping("/detail")
	public String detail() {
		return "placeDetail";
	}
}
