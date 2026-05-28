var container = document.getElementById('map');
var currentLocationButton = document.getElementById('current-location');

// 첫 시작 위치 좌표 변수
var latitude = 37.5665;
var longitude = 126.9780;

// 기본 위치 고정
var options = {
    center: new kakao.maps.LatLng(latitude, longitude),
    level: 3
};

// 지도맵 생성 객체
var map = new kakao.maps.Map(container, options);

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
