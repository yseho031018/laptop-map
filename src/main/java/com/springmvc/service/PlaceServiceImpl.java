package com.springmvc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl implements PlaceService {

	@Value("${kakao.rest-api-key}")
	private String restApiKey;

	@Override
	public String searchPlaces(String keyword) throws Exception {
		return null;
	}
}
