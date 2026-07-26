package com.springmvc.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	// 장소 목록 출력
	@GetMapping("/places")
    public String searchPlace(@RequestParam(value="query", required=false) String query, Model model) {
		
		List<Place> placeNames = Collections.emptyList();

		if (query != null && !query.isBlank()) {
			placeNames = placeService.searchPlace(query);
		}

        model.addAttribute("placeNames", placeNames);

        return "placeList";
    }
	
	@GetMapping("/search")
	public String searchPlace(
			@RequestParam("query") String query,			
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude, Model model) throws Exception {
		
		List<Place> locations = placeService.searchGPS(query, latitude, longitude);

	    ObjectMapper mapper = new ObjectMapper();
	    String locationsJson = mapper.writeValueAsString(locations);

	    model.addAttribute("locationsJson", locationsJson);
		model.addAttribute("js_apikey",js_apikey);
		
	    return "home";
	}
	
	@GetMapping("/detail")
	public String detail(Model model) {
		return "placeDetail";
	}
	
	@PostMapping("/places/select")
	public String selectPlace(
			@RequestParam("place_name") String placeName,
			@RequestParam("x") String x,
			@RequestParam("y") String y,
			@RequestParam("phone") String phone,
			@RequestParam("address_name") String address_name,
			Model model) {

		Place place = new Place();

		place.setPlace_name(placeName);
		place.setX(x);
		place.setY(y);
		place.setPhone(phone);
		place.setAddress_name(address_name);

		model.addAttribute("place", place);
		model.addAttribute("x",x);
		model.addAttribute("y",y);
		model.addAttribute("phone",phone);
		
		
		return "placeDetail";
	}
	

}
