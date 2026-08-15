<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Laptop Map</title>
<link rel="stylesheet" href="<c:url value='/resources/css/home.css'/>?v=20260717">
</head>
<body>
	<script>
		var latitude = "${param.latitude}" || 37.4979;
		var longitude = "${param.longitude}" || 127.0276;
	</script>
	<main class="app-shell">
		<section class="page-header">
			<div>
				<p class="eyebrow">Laptop Map</p>
				<h1>노트북 하기 좋은 장소</h1>
				<p class="page-description">지도에서 작업하기 좋은 카페와 스터디 공간을 찾아보세요.</p>
			</div>
			<a class="button button-secondary" href="<c:url value='/places'/>">장소 목록</a>
		</section>

		<section class="search-panel" aria-labelledby="search-title">
			<div class="search-copy">
				<p class="search-label">NEARBY SEARCH</p>
				<h2 id="search-title">어디에서 작업할까요?</h2>
				<p>현재 위치를 설정하고 카페, 스터디카페 같은 장소를 검색해보세요.</p>
			</div>

			<form class="search-form" action="/laptop-map/search" method="get">
				<label class="search-field" for="query">
					<span>장소 검색</span>
					<input id="query" type="text" name="query" placeholder="예: 카페, 스터디카페" required>
				</label>
				<input type="hidden" name="latitude" id="latitude" value="37.4979">
				<input type="hidden" name="longitude" id="longitude" value="127.0276">
				<div class="search-actions">
					<button class="button button-secondary" type="button" id="current-location">현재 위치 설정</button>
					<button class="button button-primary search-submit" type="submit">주변 검색</button>
				</div>
			</form> 
			<p class="search-help">검색 결과는 현재 위치 기준 반경 500m 이내에서 표시됩니다.</p>
		</section>

		<section class="map-section">
			<div class="map-toolbar">
				<div>
					<h2>지도</h2>
					<p>현재는 서울 중심으로 시작합니다.</p>
				</div>
				<span class="map-radius">반경 500m</span>
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
