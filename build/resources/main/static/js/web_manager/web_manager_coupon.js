(function ($) {
    "use strict";

    // Spinner
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner();


    // Back to top button
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


    // Sidebar Toggler
    $('.sidebar-toggler').click(function () {
        $('.sidebar, .content').toggleClass("open");
        return false;
    });


    // Progress Bar
    $('.pg-bar').waypoint(function () {
        $('.progress .progress-bar').each(function () {
            $(this).css("width", $(this).attr("aria-valuenow") + '%');
        });
    }, {offset: '80%'});


    // Calender
    $('#calender').datetimepicker({
        inline: true,
        format: 'L'
    });


    // Testimonials carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1000,
        items: 1,
        dots: true,
        loop: true,
        nav : false
    });
})(jQuery);



// 모달 열기 및 닫기 설정
var registerModal = document.getElementById("registerModal");
var editModal = document.getElementById("editModal");
var registerBtn = document.getElementById("registerBtn");
var editBtn = document.getElementById("editBtn");
var closeBtns = document.getElementsByClassName("close");

registerBtn.onclick = function() {
    registerModal.style.display = "flex";
}

editBtn.onclick = function() {
    var selectedCoupon = document.querySelector('input[name="coupon_check"]:checked');
    if (selectedCoupon) {
        var selectedRow = selectedCoupon.parentElement.parentElement;
        document.getElementById("editCouponCode").value = selectedRow.cells[1].innerText;
        document.getElementById("editCouponName").value = selectedRow.cells[2].innerText;
        document.getElementById("editCouponType").value = selectedRow.cells[3].innerText.includes('%') ? 'percent' : 'pay';
        document.getElementById("editDiscount").value = selectedRow.cells[3].innerText.replace('%', '').replace('원', '');
        document.getElementById("editCouponQuantity").value = selectedRow.cells[4].innerText;
        // 쿠폰 기간 데이터 처리
        var period = selectedRow.cells[6].innerText.split(' ~ ');
        document.getElementById("editCouponStart").value = period[0].replace(/\./g, '-').trim();
        document.getElementById("editCouponEnd").value = period[1].replace(/\./g, '-').trim();
        document.getElementById("editMinPurchasePrice").value = ''; // 최소 구매 금액을 어디에서 가져올지 결정 필요

        editModal.style.display = "flex";
    } else {
        alert("수정할 쿠폰을 선택해주세요.");
    }
}

for (var i = 0; i < closeBtns.length; i++) {
    closeBtns[i].onclick = function() {
        registerModal.style.display = "none";
        editModal.style.display = "none";
    }
}

window.onclick = function(event) {
    if (event.target == registerModal) {
        registerModal.style.display = "none";
    }
    if (event.target == editModal) {
        editModal.style.display = "none";
    }
}