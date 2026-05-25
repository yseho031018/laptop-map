<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Laptop Map</title>
<link rel="stylesheet" href="<c:url value='/resources/css/home.css'/>">
</head>
<body>
	<h1>노트북 하기 좋은 장소</h1>
	<p>메인 화면입니다.</p>
	<button type="button" id="current-location">현재 위치 보기</button>
	<div id="map"></div>
	<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${apikey}&libraries=services"></script>
	<script src="<c:url value='/resources/js/kakaoMap.js'/>"></script>
	
	<a href="<c:url value='/places'/>">
		<button type="button">장소 목록 화면으로 이동</button>
	</a>
</body>
</html>
