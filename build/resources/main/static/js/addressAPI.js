var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(37.537187, 127.005476),
        level: 5
    };

var map = new kakao.maps.Map(mapContainer, mapOption);
var geocoder = new kakao.maps.services.Geocoder();
var marker = new kakao.maps.Marker({
    position: new kakao.maps.LatLng(37.537187, 127.005476),
    map: map
});

function sample5_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = data.address;
            document.getElementById("sample5_address").value = addr;
            var region = data.sigungu + " " + data.bname;
            var roadName = data.roadAddress;

            document.getElementById("sample5_region").value = region;
            document.getElementById("sample5_roadName").value = roadName;


            console.log("지번 : " + region);
            console.log("도로명 : " + roadName);

            geocoder.addressSearch(data.address, function(results, status) {
                if (status === kakao.maps.services.Status.OK) {
                    var result = results[0];
                    var coords = new kakao.maps.LatLng(result.y, result.x);

                    mapContainer.style.display = "block";
                    map.relayout();
                    map.setCenter(coords);
                    marker.setPosition(coords);

                    document.getElementById("sample5_lat").value = result.y;
                    document.getElementById("sample5_lon").value = result.x;
                }
                console.log("위도 : " + result.x);
                console.log("경도 : " + result.y);
            });
        }
    }).open();
}