package com.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springmvc.domain.Place;
import com.springmvc.service.PlaceService;

@Controller
@RequestMapping("/places")
public class PlaceController {
	
	@Autowired
	private PlaceService placeService;

	@GetMapping
	public String list() {
		return "placeList";
	}

	// 요청을 받는 코드
	@GetMapping("/search")
	public String searchPlace(@RequestParam("query") String query, Model model) {
		List<Place> placeNames = placeService.searchPlace(query);
		
		model.addAttribute("placeNames", placeNames);
		
		return "placeList";
	}

	@GetMapping("/detail")
	public String detail() {
		return "placeDetail";
	}
}
