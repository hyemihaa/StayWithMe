<script th:inline="javascript">
    document.addEventListener('DOMContentLoaded', function () {
        // 사용자 조회수, 예약 신청, 예약 취소, 결제 금액
        const viewsCount = /*[[${viewsCount}]]*/ 0;
        const bookingCount = /*[[${bookingCount}]]*/ 0;
        const cancellationCount = /*[[${cancellationCount}]]*/ 0;
        const confirmedAmount = /*[[${confirmedAmount}]]*/ 0;

        // 차트 및 테이블에 데이터 반영
        document.querySelector('.status-boxes .status-box:nth-child(1) p').innerText = viewsCount;
        document.querySelector('.status-boxes .status-box:nth-child(2) p').innerText = bookingCount;
        document.querySelector('.status-boxes .status-box:nth-child(3) p').innerText = cancellationCount;
        document.querySelector('.status-boxes .status-box:nth-child(4) p').innerText = confirmedAmount;

        // 숙박 형태별 매출 현황
        const accommodationData = /*[[${accommodationData}]]*/ '[]';
        const accommodationDataParsed = JSON.parse(accommodationData);
        const accommodationLabels = accommodationDataParsed.map(item => item.accommodationType);
        const accommodationAmounts = accommodationDataParsed.map(item => item.reservationAmount);

        const accommodationChartCtx = document.getElementById('accommodationChart').getContext('2d');
        new Chart(accommodationChartCtx, {
            type: 'bar',
            data: {
                labels: accommodationLabels,
                datasets: [{
                    label: '숙박 형태별 매출',
                    data: accommodationAmounts,
                    backgroundColor: ['#4BC0C0', '#36A2EB', '#FFCE56'],
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,  // 종횡비 유지
            }
        });

        // 월별 매출 현황
        const monthlySalesData = /*[[${monthlySalesData}]]*/ '[]';
        const monthlySalesDataParsed = JSON.parse(monthlySalesData);
        const monthlyLabels = monthlySalesDataParsed.map(item => item.reservationDate);
        const monthlyAmounts = monthlySalesDataParsed.map(item => item.reservationAmount);

        const monthlySalesChartCtx = document.getElementById('monthlySalesChart').getContext('2d');
        new Chart(monthlySalesChartCtx, {
            type: 'line',
            data: {
                labels: monthlyLabels,
                datasets: [{
                    label: '월별 매출',
                    data: monthlyAmounts,
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

        // 최근 매출 현황 테이블에 데이터 추가
        const recentSalesData = /*[[${recentSalesData}]]*/ '[]';
        const recentSalesDataParsed = JSON.parse(recentSalesData);
        const tableBody = document.querySelector('.sales-table tbody');
        recentSalesDataParsed.forEach(item => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${item.reservationDate}</td>
                <td>${item.accommodationType}</td>
                <td>${item.bookingNo}</td>
                <td>${item.reservationAmount}</td>
                <td>${item.bookingNo - (item.reservationAmount / 100000)}</td>  <!-- 예제용 계산 -->
                <td>${item.reservationAmount - (item.bookingNo - (item.reservationAmount / 100000))}</td>
            `;
            tableBody.appendChild(row);
        });
    });
</script>
