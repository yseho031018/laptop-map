package com.springmvc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/places")
public class PlaceController {
	
	@GetMapping
    public String list(Model model) {   
        return "placeList";
    }

	@GetMapping("/detail")
	public String detail() {
		return "placeDetail";
	}
}
