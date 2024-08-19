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


    // Worldwide Sales Chart
    var ctx1 = $("#worldwide-sales").get(0).getContext("2d");
    var myChart1 = new Chart(ctx1, {
        type: "bar",
        data: {
            labels: ["숙박 현황"],
            datasets: [{
                    label: "호텔",
                    data: [150],
                    backgroundColor: "rgba(0, 156, 255, .7)"
                },
                {
                    label: "리조트",
                    data: [65],
                    backgroundColor: "rgba(0, 156, 255, .5)"
                },
                {
                    label: "펜션",
                    data: [37],
                    backgroundColor: "rgba(0, 156, 255, .3)"
                }
            ]
            },
        options: {
            responsive: true
        }
    });


    // Salse & Revenue Chart
    var ctx2 = $("#salse-revenue").get(0).getContext("2d");
    var myChart2 = new Chart(ctx2, {
        type: "line",
        data: {
            labels: ["01월", "02월", "03월", "04월", "05월", "06월", "07월", "08월"],
            datasets: [{
                    label: "호텔",
                    data: [100, 117, 97, 85, 102, 109, 125, 143],
                    backgroundColor: "rgba(0, 156, 255, .1)",
                    fill: true
                },
                {
                    label: "리조트",
                    data: [70, 61, 43, 55, 49, 77, 69, 65],
                    backgroundColor: "rgba(0, 156, 255, .3)",
                    fill: true
                },
                {
                    label: "펜션",
                    data: [30, 27, 29, 37, 22, 31, 35, 37],
                    backgroundColor: "rgba(0, 156, 255, .5)",
                    fill: true
                }
            ]
            },
        options: {
            responsive: true
        }
    });

})(jQuery);

