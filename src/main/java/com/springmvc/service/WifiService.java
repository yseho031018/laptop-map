package com.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WifiService {
	
	@Value("${public-wifi.service-key}")
	private String publicWifiServiceKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveWifiData() {
    	String url =
    			"https://apis.data.go.kr/1741000/free_wifi_info"
    			+ "?serviceKey=" + publicWifiServiceKey
    			+ "&pageNo=1"
    			+ "&numOfRows=100"
    			+ "&type=json";
    	
        String json = restTemplate.getForObject(url, String.class);

        // JSON 파싱 (Jackson ObjectMapper 사용)
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(json);
            JsonNode items = root.path("response").path("body").path("items");
            // ↑ 이 경로는 API 명세서 응답 구조 보고 맞춰야 함

            for (JsonNode item : items) {
                String placeName = item.path("placeName").asText();
                double lat = item.path("lat").asDouble();
                double lng = item.path("lng").asDouble();
                String ssid = item.path("ssid").asText();

                String sql = "INSERT INTO wifi (place_name, latitude, longitude, ssid) VALUES (?, ?, ?, ?)";
                jdbcTemplate.update(sql, placeName, lat, lng, ssid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
