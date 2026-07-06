var container = document.getElementById('map');
var currentLocationButton = document.getElementById('current-location');

// 첫 시작 위치 좌표 변수
var latitude = place.y;
var longitude = place.x;

// 기본 위치 고정
var options = {
    center: new kakao.maps.LatLng(latitude, longitude),
    level: 3
};

// 지도맵 생성 객체
var map = new kakao.maps.Map(container, options);

// 마커 생성 위치
// 지도 객체(map)가 만들어진 뒤에 마커를 생성해야 지도 위에 표시할 수 있다.
// 현재는 첫 시작 좌표(latitude, longitude)에 기본 마커 1개를 표시한다.
function createMaker({latitude, longitude, map}){
	var markerPosition = new kakao.maps.LatLng(latitude, longitude);

	var marker = new kakao.maps.Marker({
		map: map,
		position: markerPosition
	});
	
	return marker;
};

createMaker(latitude, longitude, map);
createMaker(37.4979, 127.0276, map);

// 버퍼링 기능 함수
function setLocationLoading(isLoading) {
	if (isLoading) {
		currentLocationButton.disabled = true;
		currentLocationButton.classList.add('is-loading');
		currentLocationButton.textContent = '위치 찾는 중...';
		return;
	}

	currentLocationButton.disabled = false;
	currentLocationButton.classList.remove('is-loading');
	currentLocationButton.textContent = '현재 위치 보기';
}

// 현재 위치 출력 이벤트 함수
currentLocationButton.addEventListener('click', function() {
	if (navigator.geolocation) {
		setLocationLoading(true);

	    navigator.geolocation.getCurrentPosition(function(position) {
	        latitude = position.coords.latitude;
	        longitude = position.coords.longitude;

	        console.log('현재 위도:', latitude);
	        console.log('현재 경도:', longitude);

			var currentPosition = new kakao.maps.LatLng(latitude, longitude);
			map.setCenter(currentPosition);

			setLocationLoading(false);
	    }, function() {
	        console.log('현재 위치를 가져올 수 없습니다.');
	        setLocationLoading(false);
	    });
	} else {
	    console.log('이 브라우저에서는 현재 위치를 사용할 수 없습니다.');
	    setLocationLoading(false);
	}
});
