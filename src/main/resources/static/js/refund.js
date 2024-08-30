


function requestRefund() {
    const impUid = 'YOUR_IMP_UID'; // 승인번호를 실제 데이터로 대체하세요.
    const merchantUid = 'YOUR_MERCHANT_UID'; // 주문번호를 실제 데이터로 대체하세요.
    const reason = '고객 요청에 의한 환불'; // 환불 사유

    // 포트원(아임포트) API를 통해 환불 요청
    jQuery.ajax({
        url: "https://api.iamport.kr/payments/cancel",
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + getAccessToken() // 토큰을 가져오는 함수
        },
        data: JSON.stringify({
            reason: reason,
            imp_uid: impUid, // 환불할 결제 건의 imp_uid
            merchant_uid: merchantUid // 환불할 결제 건의 merchant_uid (선택 사항)
        })
    }).done(function (data) {
        if (data.response && data.response.status === 'cancelled') {
            alert('환불이 성공적으로 처리되었습니다.');
        } else {
            alert('환불 처리 중 오류가 발생했습니다.');
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.error("환불 요청 실패:", textStatus, errorThrown);
        alert('환불 요청 중 오류가 발생했습니다.');
    });
}

function getAccessToken() {
    // 포트원(아임포트) 액세스 토큰을 가져오는 함수
    return jQuery.ajax({
        url: "https://api.iamport.kr/users/getToken", // 포트원(아임포트) 액세스 토큰 발급 API URL
        method: "POST",
        headers: { "Content-Type": "application/json" },
        data: JSON.stringify({
            imp_key: "YOUR_REST_API_KEY", // 실제 포트원(아임포트) REST API 키로 대체
            imp_secret: "YOUR_REST_API_SECRET" // 실제 포트원(아임포트) REST API 시크릿으로 대체
        })
    }).then(function (response) {
        return response.response.access_token; // 토큰 값 반환
    });
}