// 기존 테이블의 행 수를 계산하여 초기화
let rowCount = document.querySelectorAll('#rateTableBody tr').length;

// '요금타입 추가' 버튼 클릭 시 새 요금타입 행을 추가하는 이벤트 리스너
document.getElementById('addRateType').addEventListener('click', function() {
    addRateRow(rowCount); // 새 행을 추가하는 함수 호출
    rowCount++; // 행 수 증가
});

// 새 요금타입 행을 추가하는 함수
function addRateRow(index) {
    const newRow = `
        <tr>
            <td><input type="text" name="extraRates[${index}].extraName" placeholder="요금타입 입력" class="text-center"></td>
            <td><input type="text" name="extraRates[${index}].extraWeekdayRate" class="text-center"></td>
            <td><input type="text" name="extraRates[${index}].extraFridayRate" class="text-center"></td>
            <td><input type="text" name="extraRates[${index}].extraSaturdayRate" class="text-center"></td>
            <td><input type="text" name="extraRates[${index}].extraSundayRate" class="text-center"></td>
            <td><button class="deleteRow btn btn-danger btn-sm" data-extra-name=""><!-- 삭제 -->삭제</button></td>
        </tr>`;
    document.getElementById('rateTableBody').insertAdjacentHTML('beforeend', newRow);
    addDeleteEventListeners(); // 새로 추가된 삭제 버튼에 대한 이벤트 리스너 추가
}

// 삭제 버튼에 대한 이벤트 리스너 추가 함수
function addDeleteEventListeners() {
    const deleteButtons = document.querySelectorAll('.deleteRow');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            const extraName = button.getAttribute('data-extra-name');
            if (extraName) {
                fetch(`/extra-delete?extraName=${extraName}`, { method: 'DELETE' })
                    .then(response => response.json())
                    .then(result => {
                        if (result.success) {
                            button.closest('tr').remove(); // UI에서 행 제거
                        } else {
                            alert('삭제에 실패했습니다.');
                        }
                    })
                    .catch(error => {
                        console.error("삭제 요청 중 오류가 발생했습니다.", error);
                    });
            } else {
                button.closest('tr').remove(); // extraName이 없는 경우, 단순히 행만 제거
            }
            updateRowIndices(); // 행이 삭제된 후, 남은 행들의 인덱스를 업데이트
        });
    });
}

// 테이블에서 행이 삭제되거나 추가될 때, 각 행의 인덱스를 업데이트하는 함수
function updateRowIndices() {
    const rows = document.querySelectorAll('#rateTableBody tr');
    rows.forEach((row, index) => {
        const inputs = row.querySelectorAll('input');
        inputs.forEach(input => {
            const name = input.getAttribute('name');
            const newName = name.replace(/\[\d+\]/, `[${index}]`);
            input.setAttribute('name', newName);
        });
    });
    rowCount = rows.length;
}

// 폼 제출 전에 빈 행을 제거하는 함수
document.getElementById('rateForm').addEventListener('submit', function(event) {
    const rows = document.querySelectorAll('#rateTableBody tr');
    rows.forEach(row => {
        const inputs = row.querySelectorAll('input[type="text"]');
        let isEmptyRow = true;
        inputs.forEach(input => {
            if (input.value.trim() !== '') {
                isEmptyRow = false;
            }
        });
        if (isEmptyRow) {
            row.remove();
        }
    });
});

// 방 이름에 따라 요금 데이터를 로드하여 테이블에 삽입하는 함수
function loadRoomRates(roomName) {
    if (roomName === "") {
        document.getElementById('rateTableBody').innerHTML = "";
        return;
    }

    const xhr = new XMLHttpRequest();
    xhr.open('GET', `/getRoomRates?roomName=${roomName}`, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);

            let basicRatesHtml = "";
            let extraRatesHtml = "";

            console.log(JSON.stringify(data, null, 2));

            if (data.basicRates) {
                basicRatesHtml += `
                    <tr>
                        <td>기본가</td>
                        <td><input type="text" name="weekdayRate" value="${data.basicRates.weekdayRate || ''}" class="text-center"></td>
                        <td><input type="text" name="fridayRate" value="${data.basicRates.fridayRate || ''}" class="text-center"></td>
                        <td><input type="text" name="saturdayRate" value="${data.basicRates.saturdayRate || ''}" class="text-center"></td>
                        <td><input type="text" name="sundayRate" value="${data.basicRates.sundayRate || ''}" class="text-center"></td>
                    </tr>`;
            }

            if (data.extraRates && data.extraRates[0].extraRates.length > 0) {
                data.extraRates[0].extraRates.forEach((item, index) => {
                    extraRatesHtml += `
                        <tr>
                            <td><input type="text" name="extraRates[${index}].extraName" value="${item.extraName || ''}" class="text-center"></td>
                            <td><input type="text" name="extraRates[${index}].extraWeekdayRate" value="${item.extraWeekdayRate || ''}" class="text-center"></td>
                            <td><input type="text" name="extraRates[${index}].extraFridayRate" value="${item.extraFridayRate || ''}" class="text-center"></td>
                            <td><input type="text" name="extraRates[${index}].extraSaturdayRate" value="${item.extraSaturdayRate || ''}" class="text-center"></td>
                            <td><input type="text" name="extraRates[${index}].extraSundayRate" value="${item.extraSundayRate || ''}" class="text-center"></td>
                            <td><button type="button" class="deleteRow btn btn-danger btn-sm" data-extra-name="${item.extraName}">삭제</button></td>
                            <input type="hidden" name="extraRates[${index}].extraDateStart" value="${item.extraDateStart || ''}">
                            <input type="hidden" name="extraRates[${index}].extraDateEnd" value="${item.extraDateEnd || ''}">
                        </tr>`;
                });
            }

            document.getElementById('rateTableBody').innerHTML = basicRatesHtml + extraRatesHtml;
            addDeleteEventListeners();
            rowCount = document.querySelectorAll('#rateTableBody tr').length;
        }
    };
    xhr.send();
}

