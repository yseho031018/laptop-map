package com.springmvc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springmvc.domain.Place;

@Service
public interface PlaceService {
	public List<Place> searchPlace(String query);
	public List<Place> searchLocation(String query, String latitude, String longitude);
}

