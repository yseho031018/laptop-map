package com.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmvc.domain.Place;
import com.springmvc.service.PlaceService;

@Controller
public class PlaceController {
	
	@Autowired
	private PlaceService placeService;
	
	@Value("${kakao.javascript-key}")
	private String js_apikey;

//	@GetMapping("/places")
//	public String list() {
//		return "placeList";
//	}

//	 요청을 받는 코드
	@GetMapping("/places")
	public String searchPlace(@RequestParam(value="query", required=false) String query, Model model) {
		
		List<Place> placeNames = placeService.searchPlace(query);
		
		model.addAttribute("placeNames", placeNames);
		
		return "placeList";
	}
	
	
	@GetMapping("/search")
	public String searchPlace(
			@RequestParam("query") String query,			
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude, Model model) throws Exception {
		
		List<Place> locations = placeService.searchLocation(query, latitude, longitude);

	    ObjectMapper mapper = new ObjectMapper();
	    String locationsJson = mapper.writeValueAsString(locations);

	    model.addAttribute("locationsJson", locationsJson);
		model.addAttribute("js_apikey",js_apikey);
		
	    return "home";
	}

	@GetMapping("/detail")
	public String detail() {
		return "placeDetail";
	}
}
