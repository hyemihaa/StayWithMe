let rowCount = document.querySelectorAll('#rateTableBody tr').length;

document.getElementById('addRateType').addEventListener('click', function() {
    const newRow = `
        <tr>
            <td><input type="text" name="extraRates[${rowCount}].extraName" placeholder="요금타입 입력" class="text-center"></td>
            <td><input type="text" name="extraRates[${rowCount}].extraWeekdayRate" class="text-center"></td>
            <td><input type="text" name="extraRates[${rowCount}].extraFridayRate" class="text-center"></td>
            <td><input type="text" name="extraRates[${rowCount}].extraSaturdayRate" class="text-center"></td>
            <td><input type="text" name="extraRates[${rowCount}].extraSundayRate" class="text-center"></td>
            <td><button class="deleteRow btn btn-danger btn-sm">삭제</button></td>
        </tr>`;
    document.getElementById('rateTableBody').insertAdjacentHTML('beforeend', newRow);
    addDeleteEventListeners();
    rowCount++;
});

function addDeleteEventListeners() {
    const deleteButtons = document.querySelectorAll('.deleteRow');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            const row = button.closest('tr');
            row.remove();
            rowCount--;
        });
    });
}

addDeleteEventListeners();

// 데이터를 받아와서 테이블에 삽입하는 코드
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

            console.log(JSON.stringify(data, null, 2)); // 데이터를 확인합니다.

            // 기본 요금 출력
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

            // 추가 요금 출력
            // 여기서 data.extraRates[0].extraRates 배열을 탐색하여 렌더링
            if (data.extraRates[0].extraRates.length > 0) {
                data.extraRates[0].extraRates.forEach((item, index) => {
                    extraRatesHtml += `
                        <tr>
                            <td>${item.extraName || 'N/A'}</td>
                            <td><input type="text" name="extraRates[${index}].extraWeekdayRate" value="${item.extraWeekdayRate || ''}" class="text-center"></td>
                            <td><input type="text" name="extraRates[${index}].extraFridayRate" value="${item.extraFridayRate || ''}" class="text-center"></td>
                            <td><input type="text" name="extraRates[${index}].extraSaturdayRate" value="${item.extraSaturdayRate || ''}" class="text-center"></td>
                            <td><input type="text" name="extraRates[${index}].extraSundayRate" value="${item.extraSundayRate || ''}" class="text-center"></td>
                            <td><button type="button" class="deleteRow btn btn-danger btn-sm">삭제</button></td>
                        </tr>`;
                });
            }

            document.getElementById('rateTableBody').innerHTML = basicRatesHtml + extraRatesHtml;
            addDeleteEventListeners();  // 새로 추가된 삭제 버튼에도 리스너를 추가합니다.
        }
    };
    xhr.send();
}






