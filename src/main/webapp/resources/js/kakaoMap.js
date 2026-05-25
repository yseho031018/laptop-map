var container = document.getElementById('map');
var currentLocationButton = document.getElementById('current-location');

var latitude = 37.5665;
var longitude = 126.9780;

// 기본 위치 고정
var options = {
    center: new kakao.maps.LatLng(latitude, longitude),
    level: 3
};

var map = new kakao.maps.Map(container, options);

// 현재 위치 출력 이벤트 함수
currentLocationButton.addEventListener('click', function() {
	if (navigator.geolocation) {
	    navigator.geolocation.getCurrentPosition(function(position) {
	        latitude = position.coords.latitude;
	        longitude = position.coords.longitude;

	        console.log('현재 위도:', latitude);
	        console.log('현재 경도:', longitude);

			var currentPosition = new kakao.maps.LatLng(latitude, longitude);
			map.setCenter(currentPosition);
	    });
	} else {
	    console.log('이 브라우저에서는 현재 위치를 사용할 수 없습니다.');
	}
});
