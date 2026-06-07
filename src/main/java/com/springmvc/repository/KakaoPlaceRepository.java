package com.springmvc.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class KakaoPlaceRepository {
	
	@Value("${kakao.rest-api-key}")
	private String rest_apikey;
	
	public String CallingAPI(String query) {
		RestTemplate restTemplate = new RestTemplate();

		// 카카오 API 호출 준비
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK " + rest_apikey);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		// 인증 헤더 추가
		String url = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + query;
	
		// 카카오 API 실제 호출
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		
		return response.getBody();
	}
}
