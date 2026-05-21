package com.springmvc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springmvc.domain.Place;

public interface PlaceService {
	public List<Place> searchPlaces(String keyword) ;
}
