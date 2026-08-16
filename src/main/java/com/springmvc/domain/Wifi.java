package com.springmvc.domain;

public class Wifi {

    // 시도명
    private String provinceName;
    // 시군구명
    private String districtName;
    // 설치장소명
    private String placeName;
    // 상세위치
    private String locationDetail;
    // 서비스 제공사
    private String serviceProvider;
    // 도로명주소
    private String roadAddress;
    // 지번주소
    private String lotNumberAddress;
	// 위도
    private double latitude;
    // 경도
    private double longitude;
	// 검색 위치로부터의 거리(m)
    private double distance;
    
    public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getLocationDetail() {
		return locationDetail;
	}
	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}
	public String getServiceProvider() {
		return serviceProvider;
	}
	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	public String getRoadAddress() {
		return roadAddress;
	}
	public void setRoadAddress(String roadAddress) {
		this.roadAddress = roadAddress;
	}
	public String getLotNumberAddress() {
		return lotNumberAddress;
	}
	public void setLotNumberAddress(String lotNumberAddress) {
		this.lotNumberAddress = lotNumberAddress;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
}
