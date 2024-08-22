document.addEventListener('DOMContentLoaded', function () {
    // 사용자 조회수, 예약 신청, 예약 취소, 결제 금액을 화면에 표시
    document.querySelector('.status-boxes .status-box:nth-child(1) p').innerText = viewsCount || 0;
    document.querySelector('.status-boxes .status-box:nth-child(2) p').innerText = bookingCount || 0;
    document.querySelector('.status-boxes .status-box:nth-child(3) p').innerText = cancellationCount || 0;
    document.querySelector('.status-boxes .status-box:nth-child(4) p').innerText = confirmedAmount || 0;

    // 밝고 명확한 색상 팔레트
    const colorPalette = [
        '#FF6384', // 밝은 빨간색
        '#36A2EB', // 밝은 파란색
        '#FFCE56', // 밝은 노란색
        '#4BC0C0', // 밝은 청록색
        '#9966FF', // 밝은 보라색
        '#FF9F40', // 밝은 주황색
    ];

    // 숙박 형태별 매출 현황 데이터를 파싱하여 차트에 사용할 데이터로 변환
    const accommodationDataParsed = JSON.parse(accommodationData);
    const accommodationLabels = accommodationDataParsed.map(item => item.accommodationType);
    const accommodationAmounts = accommodationDataParsed.map(item => item.reservationAmount);

    // 중복된 숙박 형태가 있으면 하나로 합쳐서 표시하도록 처리
    const uniqueAccommodationData = accommodationLabels.reduce((acc, label, index) => {
        if (acc[label]) {
            acc[label] += accommodationAmounts[index];
        } else {
            acc[label] = accommodationAmounts[index];
        }
        return acc;
    }, {});

    // 차트에 사용할 레이블과 데이터를 다시 설정
    const uniqueAccommodationLabels = Object.keys(uniqueAccommodationData);
    const uniqueAccommodationAmounts = Object.values(uniqueAccommodationData);

    // 숙박 형태별 매출 현황 차트 생성 (Bar Chart)
    const accommodationChartCtx = document.getElementById('accommodationChart').getContext('2d');
    new Chart(accommodationChartCtx, {
        type: 'bar',
        data: {
            labels: uniqueAccommodationLabels,
            datasets: [{
                label: '숙박 형태별 매출',
                data: uniqueAccommodationAmounts,
                backgroundColor: uniqueAccommodationLabels.map((_, index) => colorPalette[index % colorPalette.length]), // 색상 팔레트를 반복 적용
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true, // 종횡비 유지
            plugins: {
                legend: {
                    display: true,
                },
            },
        }
    });

    // 월별 매출 현황 데이터를 파싱하여 차트에 사용할 데이터로 변환
    const monthlySalesDataParsed = JSON.parse(monthlySalesData);

    // 숙소 타입별로 데이터를 분리하여 저장
    const accommodationTypes = [...new Set(monthlySalesDataParsed.map(item => item.accommodationType))];

    // 각 타입별로 월별 데이터를 계산
    const months = [...new Set(monthlySalesDataParsed.map(item => item.revenueMonth))];

    const calculateMonthlySales = (type, data, months) => {
        return months.map(month => {
            const found = data.find(item => item.revenueMonth === month && item.accommodationType === type);
            return found ? found.monthlyRevenue : 0;
        });
    };

    const datasets = accommodationTypes.map((type, index) => {
        return {
            label: `${type} 월별 매출`,
            data: calculateMonthlySales(type, monthlySalesDataParsed, months),
            borderColor: colorPalette[index % colorPalette.length], // 색상 팔레트를 반복 적용
            backgroundColor: colorPalette[index % colorPalette.length] + '33', // 색상에 투명도를 더해 배경색 지정
            fill: true,
            tension: 0.1
        };
    });

    // 월별 매출 현황 차트 생성 (Line Chart)
    const monthlySalesChartCtx = document.getElementById('monthlySalesChart').getContext('2d');
    new Chart(monthlySalesChartCtx, {
        type: 'line',
        data: {
            labels: months,
            datasets: datasets.length > 0 ? datasets : [{
                label: 'No Data',
                data: [],
                borderColor: '#ccc',
                fill: false,
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: function(value) {
                            return value.toLocaleString(); // y축의 값에 천단위 구분자 추가
                        }
                    }
                },
                x: {
                    type: 'time',
                    time: {
                        unit: 'month', // x축의 시간 단위를 월 단위로 설정
                        tooltipFormat: 'MMM yyyy',
                    }
                }
            }
        }
    });

    // 최근 매출 현황 테이블에 데이터 추가
    const recentSalesDataParsed = JSON.parse(recentSalesData);
    const tableBody = document.querySelector('.sales-table tbody');
    tableBody.innerHTML = ''; // 테이블 초기화
    if (recentSalesDataParsed.length > 0) {
        recentSalesDataParsed.forEach(item => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${item.reservationDate}</td>
                <td>${item.accommodationType}</td>
                <td>${item.bookingNo}</td>
                <td>${item.cancellationCount}</td>
                <td>${item.reservationAmount}</td>
            `;
            tableBody.appendChild(row);
        });
    } else {
        const row = document.createElement('tr');
        row.innerHTML = `<td colspan="5">데이터가 없습니다.</td>`;
        tableBody.appendChild(row);
    }
});
