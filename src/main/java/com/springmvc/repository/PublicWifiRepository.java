package com.springmvc.repository;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
public class PublicWifiRepository {

    @Value("${public-wifi.service-key}")
    private String serviceKey;

    private final RestTemplate restTemplate;

    public PublicWifiRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

	// 도로명 주소에 시도와 시군구가 포함된 공공 와이파이 데이터를 페이지 단위로 조회한다.
	public String callApi(int pageNo, int numOfRows, String roadAddressKeyword) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(
                    "https://apis.data.go.kr/1741000/free_wifi_info/info"
                )
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", pageNo)
                .queryParam("numOfRows", numOfRows)
                .queryParam("returnType", "json");

		if (roadAddressKeyword != null && !roadAddressKeyword.isBlank()) {
			uriBuilder.queryParam(
					"cond[LCTN_ROAD_NM_ADDR::LIKE]",
					roadAddressKeyword
			);
		}

		URI uri = uriBuilder
                .build()
                .encode()
                .toUri();

        return restTemplate.getForObject(uri, String.class);
    }
}
