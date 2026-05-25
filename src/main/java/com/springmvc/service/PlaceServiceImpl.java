package com.springmvc.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl implements PlaceService {

	@Value("${kakao.rest-api-key}")
	private String restApiKey;

	@Override
	public String searchPlaces(String keyword) throws Exception {
		String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
		String url = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + encodedKeyword;

		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(url))
			.header("Authorization", "KakaoAK " + restApiKey)
			.GET()
			.build();

		HttpResponse<String> response = HttpClient.newHttpClient()
			.send(request, HttpResponse.BodyHandlers.ofString());

		return response.body();
	}
}
