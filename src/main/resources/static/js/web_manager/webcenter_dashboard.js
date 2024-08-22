import { enUS } from 'date-fns/locale'; // date-fns에서 로케일 가져오기

document.addEventListener('DOMContentLoaded', function () {
    // 사용자 조회수, 예약 신청, 예약 취소, 결제 금액을 화면에 표시
    document.querySelector('.status-boxes .status-box:nth-child(1) p').innerText = viewsCount || 0;
    document.querySelector('.status-boxes .status-box:nth-child(2) p').innerText = bookingCount || 0;
    document.querySelector('.status-boxes .status-box:nth-child(3) p').innerText = cancellationCount || 0;
    document.querySelector('.status-boxes .status-box:nth-child(4) p').innerText = confirmedAmount || 0;

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
                backgroundColor: ['#4BC0C0', '#36A2EB', '#FFCE56'], // 색상 지정
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true, // 종횡비 유지
        }
    });

    // 월별 매출 현황 데이터를 파싱하여 차트에 사용할 데이터로 변환
    const monthlySalesDataParsed = JSON.parse(monthlySalesData);

    // 숙소 타입별로 데이터를 분리하여 저장
    const hotelData = monthlySalesDataParsed.filter(item => item.accommodationType === '호텔');
    const resortData = monthlySalesDataParsed.filter(item => item.accommodationType === '리조트');

    // 각 타입별로 월별 데이터를 계산
    const months = [...new Set(monthlySalesDataParsed.map(item => item.reservationDate.substring(0, 7)))];

    const calculateMonthlySales = (data, months) => {
        return months.map(month => {
            const found = data.find(item => item.reservationDate.startsWith(month));
            return found ? found.reservationAmount : 0;
        });
    };

    const hotelMonthlyAmounts = calculateMonthlySales(hotelData, months);
    const resortMonthlyAmounts = calculateMonthlySales(resortData, months);

    // 월별 매출 현황 차트 생성 (Line Chart)
    const monthlySalesChartCtx = document.getElementById('monthlySalesChart').getContext('2d');
    new Chart(monthlySalesChartCtx, {
        type: 'line',
        data: {
            labels: months,
            datasets: [
                {
                    label: '호텔 월별 매출',
                    data: hotelMonthlyAmounts,
                    borderColor: '#4BC0C0',
                    fill: false,
                    tension: 0.1
                },
                {
                    label: '리조트 월별 매출',
                    data: resortMonthlyAmounts,
                    borderColor: '#FFCE56',
                    fill: false,
                    tension: 0.1
                }
            ]
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
                    },
                    adapters: {
                        date: {
                            locale: enUS, // 적절한 로케일 설정
                        },
                    }
                }
            }
        }
    });

    // 최근 매출 현황 테이블에 데이터 추가
    const recentSalesDataParsed = JSON.parse(recentSalesData);
    const tableBody = document.querySelector('.sales-table tbody');
    recentSalesDataParsed.forEach(item => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${item.reservationDate}</td>
            <td>${item.accommodationType}</td>
            <td>${item.bookingNo}</td>
            <td>${item.reservationAmount}</td>
            <td>${item.cancellationCount}</td>
            <td>${item.reservationAmount}</td>
        `;
        tableBody.appendChild(row);
    });
});
