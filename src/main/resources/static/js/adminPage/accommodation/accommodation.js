
let roomCount = 0;

document.getElementById('addRoomBtn').addEventListener('click', function() {
    roomCount++;
    var roomContainer = document.createElement('div');
    roomContainer.classList.add('room-container');
    roomContainer.setAttribute('id', `room-${roomCount}`);

    roomContainer.innerHTML = `
                <div class="row mb-3">
                    <label for="roomName-${roomCount}" class="col-sm-2 col-form-label">객실 이름</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="roomName-${roomCount}">
                    </div>
                </div>
                <table class="table table-bordered text-center">
                    <thead>
                        <tr>
                            <th>요금타입</th>
                            <th>주중</th>
                            <th>금요일</th>
                            <th>토요일</th>
                            <th>일요일</th>
                            <th>비고</th>
                        </tr>
                    </thead>
                    <tbody id="rateTableBody-${roomCount}">
                        <tr>
                            <td>기본금액</td>
                            <td><input type="text" class="form-control"></td>
                            <td><input type="text" class="form-control"></td>
                            <td><input type="text" class="form-control"></td>
                            <td><input type="text" class="form-control"></td>
                            <td><button class="btn btn-save btn-sm">저장</button></td>
                        </tr>
                    </tbody>
                </table>

                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">카테고리 선택 (단일 선택)</label>
                    <div class="col-sm-10 tags">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="type-${roomCount}" id="type1-${roomCount}" data-tag="오션뷰">
                            <label class="form-check-label" for="type1-${roomCount}">오션뷰</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="type-${roomCount}" id="type2-${roomCount}" data-tag="리버뷰">
                            <label class="form-check-label" for="type2-${roomCount}">리버뷰</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="type-${roomCount}" id="type3-${roomCount}" data-tag="시티뷰">
                            <label class="form-check-label" for="type3-${roomCount}">시티뷰</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="type-${roomCount}" id="type4-${roomCount}" data-tag="온돌방">
                            <label class="form-check-label" for="type4-${roomCount}">온돌방</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="type-${roomCount}" id="type5-${roomCount}" data-tag="마운틴뷰">
                            <label class="form-check-label" for="type5-${roomCount}">마운틴뷰</label>
                        </div>
                    </div>
                </div>
                
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">최대인원</label>
                    <div class="col-sm-10 d-flex align-items-center">
                        <button class="btn btn-secondary decrement" data-room-id="${roomCount}">-</button>
                        <input type="text" class="form-control text-center mx-2 maxPeople" id="maxPeople-${roomCount}" value="1" readonly>
                        <button class="btn btn-secondary increment" data-room-id="${roomCount}">+</button>
                    </div>
                </div>
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">이미지 업로드</label>
                    <div class="col-sm-10">
                        <input type="file" class="form-control image-upload" id="imageUpload-${roomCount}" multiple data-room-id="${roomCount}">
                        <div class="image-preview" id="imagePreview-${roomCount}"></div>
                    </div>
                </div>
                <button class="btn btn-delete" data-room-id="${roomCount}">삭제</button>
            `;

    document.getElementById('roomsContainer').appendChild(roomContainer);

    // Add event listener for the save/edit button
    roomContainer.querySelector('.btn-save').addEventListener('click', function() {
        var row = this.closest('tr');
        var inputs = row.querySelectorAll('input');

        if (this.classList.contains('btn-save')) {
            // Save logic
            inputs.forEach(function(input) {
                input.disabled = true;
            });
            this.textContent = '수정';
            this.classList.remove('btn-save');
            this.classList.add('btn-edit');
        } else if (this.classList.contains('btn-edit')) {
            // Edit logic
            inputs.forEach(function(input) {
                input.disabled = false;
            });
            this.textContent = '저장';
            this.classList.remove('btn-edit');
            this.classList.add('btn-save');
        }
    });
});

// 인원 증가/감소 버튼의 이벤트 리스너 추가
document.addEventListener('click', function(event) {
    if (event.target && event.target.classList.contains('decrement')) {
        var roomId = event.target.getAttribute('data-room-id');
        var maxPeopleInput = document.getElementById('maxPeople-' + roomId);
        var currentValue = parseInt(maxPeopleInput.value);
        if (currentValue > 1) {
            maxPeopleInput.value = currentValue - 1;
        }
    }

    if (event.target && event.target.classList.contains('increment')) {
        var roomId = event.target.getAttribute('data-room-id');
        var maxPeopleInput = document.getElementById('maxPeople-' + roomId);
        var currentValue = parseInt(maxPeopleInput.value);
        maxPeopleInput.value = currentValue + 1;
    }

    // 객실 삭제 버튼 클릭 시 해당 객실 삭제
    if (event.target && event.target.classList.contains('btn-delete')) {
        var roomId = event.target.getAttribute('data-room-id');
        var roomContainer = document.getElementById('room-' + roomId);
        roomContainer.remove();
    }
});

// 이미지 업로드 미리보기
document.addEventListener('change', function(event) {
    if (event.target && event.target.classList.contains('image-upload')) {
        var roomId = event.target.getAttribute('data-room-id');
        var imagePreview = document.getElementById('imagePreview-' + roomId);
        imagePreview.innerHTML = '';

        Array.from(event.target.files).forEach(function(file) {
            var reader = new FileReader();
            reader.onload = function(e) {
                var img = document.createElement('img');
                img.src = e.target.result;
                imagePreview.appendChild(img);
            };
            reader.readAsDataURL(file);
        });
    }
});

// 태그 추가하면 자동으로 입력
document.querySelectorAll('.tag input[type="checkbox"]').forEach(function (checkbox) {
    checkbox.addEventListener('change', function () {
        var subFacilityInput = document.getElementById('sub-facility');
        var tagValue = this.getAttribute('data-tag');

        if (this.checked) {
            if (subFacilityInput.value) {
                subFacilityInput.value += ', ' + tagValue;
            } else {
                subFacilityInput.value = tagValue;
            }
        } else {
            var values = subFacilityInput.value.split(', ');
            values = values.filter(function (value) {
                return value !== tagValue;
            });
            subFacilityInput.value = values.join(', ');
        }
    });
});

document.querySelectorAll('.tag input[type="radio"]').forEach(function (radio) {
    radio.addEventListener('change', function () {
        var typeInput = document.getElementById('type');
        typeInput.value = this.getAttribute('data-tag');
    });
});

// 객실 금액 저장 / 수정 버튼
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('#rateTableBody .btn-save').forEach(function(button) {
        button.addEventListener('click', function() {
            var row = this.closest('tr');
            var inputs = row.querySelectorAll('input');

            if (this.classList.contains('btn-save')) {
                // Save logic
                inputs.forEach(function(input) {
                    input.disabled = true;
                });
                this.textContent = '수정';
                this.classList.remove('btn-save');
                this.classList.add('btn-edit');
            } else if (this.classList.contains('btn-edit')) {
                // Edit logic
                inputs.forEach(function(input) {
                    input.disabled = false;
                });
                this.textContent = '저장';
                this.classList.remove('btn-edit');
                this.classList.add('btn-save');
            }
        });
    });
});


