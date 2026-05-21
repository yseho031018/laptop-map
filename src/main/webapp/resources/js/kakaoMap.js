var container = document.getElementById('map');

var options = {
    center: new kakao.maps.LatLng(37.5665, 126.9780),
    level: 3
};

var map = new kakao.maps.Map(container, options);


if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;

        console.log('현재 위도:', latitude);
        console.log('현재 경도:', longitude);
    });
} else {
    console.log('이 브라우저에서는 현재 위치를 사용할 수 없습니다.');
}
