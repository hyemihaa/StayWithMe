var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(37.537187, 127.005476),
        level: 5
    };

//지도를 미리 생성
var map = new kakao.maps.Map(mapContainer, mapOption);
//주소-좌표 변환 객체를 생성
var geocoder = new kakao.maps.services.Geocoder();
//마커를 미리 생성
var marker = new kakao.maps.Marker({
    position: new kakao.maps.LatLng(37.537187, 127.005476),
    map: map
});




// 지도
function sample5_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = data.address; // 최종 주소 변수

            // 주소 정보를 해당 필드에 넣는다.
            document.getElementById("sample5_address").value = addr;
            // 주소로 상세 정보를 검색
            geocoder.addressSearch(data.address, function(results, status) {
                // 정상적으로 검색이 완료됐으면
                if (status === kakao.maps.services.Status.OK) {

                    var result = results[0]; //첫번째 결과의 값을 활용

                    // 해당 주소에 대한 좌표를 받아서
                    var coords = new kakao.maps.LatLng(result.y, result.x);

                    // 지도를 보여준다.
                    mapContainer.style.display = "block";
                    map.relayout();
                    // 지도 중심을 변경한다.
                    map.setCenter(coords);
                    // 마커를 결과값으로 받은 위치로 옮긴다.
                    marker.setPosition(coords);

                    $.ajax({
                        url: '/saveLocation',
                        type: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify({
                            lat: result.y,
                            lon: result.x
                        }),
                        success: function(response) {
                            console.log('서버로 전송 성공:', response);
                        },
                        error: function(xhr, status, error) {
                            console.error('서버로 전송 실패:', status, error);
                        }
                    });
                }
            });
        }
    }).open();
}