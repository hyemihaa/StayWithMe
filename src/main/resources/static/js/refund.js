
function requestRefund(bookingNo) {

    if (confirm("정말로 환불을 진행하시겠습니까?")) {

        // 이제 amount 변수에 숫자 형태의 금액이 담깁니다.
        console.log(bookingNo);

        // 서버로 환불 요청 전송
        jQuery.ajax({
            url: "http://localhost:8080/refund/complete",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify({
                bookingNo: bookingNo,
            })
        }).done(function (data) {
            console.log("서버 응답 데이터:", data);
            if (data.response && data.response.status === 'cancelled') {
                jQuery.ajax({
                    url: "http://localhost:8080/refund", // DB 업데이트를 처리할 서버 엔드포인트
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    data: JSON.stringify({
                        imp_uid: impUid,
                        cancel_by: cancelBy,
                        booking_no: bookingNo,
                        cancel_amount: cancelAmount,
                        // 필요에 따라 다른 데이터를 추가할 수 있습니다.
                    })
                }).done(function (dbResponse) {
                    if (dbResponse === 'OK') {
                        alert('환불이 정상 처리되었습니다.');
                    } else {
                        alert('환불은 성공했지만 DB 업데이트 중 오류가 발생했습니다.');
                    }
                }).fail(function (jqXHR, textStatus, errorThrown) {
                    console.error("DB 업데이트 요청 실패:", textStatus, errorThrown);
                    alert('환불은 성공했지만 DB 업데이트 중 오류가 발생했습니다.');
                });

            } else {
                alert('환불 처리 중 오류가 발생했습니다.');
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.error("환불 요청 실패:", textStatus, errorThrown);
            alert('환불 요청 중 오류가 발생했습니다.');
        });
    } else {
        // 사용자가 취소를 눌렀을 때는 아무 동작도 하지 않습니다.
        alert('환불 요청이 취소되었습니다.');
    }
}