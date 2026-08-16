package com.springmvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmvc.domain.Wifi;
import com.springmvc.repository.PublicWifiRepository;

@Service
public class WifiServiceImpl implements WifiService {

	@Autowired
	private PublicWifiRepository publicWifiRepository;

	// 입력한 시군구의 공공 와이파이 정보를 최대 15개 가져온다.
	public List<Wifi> searchWifi(String query) {
		List<Wifi> wifiNames = new ArrayList<>();

		if (query == null || query.isBlank()) {
			return wifiNames;
		}

		ObjectMapper mapper = new ObjectMapper();

		try {
			// 첫 번째 페이지에서 15개만 요청한다.
			String json = publicWifiRepository.callApi(1, 15, query);
			JsonNode items = mapper.readTree(json)
					.path("response")
					.path("body")
					.path("items")
					.path("item");

			for (JsonNode item : items) {
				double latitude = item.path("WGS84_LAT").asDouble();
				double longitude = item.path("WGS84_LOT").asDouble();

				// 좌표가 없는 데이터는 지도에 표시할 수 없으므로 제외한다.
				if (latitude == 0.0 || longitude == 0.0) {
					continue;
				}

				Wifi wifi = new Wifi();
				wifi.setProvinceName(item.path("INSTL_CTPV_NM").asText());
				wifi.setDistrictName(item.path("INSTL_SGG_NM").asText());
				wifi.setPlaceName(item.path("INSTL_PLC_NM").asText());
				wifi.setLocationDetail(item.path("INSTL_PLC_DTL").asText());
				wifi.setServiceProvider(item.path("SRVC_PROV_NM").asText());
				wifi.setRoadAddress(item.path("LCTN_ROAD_NM_ADDR").asText());
				wifi.setLotNumberAddress(item.path("LCTN_LOTNO_ADDR").asText());
				wifi.setLatitude(latitude);
				wifi.setLongitude(longitude);
				wifiNames.add(wifi);
			}
		} catch (Exception e) {
			System.out.println("공공 와이파이 JSON 파싱 오류: " + e.getMessage());
		}

		return wifiNames;
	}

	// 검색 위치와 가까운 순서대로 공공 와이파이를 최대 15개 가져온다.
	public List<Wifi> searchNearbyWifi(String query, double lat1, double lon1) {
		List<Wifi> wifiNames = new ArrayList<>();

		if (query == null || query.isBlank()) {
			return wifiNames;
		}

		ObjectMapper mapper = new ObjectMapper();
		int pageNo = 1;
		int totalPages = 1;

		try {
			do {
				String json = publicWifiRepository.callApi(pageNo, 100, query);
				JsonNode body = mapper.readTree(json)
						.path("response")
						.path("body");
				JsonNode items = body.path("items").path("item");

				for (JsonNode item : items) {
					double lat2 = item.path("WGS84_LAT").asDouble();
					double lon2 = item.path("WGS84_LOT").asDouble();

					if (lat2 == 0.0 || lon2 == 0.0) {
						continue;
					}

					// 두 위도와 경도의 차이를 이용해 거리를 미터 단위로 계산한다.
					double earthRadius = 6371000;
					double dLat = Math.toRadians(lat2 - lat1);
					double dLon = Math.toRadians(lon2 - lon1);

					double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
							+ Math.cos(Math.toRadians(lat1))
							* Math.cos(Math.toRadians(lat2))
							* Math.sin(dLon / 2) * Math.sin(dLon / 2);

					double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
					double distance = earthRadius * c;

					Wifi wifi = new Wifi();
					wifi.setPlaceName(item.path("INSTL_PLC_NM").asText());
					wifi.setLatitude(lat2);
					wifi.setLongitude(lon2);
					wifi.setDistance(distance);
					wifiNames.add(wifi);
				}

				int totalCount = body.path("totalCount").asInt();
				totalPages = (totalCount + 99) / 100;
				pageNo++;
			} while (pageNo <= totalPages);
		} catch (Exception e) {
			System.out.println("공공 와이파이 거리 계산 오류: " + e.getMessage());
		}

		// 계산한 거리가 짧은 순서대로 정렬한다.
		wifiNames.sort((wifi1, wifi2) ->
				Double.compare(wifi1.getDistance(), wifi2.getDistance()));

		// 정렬된 결과에서 앞의 15개만 반환한다.
		if (wifiNames.size() > 15) {
			return new ArrayList<>(wifiNames.subList(0, 15));
		}

		return wifiNames;
	}
}
