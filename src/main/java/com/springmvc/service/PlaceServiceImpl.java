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
		 
		String json = kakaoPlaceRepository.CallingAPI_1(query);
		
		List<Place> placeNames = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();

		try {
			JsonNode root = mapper.readTree(json);
			JsonNode documents = root.get("documents");
  
			for (JsonNode doc : documents) {
				Place place = new Place();
				
				String addressName = doc.get("address_name").asText();
				String placeName = doc.get("place_name").asText(); 
				String roadAddressName = doc.get("road_address_name").asText(); 
				String phone = doc.get("phone").asText(); 
				String X = doc.get("x").asText(); 
				String Y = doc.get("y").asText(); 
				
				place.setAddress_name(addressName);
				place.setPlace_name(placeName);
				place.setRoad_address_name(roadAddressName);
				place.setPhone(phone);
				place.setX(X);
				place.setY(Y);
				
				placeNames.add(place);
			}
			
		} catch (Exception e) {
			System.out.println("JSON 파싱 오류: " + e.getMessage());
		}

		return placeNames;
	}
	
	public List<Place> searchGPS(String query, String latitude, String longitude) {
		 
		String json = kakaoPlaceRepository.CallingAPI(query, longitude, latitude);
		
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

	public List<Place> searchLocation(String place_name) {
		 
		String json = kakaoPlaceRepository.CallingAPI_2(place_name);
		
		List<Place> Loaction = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();

		try {
			JsonNode root = mapper.readTree(json);
			JsonNode documents = root.get("documents");
  
			for (JsonNode doc : documents) {
				Place place = new Place();
				
				String PlaceName = doc.get("place_name").asText();  
				place.setX(PlaceName);
				
				Loaction.add(place);
			}
			
		} catch (Exception e) {
			System.out.println("JSON 파싱 오류: " + e.getMessage());
		}

		return Loaction;
	}
}
