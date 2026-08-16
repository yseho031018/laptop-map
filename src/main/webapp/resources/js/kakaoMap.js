var container = document.getElementById('map');
var currentLocationButton = document.getElementById('current-location');
var wifiFilter = document.getElementById('wifi-filter');

// 기본 위치 고정
var options = {
    center: new kakao.maps.LatLng(latitude, longitude),
    level: 3
};

// 지도맵 생성 객체
var map = new kakao.maps.Map(container, options);

// 현재 위치 마커를 저장해 버튼을 다시 눌러도 마커가 중복 생성되지 않게 한다.
var currentLocationMarker;

// 44x44 크기의 현재 위치 전용 SVG 이미지를 카카오 마커 이미지로 만든다.
// offset은 이미지의 정중앙이 실제 현재 위치 좌표에 오도록 설정한다.
var currentLocationMarkerImage = new kakao.maps.MarkerImage(
	currentLocationMarkerImageUrl,
	new kakao.maps.Size(44, 44),
	{
		offset: new kakao.maps.Point(22, 22)
	}
);

// 마커 생성 위치
// 지도 객체(map)가 만들어진 뒤에 마커를 생성해야 지도 위에 표시할 수 있다.
// 현재는 첫 시작 좌표(latitude, longitude)에 기본 마커 1개를 표시한다.
var placeMarkers = [];
var placeInfoWindow = new kakao.maps.InfoWindow({
	removable: true
});

// 내부 아이콘이 없는 파란색 핀 형태의 장소 마커 이미지를 설정한다.
var placeMarkerImage = new kakao.maps.MarkerImage(
	placeMarkerImageUrl,
	new kakao.maps.Size(32, 40),
	{
		offset: new kakao.maps.Point(16, 40)
	}
);

function createPlaceMarkers(locations) {
    locations.forEach(location => {
        var placeMarker = new kakao.maps.Marker({
            map: map,
			image: placeMarkerImage,
			clickable: true,
            position: new kakao.maps.LatLng(
                location.y,
                location.x
            )
        });

        placeMarkers.push(placeMarker);
		
		// 마커 클릭 이벤트
		kakao.maps.event.addListener(placeMarker, 'click', function() {
			var content = document.createElement('div');
			content.className = 'place-info-window';

			var title = document.createElement('strong');
			title.className = 'place-info-title';
			title.textContent = location.place_name || '장소명 없음';

			var address = document.createElement('p');
			address.className = 'place-info-address';
			address.textContent = location.road_address_name
				|| location.address_name
				|| '주소 정보 없음';

			content.appendChild(title);
			content.appendChild(address);

			if (location.phone) {
				var phone = document.createElement('p');
				phone.className = 'place-info-phone';
				phone.textContent = location.phone;
				content.appendChild(phone);
			}

			placeInfoWindow.setContent(content);
			placeInfoWindow.open(map, placeMarker);
		}
		);
    });
}

// 체크박스로 생성한 와이파이 마커만 따로 저장한다.
var wifiMarkers = [];

// 장소 마커와 구분할 수 있도록 파란색 와이파이 전용 이미지를 설정한다.
var wifiMarkerImage = new kakao.maps.MarkerImage(
	wifiMarkerImageUrl,
	new kakao.maps.Size(32, 40),
	{
		offset: new kakao.maps.Point(16, 40)
	}
);

// 서버에서 받은 최대 15개의 좌표에 와이파이 이미지 마커를 생성한다.
function createWifiMarkers(wifiLocations) {
	wifiLocations.slice(0, 15).forEach(wifi => {
		var wifiMarker = new kakao.maps.Marker({
			map: map,
			image: wifiMarkerImage,
			position: new kakao.maps.LatLng(wifi.latitude, wifi.longitude)
		});

		wifiMarkers.push(wifiMarker);
	});
}

// 체크박스를 해제하면 와이파이 마커만 지도에서 제거한다.
function clearWifiMarkers() {
	wifiMarkers.forEach(wifiMarker => wifiMarker.setMap(null));
	wifiMarkers = [];
}

// 장소 검색 결과가 있을 때만 와이파이 체크박스를 사용할 수 있다.
wifiFilter.disabled = locations.length === 0;

wifiFilter.addEventListener('change', function() {
	clearWifiMarkers();

	if (!wifiFilter.checked) {
		return;
	}

	// 첫 번째 장소 주소에서 시군구명을 가져온다. 예: 서울 강남구 역삼동 -> 강남구
	var address = locations[0].address_name || '';
	var addressParts = address.split(' ');
	var districtName = addressParts[1] || addressParts[0];

	if (!districtName) {
		wifiFilter.checked = false;
		return;
	}

	// 검색 기준 위도와 경도를 서버로 보내 가까운 순서의 와이파이를 요청한다.
	var params = new URLSearchParams({
		query: districtName,
		latitude: latitude,
		longitude: longitude
	});

	fetch(wifiMarkersUrl + '?' + params.toString())
		.then(response => response.json())
		.then(wifiLocations => createWifiMarkers(wifiLocations))
		.catch(error => console.log('와이파이 마커 오류:', error));
});

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
		
		// 현재 좌표값을 구하는 함수
	    navigator.geolocation.getCurrentPosition(function(position) {
			latitude = position.coords.latitude;
			longitude = position.coords.longitude;
	        document.getElementById('latitude').value = position.coords.latitude;
	        document.getElementById('longitude').value = position.coords.longitude;

	        console.log('현재 위도:', latitude);
	        console.log('현재 경도:', longitude);

			// 브라우저에서 받은 위도와 경도를 카카오 지도 좌표 객체로 변환한다.
			var currentPosition = new kakao.maps.LatLng(latitude, longitude);

			if (currentLocationMarker) {
				// 이미 현재 위치 마커가 있으면 새로 만들지 않고 위치만 변경한다.
				currentLocationMarker.setPosition(currentPosition);
			} else {
				// 처음 조회할 때 전용 SVG 이미지가 적용된 현재 위치 마커를 생성한다.
				currentLocationMarker = new kakao.maps.Marker({
					map: map,
					position: currentPosition,
					image: currentLocationMarkerImage
				});
			}

			// 지도의 중심을 조회한 현재 위치로 이동한다.
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
