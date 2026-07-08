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
	<script>
		var latitude = "${param.latitude}" || 37.4979;
		var longitude = "${param.longitude}" || 127.0276;
	</script>
	<form action="/laptop-map/search" method="get">
		<input type="text" name="query" placeholder="검색어 입력">
		<input type="hidden" name="latitude" id="latitude">
		<input type="hidden" name="longitude" id="longitude">
		<button type="submit">검색</button>
	</form>
	<main class="app-shell">
		<section class="page-header">
			<div>
				<p class="eyebrow">Laptop Map</p>
				<h1>노트북 하기 좋은 장소</h1>
				<p class="page-description">지도에서 작업하기 좋은 카페와 스터디 공간을 찾아보세요.</p>
			</div>
			<a class="button button-secondary" href="<c:url value='/places'/>">장소 목록</a>
		</section>

		<section class="map-section">
			<div class="map-toolbar">
				<div>
					<h2>지도</h2>
					<p>현재는 서울 중심으로 시작합니다.</p>
				</div>
				<button class="button button-primary" type="button" id="current-location">현재 위치 보기</button>
			</div>
			<div id="map"></div>
		</section>
	</main>
	<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${js_apikey}&libraries=services"></script>
	<script>
		const locations = ${locationsJson};
	</script>
	<script src="<c:url value='/resources/js/kakaoMap.js'/>?v=20260706"></script>
	<script>
		createMarkers(locations);
	</script>
</body>
</html>
