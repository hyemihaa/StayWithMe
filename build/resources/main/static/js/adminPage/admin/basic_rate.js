document.getElementById('addRateType').addEventListener('click', function() {
    const newRow = `
        <tr>
            <td><input type="text" placeholder="요금타입 입력"></td>
            <td><input type="text"></td>
            <td><input type="text"></td>
            <td><input type="text"></td>
            <td><input type="text"></td>
            <td><button class="deleteRow btn btn-danger btn-sm">삭제</button></td>
        </tr>`;
    document.getElementById('rateTableBody').insertAdjacentHTML('beforeend', newRow);
    addDeleteEventListeners();
});

function addDeleteEventListeners() {
    const deleteButtons = document.querySelectorAll('.deleteRow');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            const row = button.closest('tr');
            row.remove();
        });
    });
}

// 초기 페이지 로드 시 기존 행들에 대한 삭제 이벤트 리스너를 추가합니다.
addDeleteEventListeners();