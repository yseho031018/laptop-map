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
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmvc.domain.Place;
import com.springmvc.domain.Wifi;
import com.springmvc.service.PlaceService;
import com.springmvc.service.WifiService;

@Controller
public class PlaceController {
	
	@Autowired
	private PlaceService placeService;
	
	@Autowired
	private WifiService wifiService; 
	
	@Value("${kakao.javascript-key}")
	private String js_apikey;

	@GetMapping("/wifi")
	public String searchWifi(@RequestParam(value = "query", required = false) String query, Model model) {
		
		List<Wifi> wifiNames = Collections.emptyList();
		
		if (query != null && !query.isBlank()) {
			wifiNames = wifiService.searchWifi(query);
		}

        model.addAttribute("wifiNames", wifiNames);

        return "wifiList";
    }

	// 체크박스에서 요청하면 검색 위치와 가까운 와이파이 좌표를 최대 15개 반환한다.
	@GetMapping("/api/wifi/markers")
	@ResponseBody
	public List<Wifi> getWifiMarkers(
			@RequestParam("query") String query,
			@RequestParam("latitude") double latitude,
			@RequestParam("longitude") double longitude) {

		return wifiService.searchNearbyWifi(query, latitude, longitude);
	}

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
