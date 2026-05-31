package com.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


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
	
	// 요청을 받는 코드
		@GetMapping("/search")
		public String searchPlace(@RequestParam("query") String query,Model model) {
		    RestTemplate restTemplate = new RestTemplate();
		    
		    // 카카오 API 호출 준비
		    HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", "KakaoAK " + rest_apikey);

		    HttpEntity<String> entity = new HttpEntity<>(headers);
		    
		    // 인증 헤더 추가
		    String url = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + query;
		    
		    // 카카오 API 실제 호출
		    ResponseEntity<String> response = restTemplate.exchange(
		        url,
		        HttpMethod.GET,
		        entity,
		        String.class
		    );
		    
		    ObjectMapper mapper = new ObjectMapper();
		    
		    try {
		        JsonNode root = mapper.readTree(response.getBody());
		        JsonNode documents = root.get("documents");
		        List<String> placeNames = new ArrayList<>();
		        List<String> addressList = new ArrayList<>();
		        List<String> phoneList = new ArrayList<>();
		        System.out.println("=== 검색 결과 ===");
		        for (JsonNode doc : documents) {
		            String placeName = doc.get("place_name").asText();
		            String address = doc.get("address_name").asText();
		            String phone = doc.get("phone").asText("");
		            placeNames.add(placeName);
		            addressList.add(address);
		            phoneList.add(phone);
		            
		            System.out.println(placeName + " / " + address + " / " + phone);
		        }
		        model.addAttribute("placeNames", placeNames);
		        model.addAttribute("addressList", addressList);
		        model.addAttribute("phoneList", phoneList);
		        
		    } catch (Exception e) {
		        System.out.println("JSON 파싱 오류: " + e.getMessage());
		    }

		    HttpHeaders responseHeaders = new HttpHeaders();
		    responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		    
		    // return new ResponseEntity<>(response.getBody(), responseHeaders, HttpStatus.OK);
		    return "placeList";
		}
	
}
