document.addEventListener('DOMContentLoaded', function () {
    const calendarEl = document.querySelector('.calendar');
    const calendarHeader = document.getElementById("calendar-header");
    const today = new Date();
    const month = today.getMonth();
    const year = today.getFullYear();

    const monthNames = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"];
    calendarHeader.textContent = `${year}년 ${monthNames[month]}`;

    function getDaysInMonth(month, year) {
        return new Date(year, month + 1, 0).getDate();
    }

    function createCalendar(month, year) {
        const daysInMonth = getDaysInMonth(month, year);
        const startDay = new Date(year, month, 1).getDay();

        // 캘린더 초기화
        calendarEl.innerHTML = `
            <div class="calendar-header">${year}년 ${monthNames[month]}</div>
            <div class="calendar-day-names">
                <div class="calendar-day-name">월</div>
                <div class="calendar-day-name">화</div>
                <div class="calendar-day-name">수</div>
                <div class="calendar-day-name">목</div>
                <div class="calendar-day-name">금</div>
                <div class="calendar-day-name saturday">토</div>
                <div class="calendar-day-name sunday">일</div>
            </div>
        `;

        // 빈 칸 채우기
        for (let i = 0; i < startDay; i++) {
            const emptyCell = document.createElement('div');
            emptyCell.classList.add('calendar-day');
            calendarEl.appendChild(emptyCell);
        }

        // 날짜 채우기
        for (let day = 1; day <= daysInMonth; day++) {
            const dateCell = document.createElement('div');
            dateCell.classList.add('calendar-day');

            // 오늘 날짜 표시
            if (day === today.getDate() && month === today.getMonth() && year === today.getFullYear()) {
                dateCell.classList.add('today');
            }

            dateCell.innerHTML = `<div class="date">${day}</div>`;

            // 예시로 객실 상태 추가
            const roomStatus = document.createElement('span');
            roomStatus.classList.add('room-status');

            // 예시로 날짜에 따른 상태 부여 (실제 데이터에 따라 변경 필요)
            if (day % 3 === 0) {
                roomStatus.textContent = '예약 불가';
                roomStatus.classList.add('room-reserved');
            } else {
                roomStatus.textContent = '예약 가능';
                roomStatus.classList.add('room-available');
            }

            dateCell.appendChild(roomStatus);
            calendarEl.appendChild(dateCell);
        }
    }

    createCalendar(month, year);
});