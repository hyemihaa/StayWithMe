let rowCount = document.querySelectorAll('#rateTableBody tr').length;

document.getElementById('addRateType').addEventListener('click', function() {
    const newRow = `
        <tr>
            <td><input type="text" name="extraName" placeholder="요금타입 입력" class="text-center"></td>
            <td><input type="text" name="extraWeekdayRate" class="text-center"></td>
            <td><input type="text" name="extraFridayRate" class="text-center"></td>
            <td><input type="text" name="extraSaturdayRate" class="text-center"></td>
            <td><input type="text" name="extraSundayRate" class="text-center"></td>
            <td><button class="deleteRow btn btn-danger btn-sm">삭제</button></td>
        </tr>`;
    document.getElementById('rateTableBody').insertAdjacentHTML('beforeend', newRow);
    addDeleteEventListeners();
    rowCount++;  // 행의 개수를 증가시켜 다음 행의 인덱스를 준비합니다.
});



function addDeleteEventListeners() {
    const deleteButtons = document.querySelectorAll('.deleteRow');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            const row = button.closest('tr');
            row.remove();
            rowCount--; // 행이 삭제될 때 행의 개수를 줄입니다.
        });
    });
}

// 초기 페이지 로드 시 기존 행들에 대한 삭제 이벤트 리스너를 추가합니다.
addDeleteEventListeners();