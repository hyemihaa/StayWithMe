document.addEventListener('DOMContentLoaded', function () {
    const selectedDateElement = document.getElementById('selected-date');
    let selectedDate = new Date(selectedDateElement.textContent);

    // 날짜를 YYYY-MM-DD 형식으로 포맷팅하는 함수
    function formatDate(date) {
        let year = date.getFullYear();
        let month = ('0' + (date.getMonth() + 1)).slice(-2);
        let day = ('0' + date.getDate()).slice(-2);
        return `${year}-${month}-${day}`;
    }

    // 이전 날로 이동
    document.getElementById('prev-day').addEventListener('click', function () {
        selectedDate.setDate(selectedDate.getDate() - 1);
        selectedDateElement.textContent = formatDate(selectedDate);
        loadDataForDate(formatDate(selectedDate));
    });

    // 다음 날로 이동
    document.getElementById('next-day').addEventListener('click', function () {
        selectedDate.setDate(selectedDate.getDate() + 1);
        selectedDateElement.textContent = formatDate(selectedDate);
        loadDataForDate(formatDate(selectedDate));
    });

    // 날짜를 클릭하면 캘린더를 통해 날짜를 선택할 수 있게 함
    selectedDateElement.addEventListener('click', function () {
        const newDate = prompt("날짜를 선택하세요 (YYYY-MM-DD 형식으로 입력):", formatDate(selectedDate));
        if (newDate) {
            selectedDate = new Date(newDate);
            selectedDateElement.textContent = formatDate(selectedDate);
            loadDataForDate(formatDate(selectedDate));
        }
    });

    // 선택된 날짜에 대한 데이터를 서버에서 로드하는 함수
    function loadDataForDate(date) {
        window.location.href = `/reservation-daily.do?date=${date}`;
    }
});
