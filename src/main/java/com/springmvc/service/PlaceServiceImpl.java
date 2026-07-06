package com.springmvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmvc.domain.Place;
import com.springmvc.repository.KakaoPlaceRepository;

@Service
public class PlaceServiceImpl implements PlaceService {
	
	@Autowired
	private KakaoPlaceRepository kakaoPlaceRepository;
	
	public List<Place> searchPlace(String query) {
		 
		String json = kakaoPlaceRepository.CallingAPI(query);
		
		List<Place> placeNames = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();

		try {
			JsonNode root = mapper.readTree(json);
			JsonNode documents = root.get("documents");
  
			for (JsonNode doc : documents) {
				Place place = new Place();
				
				String placeName = doc.get("place_name").asText();  
				place.setPlace_name(placeName);
				
				placeNames.add(place);
			}
			
		} catch (Exception e) {
			System.out.println("JSON 파싱 오류: " + e.getMessage());
		}

		return placeNames;
	}
	
	public List<Place> searchLocation(String query) {
		 
		String json = kakaoPlaceRepository.CallingAPI(query);
		
		List<Place> Loaction = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();

		try {
			JsonNode root = mapper.readTree(json);
			JsonNode documents = root.get("documents");
  
			for (JsonNode doc : documents) {
				Place place = new Place();
				
				String X = doc.get("x").asText();  
				String Y = doc.get("y").asText();
				place.setX(X);
				place.setY(Y);
				
				Loaction.add(place);
			}
			
		} catch (Exception e) {
			System.out.println("JSON 파싱 오류: " + e.getMessage());
		}

		return Loaction;
	}

}
