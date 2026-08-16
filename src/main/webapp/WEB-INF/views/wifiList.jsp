<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Place List</title>
<link rel="stylesheet" href="<c:url value='/resources/css/home.css'/>">
</head>
<body>
	<main class="app-shell">
		<section class="page-header">
			<div>
				<p class="eyebrow">Places</p>
				<h1>장소 목록</h1>
				<p class="page-description">검색 결과와 저장할 장소 정보를 확인하는 화면입니다.</p>
			</div>
			<a class="button button-secondary" href="<c:url value='/'/>">메인
				화면</a>
		</section>

		<section class="panel">
			<h2>검색 결과</h2><br>
			<c:forEach var="wifi" items="${wifiNames}">
    			<div class="wifi-item">
        			<h3>${wifi.placeName}</h3>

        			<p>지역: ${wifi.provinceName} ${wifi.districtName}</p>
        			<p>상세 위치: ${wifi.locationDetail}</p>
		        	<p>서비스 제공사: ${wifi.serviceProvider}</p>
        			<p>도로명 주소: ${wifi.roadAddress}</p>
        			<p>지번 주소: ${wifi.lotNumberAddress}</p>
        			<p>좌표: ${wifi.latitude}, ${wifi.longitude}</p>
    			</div>
			</c:forEach>
		</section>
	</main>
</body>
</html>
