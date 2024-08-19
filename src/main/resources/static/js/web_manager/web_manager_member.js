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

document.addEventListener('DOMContentLoaded', function() {
    const managerTab = document.getElementById('manager-tab');
    const userTab = document.getElementById('user-tab');
    const searchInput = document.getElementById('searchInput');
    const managerTableBody = document.getElementById('managerTableBody');
    const userTableBody = document.getElementById('userTableBody');

    const managers = [
        { id: 'M001', name: 'Company A', contact: '010-1234-5678', address: 'Seoul' },
        { id: 'M002', name: 'Company B', contact: '010-2345-6789', address: 'Busan' }
    ];

    const users = [
        { number: 'U001', id: 'user01', contact: '010-3456-7890', name: 'John Doe', joinDate: '2022-01-01', leaveDate: '2023-01-01' },
        { number: 'U002', id: 'user02', contact: '010-4567-8901', name: 'Jane Doe', joinDate: '2022-02-01', leaveDate: '2023-02-01' }
    ];

    function renderTable(data, tableBody) {
        tableBody.innerHTML = '';
        data.forEach(item => {
            const row = document.createElement('tr');
            for (const key in item) {
                const cell = document.createElement('td');
                cell.textContent = item[key];
                row.appendChild(cell);
            }
            tableBody.appendChild(row);
        });
    }

    searchInput.addEventListener('input', function() {
        const searchTerm = searchInput.value.toLowerCase();
        if (managerTab.classList.contains('active')) {
            const filteredManagers = managers.filter(manager => manager.id.toLowerCase().includes(searchTerm));
            renderTable(filteredManagers, managerTableBody);
        } else if (userTab.classList.contains('active')) {
            const filteredUsers = users.filter(user => user.id.toLowerCase().includes(searchTerm));
            renderTable(filteredUsers, userTableBody);
        }
    });

    managerTab.addEventListener('click', function() {
        renderTable(managers, managerTableBody);
    });

    userTab.addEventListener('click', function() {
        renderTable(users, userTableBody);
    });

    // Initially render manager table
    renderTable(managers, managerTableBody);
});