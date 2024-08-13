// 현재 테이블의 행 수를 계산하여 초기화
let rowCount = document.querySelectorAll('#rateTableBody tr').length;

// '요금타입 추가' 버튼 클릭 시 새 요금타입 행을 추가하는 이벤트 리스너
document.getElementById('addRateType').addEventListener('click', function() {
    addRateRow(rowCount); // 새 행을 추가하는 함수 호출
    rowCount++; // 행 수 증가
});

// 새 요금타입 행을 추가하는 함수
function addRateRow(index) {
    // 새 행의 HTML 코드를 작성
    const newRow = `
        <tr>
            <td><input type="text" name="extraRates[${index}].extraName" placeholder="요금타입 입력" class="text-center"></td>
            <td><input type="text" name="extraRates[${index}].extraWeekdayRate" class="text-center"></td>
            <td><input type="text" name="extraRates[${index}].extraFridayRate" class="text-center"></td>
            <td><input type="text" name="extraRates[${index}].extraSaturdayRate" class="text-center"></td>
            <td><input type="text" name="extraRates[${index}].extraSundayRate" class="text-center"></td>
            <td><button class="deleteRow btn btn-danger btn-sm">삭제</button></td>
        </tr>`;
    // 작성한 행을 테이블 본문에 추가
    document.getElementById('rateTableBody').insertAdjacentHTML('beforeend', newRow);
    addDeleteEventListeners(); // 새로 추가된 삭제 버튼에 대한 이벤트 리스너 추가
}

// 삭제 버튼에 대한 이벤트 리스너 추가 함수
function addDeleteEventListeners() {
    // 모든 삭제 버튼을 선택
    const deleteButtons = document.querySelectorAll('.deleteRow');
    // 각 삭제 버튼에 클릭 이벤트 리스너 추가
    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            const row = button.closest('tr'); // 클릭된 버튼이 속한 행을 찾음
            row.remove(); // 해당 행을 테이블에서 제거
            updateRowIndices(); // 행이 삭제된 후, 남은 행들의 인덱스를 업데이트
        });
    });
}

// 테이블에서 행이 삭제되거나 추가될 때, 각 행의 인덱스를 업데이트하는 함수
function updateRowIndices() {
    const rows = document.querySelectorAll('#rateTableBody tr'); // 모든 행을 선택
    rows.forEach((row, index) => {
        const inputs = row.querySelectorAll('input'); // 각 행의 모든 input 요소를 선택
        inputs.forEach(input => {
            const name = input.getAttribute('name'); // 현재 input의 name 속성을 가져옴
            const newName = name.replace(/\[\d+\]/, `[${index}]`); // 인덱스를 현재 행의 순서로 대체
            input.setAttribute('name', newName); // 업데이트된 name 속성을 다시 설정
        });
    });
    rowCount = rows.length; // 현재 행 수를 다시 설정
}

// 폼 제출 전에 빈 행을 제거하는 함수
document.getElementById('rateForm').addEventListener('submit', function(event) {
    const rows = document.querySelectorAll('#rateTableBody tr'); // 모든 행을 선택

    rows.forEach(row => {
        const inputs = row.querySelectorAll('input[type="text"]'); // 각 행의 모든 텍스트 입력 요소를 선택
        let isEmptyRow = true; // 빈 행인지 여부를 추적

        inputs.forEach(input => {
            if (input.value.trim() !== '') {
                isEmptyRow = false; // 입력 값이 있는 경우 빈 행이 아님
            }
        });

        if (isEmptyRow) {
            row.remove(); // 빈 행인 경우 해당 행을 제거
        }
    });
});

// 방 이름에 따라 요금 데이터를 로드하여 테이블에 삽입하는 함수
function loadRoomRates(roomName) {
    if (roomName === "") {
        document.getElementById('rateTableBody').innerHTML = ""; // 방 이름이 비어있으면 테이블을 비움
        return;
    }

    const xhr = new XMLHttpRequest(); // 새 XMLHttpRequest 객체 생성
    xhr.open('GET', `/getRoomRates?roomName=${roomName}`, true); // 서버에 GET 요청 설정
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) { // 서버 응답이 완료되었을 때
            const data = JSON.parse(xhr.responseText); // 응답 데이터를 JSON으로 파싱

            let basicRatesHtml = "";
            let extraRatesHtml = "";

            console.log(JSON.stringify(data, null, 2)); // 데이터를 확인하기 위해 콘솔에 출력

            // 기본 요금 행 생성
            if (data.basicRates) {
                basicRatesHtml += `
                    <tr>
                        <td>기본가</td>
                        <td><input type="text" name="weekdayRate" value="${data.basicRates.weekdayRate || ''}" class="text-center"></td>
                        <td><input type="text" name="fridayRate" value="${data.basicRates.fridayRate || ''}" class="text-center"></td>
                        <td><input type="text" name="saturdayRate" value="${data.basicRates.saturdayRate || ''}" class="text-center"></td>
                        <td><input type="text" name="sundayRate" value="${data.basicRates.sundayRate || ''}" class="text-center"></td>
                        <td><button type="button" class="deleteRow btn btn-danger btn-sm">삭제</button></td>
                    </tr>`;
            }

            // 추가 요금 행 생성
            if (data.extraRates && data.extraRates[0].extraRates.length > 0) {
                data.extraRates[0].extraRates.forEach((item, index) => {
                    extraRatesHtml += `
                        <tr>
                            <td><input type="text" name="extraRates[${index}].extraName" value="${item.extraName || ''}" class="text-center"></td>
                            <td><input type="text" name="extraRates[${index}].extraWeekdayRate" value="${item.extraWeekdayRate || ''}" class="text-center"></td>
                            <td><input type="text" name="extraRates[${index}].extraFridayRate" value="${item.extraFridayRate || ''}" class="text-center"></td>
                            <td><input type="text" name="extraRates[${index}].extraSaturdayRate" value="${item.extraSaturdayRate || ''}" class="text-center"></td>
                            <td><input type="text" name="extraRates[${index}].extraSundayRate" value="${item.extraSundayRate || ''}" class="text-center"></td>
                            <td><button type="button" class="deleteRow btn btn-danger btn-sm">삭제</button></td>
                            <input type="hidden" name="extraRates[${index}].extraDateStart" value="${item.extraDateStart || ''}">
                            <input type="hidden" name="extraRates[${index}].extraDateEnd" value="${item.extraDateEnd || ''}">
                        </tr>`;
                });
            }

            document.getElementById('rateTableBody').innerHTML = basicRatesHtml + extraRatesHtml; // 테이블 본문에 생성된 HTML 삽입
            addDeleteEventListeners(); // 새로 추가된 삭제 버튼에 대한 이벤트 리스너 추가
            rowCount = document.querySelectorAll('#rateTableBody tr').length; // 현재 테이블의 행 수를 다시 설정
        }
    };
    xhr.send(); // 서버에 요청 전송
}
