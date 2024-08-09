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

// 초기 페이지 로드 시 기존 행들에 대한 삭제 이벤트 리스너를 추가합니다.
addDeleteEventListeners();

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

            console.log(data);

            // 기본 요금 출력
            if (data.basicRates) {
                basicRatesHtml += `
                    <tr>
                        <td>기본가</td>
                        <td><input type="text" name="weekdayRate" value="${data.basicRates.weekdayRate}" class="text-center"></td>
                        <td><input type="text" name="fridayRate" value="${data.basicRates.fridayRate}" class="text-center"></td>
                        <td><input type="text" name="saturdayRate" value="${data.basicRates.saturdayRate}" class="text-center"></td>
                        <td><input type="text" name="sundayRate" value="${data.basicRates.sundayRate}" class="text-center"></td>
                        <td><button type="button" class="deleteRow btn btn-danger btn-sm">삭제</button></td>
                    </tr>`;
            }

            // 추가 요금 출력
            data.extraRates.forEach(item => {
                extraRatesHtml += `
                    <tr>
                        <td>${item.extraName}</td>
                        <td><input type="text" name="extraWeekdayRate" value="${item.extraWeekdayRate}" class="text-center"></td>
                        <td><input type="text" name="extraFridayRate" value="${item.extraFridayRate}" class="text-center"></td>
                        <td><input type="text" name="extraSaturdayRate" value="${item.extraSaturdayRate}" class="text-center"></td>
                        <td><input type="text" name="extraSundayRate" value="${item.extraSundayRate}" class="text-center"></td>
                        <td><button type="button" class="deleteRow btn btn-danger btn-sm">삭제</button></td>
                    </tr>`;
            });

            document.getElementById('rateTableBody').innerHTML = basicRatesHtml + extraRatesHtml;
            addDeleteEventListeners();  // 새로 추가된 삭제 버튼에도 리스너를 추가합니다.
        }
    };
    xhr.send();
}

function addDeleteEventListeners() {
    const deleteButtons = document.querySelectorAll('.deleteRow');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            const row = button.closest('tr');
            row.remove();
        });
    });
}

