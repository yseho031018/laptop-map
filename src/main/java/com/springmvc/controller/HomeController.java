package com.springmvc.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

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

	@GetMapping("/search")
	public ResponseEntity<String> searchPlace(@RequestParam("query") String query) {
	    RestTemplate restTemplate = new RestTemplate();

	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", "KakaoAK" + rest_apikey);

	    HttpEntity<String> entity = new HttpEntity<>(headers);

	    String url = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + query;

	    ResponseEntity<String> response = restTemplate.exchange(
	        url,
	        HttpMethod.GET,
	        entity,
	        String.class
	    );

	    return ResponseEntity.ok(response.getBody());
	}
}
