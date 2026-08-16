package com.springmvc.service;

import java.util.List;

import com.springmvc.domain.Wifi;

public interface WifiService {
	public List<Wifi> searchWifi(String query);
	public List<Wifi> searchNearbyWifi(String query, double latitude, double longitude);
}
