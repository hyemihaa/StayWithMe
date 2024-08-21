document.addEventListener('DOMContentLoaded', function () {
    const accommodationChartCtx = document.getElementById('accommodationChart').getContext('2d');
    const accommodationChart = new Chart(accommodationChartCtx, {
        type: 'bar',
        data: {
            labels: ['호텔', '리조트', '펜션'],
            datasets: [{
                label: '숙박 형태별 매출',
                data: [40, 30, 20],
                backgroundColor: ['#4BC0C0', '#36A2EB', '#FFCE56'],
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,  // 종횡비 유지
        }
    });

    const monthlySalesChartCtx = document.getElementById('monthlySalesChart').getContext('2d');
    const monthlySalesChart = new Chart(monthlySalesChartCtx, {
        type: 'line',
        data: {
            labels: ['01월', '02월', '03월', '04월', '05월', '06월', '07월', '08월'],
            datasets: [{
                label: '월별 매출',
                data: [20, 40, 60, 80, 100, 120, 140, 160],
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                fill: true,
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,  // 종횡비 유지
        }
    });
});
