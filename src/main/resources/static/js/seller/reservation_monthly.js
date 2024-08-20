let currentYear = new Date().getFullYear();
let currentMonth = new Date().getMonth();

// 달력을 생성하는 함수
function generateCalendar(year, month) {
    const today = new Date();
    const firstDay = new Date(year, month, 1); // 해당 월의 첫 번째 날
    const lastDay = new Date(year, month + 1, 0); // 해당 월의 마지막 날

    const calendarBody = document.getElementById('calendar-body'); // 달력의 tbody 요소 가져오기
    calendarBody.innerHTML = ''; // 이전 달력 내용을 초기화

    let date = 1; // 달력에 표시할 첫 번째 날짜 설정
    for (let i = 0; i < 6; i++) { // 최대 6주 (행)까지 반복
        const row = document.createElement('tr'); // 새로운 행(tr) 생성

        for (let j = 0; j < 7; j++) { // 7일(열) 반복
            if (i === 0 && j < firstDay.getDay()) { // 첫 번째 주에서 시작 요일 이전은 빈 셀로 처리
                const cell = document.createElement('td');
                row.appendChild(cell);
            } else if (date > lastDay.getDate()) { // 마지막 날짜 이후는 빈 셀로 남기기
                break;
            } else {
                const cell = document.createElement('td'); // 날짜 셀 생성
                cell.textContent = date; // 셀에 날짜 추가

                // 오늘 날짜와 일치하는 경우 강조 표시
                if (year === today.getFullYear() && month === today.getMonth() && date === today.getDate()) {
                    cell.classList.add('today'); // 'today' 클래스 추가하여 스타일 적용
                }

                row.appendChild(cell); // 행에 셀 추가
                date++; // 날짜 증가
            }
        }

        calendarBody.appendChild(row); // 완성된 행을 tbody에 추가
    }

    // 현재 선택된 월을 표시
    document.getElementById('current-month').textContent = `${year}년 ${month + 1}월`;
}

// 이전 달로 이동
document.getElementById('prev-month').addEventListener('click', () => {
    if (currentMonth === 0) {
        currentMonth = 11;
        currentYear--;
    } else {
        currentMonth--;
    }
    generateCalendar(currentYear, currentMonth);
});

// 다음 달로 이동
document.getElementById('next-month').addEventListener('click', () => {
    if (currentMonth === 11) {
        currentMonth = 0;
        currentYear++;
    } else {
        currentMonth++;
    }
    generateCalendar(currentYear, currentMonth);
});

// 페이지가 로드될 때 자동으로 달력을 생성
window.onload = () => generateCalendar(currentYear, currentMonth);
