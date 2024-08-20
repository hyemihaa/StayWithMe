(function ($) {
    "use strict";

    // 페이지 로딩 중에 스피너를 표시하는 기능
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner();

    // 스크롤 시 페이지 맨 위로 올라가는 버튼 표시 기능
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });

    // 사이드바와 콘텐츠 영역을 토글하는 기능
    $('.sidebar-toggler').click(function () {
        $('.sidebar, .content').toggleClass("open");
        return false;
    });

    // 월별 예약, 취소, 결제 건수 그래프 생성
    var ctx2 = document.getElementById("salse-revenue").getContext("2d");
    var myChart2 = new Chart(ctx2, {
        type: "bar", // 막대 그래프
        data: {
            labels: monthlyLabels, // 월별 레이블
            datasets: [{
                label: "예약 건수",
                data: monthlyReservationCounts, // 월별 예약 건수 데이터
                backgroundColor: "rgba(0, 156, 255, .5)" // 예약 건수 막대 색상
            },
            {
                label: "결제 건수",
                data: monthlyPaymentCounts, // 월별 결제 건수 데이터
                backgroundColor: "rgba(75, 192, 192, .5)" // 결제 건수 막대 색상
            },
            {
                label: "취소 건수",
                data: monthlyCancelCounts, // 월별 취소 건수 데이터
                backgroundColor: "rgba(255, 99, 132, .5)" // 취소 건수 막대 색상
            },]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false, // 비율 유지 안함
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        stepSize: 1 // 예약 건수는 정수로 표시되므로 stepSize를 1로 설정
                    }
                }
            }
        }
    });

    // 디버깅용 데이터 확인
    console.log("monthlyLabels: ", monthlyLabels);
    console.log("monthlyReservationCounts: ", monthlyReservationCounts);
    console.log("monthlyCancelCounts: ", monthlyCancelCounts);
    console.log("monthlyPaymentCounts: ", monthlyPaymentCounts);

})(jQuery);
