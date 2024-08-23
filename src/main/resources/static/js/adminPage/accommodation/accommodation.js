
let roomCount = 0;

// 각 객실에 대한 파일 배열을 관리하기 위한 객체
let roomFiles = {};
let endIndex = [];


document.getElementById('addRoomBtn').addEventListener('click', function() {
    roomCount ++;
    var roomContainer = document.createElement('div');
    roomContainer.classList.add('room-container');
    roomContainer.setAttribute('id', `room-${roomCount}`);

    roomContainer.innerHTML = `
        <div class="row mb-3">
            <input type="hidden" th:name="www">
            <label for="roomName-${roomCount}" class="col-sm-2 col-form-label">객실 이름</label>
            <input type="hidden" name="roomCount"  value="${roomCount}">
            <div class="col-sm-10">
                <input type="text" class="form-control" id="roomName" name="roomName" value="" required> 
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
                </tr>
            </thead>
            <tbody id="rateTableBody-${roomCount}">
                <tr>
                    <td>기본금액</td>
                    <td><input type="text" class="form-control" name="weekdayRate" value="" required></td>
                    <td><input type="text" class="form-control" name="fridayRate" value="" required></td>
                    <td><input type="text" class="form-control" name="saturdayRate" value="" required></td>
                    <td><input type="text" class="form-control" name="sundayRate" value="" required></td>
                </tr>
            </tbody>
        </table>
        <!-- 체크인/체크아웃 시간 입력 필드 추가 -->

        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">체크인 시간</label>
            <div class="col-sm-4">
                <input type="time" class="form-control" name="checkInTime-${roomCount}" id="checkInTime-${roomCount}" required step="1800">
            </div>
            <label class="col-sm-2 col-form-label">체크아웃 시간</label>
            <div class="col-sm-4">
                <input type="time" class="form-control" name="checkOutTime-${roomCount}" id="checkOutTime-${roomCount}" required step="1800">
            </div>
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
                    <input class="form-check-input" type="radio" name="type-${roomCount}" id="type5-${roomCount}" data-tag="마운틴뷰">
                    <label class="form-check-label" for="type5-${roomCount}">마운틴뷰</label>
                </div>
            </div>
        </div>
        
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">기준인원</label>
            <div class="col-sm-10 d-flex align-items-center">
                <div class="btn btn-secondary decrement" data-room-id="${roomCount}" data-type="basic">-</div>
                <input type="text" class="form-control text-center mx-2 maxPeople" name="standardOccupation" id="basicPeople-${roomCount}" value="1" readonly>
                <div class="btn btn-secondary increment" data-room-id="${roomCount}" data-type="basic">+</div>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">최대인원</label>
            <div class="col-sm-10 d-flex align-items-center">
                <div class="btn btn-secondary decrement" data-room-id="${roomCount}" data-type="max">-</div>
                <input type="text" class="form-control text-center mx-2 maxPeople" name="maxOccupation" id="maxPeople-${roomCount}" value="1" readonly>
                <div class="btn btn-secondary increment" data-room-id="${roomCount}" data-type="max">+</div>
            </div>
        </div>
        
        <div class="row">
                    <label class="col-sm-2 col-form-label">객실 이미지</label>
                    <div class="col-sm-10">
                        <ul class="product-gallery" id="preview-container-${roomCount}"></ul>
                        <input class="btn btn-g btn-circle image-upload" type="file" name="previewFiles" multiple id="preview-file-input-${roomCount}" data-room-id="${roomCount}">
                    </div>
                </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">동일 객실 수</label>
            <div class="col-sm-10 d-flex align-items-center">
                <div class="btn btn-secondary decrement" data-room-id="${roomCount}" data-type="roomValues">-</div>
                <input type="text" class="form-control text-center mx-2 maxPeople" name="roomValues" id="roomValues-${roomCount}" value="1" readonly>
                <div class="btn btn-secondary increment" data-room-id="${roomCount}" data-type="roomValues">+</div>
            </div>
        </div>
        <button class="btn btn-delete" data-room-id="${roomCount}">삭제</button>
    `;

    document.getElementById('roomsContainer').appendChild(roomContainer);
});

/**
 * 카테고리 선택 및 태그 추가
 */
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


/**
 *  객실별 다중 이미지
 */
function calculateEndIndex(roomCount, roomFiles, roomId) {
    let currentTotalFiles = 0;
    let endIndex = [];


    for (let i = 0; i <= roomCount; i++) {
        if (roomFiles[i]) {
            currentTotalFiles += roomFiles[i].length;
        }

        // endIndex 갱신
        if (roomId === i) {
            endIndex[i] = currentTotalFiles;
        } else {
            endIndex[i] = currentTotalFiles;
        }
    }
    return endIndex;
}

// 전역 변수 선언
let mainPhotoFile = null;


/**
 * 대표사진 업로드 및 미리보기 기능 추가
 */
document.getElementById('photo').addEventListener('change', function() {
    const photoInput = this;
    const photoPreviewContainer = document.getElementById('photo-preview-container');

    // 선택된 파일을 가져옴
    const selectedFile = photoInput.files[0];

    if (selectedFile) {
        mainPhotoFile = selectedFile; // mainPhotoFile 변수에 저장

        // 미리보기 초기화 (기존 이미지 제거)
        photoPreviewContainer.innerHTML = '';

        // FileReader를 이용해 이미지를 미리보기로 표시
        const reader = new FileReader();
        reader.onload = function(e) {
            const imgElement = document.createElement('img');
            imgElement.src = e.target.result;
            imgElement.classList.add('preview-image');
            imgElement.style.height = '100%';
            imgElement.style.width = '130%';

            const imgContainer = document.createElement('li');
            imgContainer.classList.add('preview-item');

            // 삭제 버튼 생성
            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'Delete';
            deleteButton.classList.add('delete-button');
            deleteButton.addEventListener('click', function() {
                deleteMainPhoto(imgContainer);
            });

            imgContainer.appendChild(imgElement);
            imgContainer.appendChild(deleteButton);
            photoPreviewContainer.appendChild(imgContainer);
        }
        reader.readAsDataURL(selectedFile);
    }

    // 선택된 파일 초기화 (동일한 파일 다시 선택할 수 있게 함)
    photoInput.value = '';
});


//
/**
 * 다중 이미지 업로드 와 미리보기
 */
document.addEventListener('change', function(event) {
    if (event.target && event.target.classList.contains('image-upload')) {
        let roomId = event.target.getAttribute('data-room-id');
        roomId = parseInt(roomId, 10);
        var imagePreview = document.getElementById('preview-container-' + roomId);
        const selectedFiles = Array.from(event.target.files);


        // roomFiles 객체에 해당 roomId가 없으면 빈 배열로 초기화
        if (!roomFiles[roomId]) {
            roomFiles[roomId] = [];
        }


        // 이미지 파일 추가
        selectedFiles.forEach(file => {
            roomFiles[roomId].push(file);
        });


        // endIndex 갱신
        endIndex = calculateEndIndex(roomCount, roomFiles, roomId);

        selectedFiles.forEach((file) => {
            const reader = new FileReader();
            reader.onload = function(e) {
                const imgElement = document.createElement('img');
                imgElement.src = e.target.result;
                imgElement.classList.add('preview-image');
                imgElement.style.height = '50%';
                imgElement.style.width = '50%';

                const imgContainer = document.createElement('li');
                imgContainer.classList.add('preview-item');

                // 삭제 버튼 생성
                const deleteButton = document.createElement('button');
                deleteButton.textContent = 'Delete';
                deleteButton.classList.add('delete-button');
                deleteButton.addEventListener('click', function() {
                    deleteFile(roomId, file, imgContainer);
                });

                imgContainer.appendChild(imgElement);
                imgContainer.appendChild(deleteButton);
                imagePreview.appendChild(imgContainer);
            }
            reader.readAsDataURL(file);
        });
        event.target.value = '';
    }

});
// 파일 삭제 함수
function deleteFile(roomId, file, imgContainer) {
    const roomFilesArray = roomFiles[roomId];
    const fileIndex = roomFilesArray.indexOf(file);
    if (fileIndex > -1) {
        roomFilesArray.splice(fileIndex, 1); // 배열에서 파일 제거
    }
    imgContainer.remove(); // 미리보기에서 이미지 제거
}

// 대표사진 삭제 함수
function deleteMainPhoto(imgContainer) {
    mainPhotoFile = null; // 파일 참조 제거
    imgContainer.remove(); // 미리보기에서 이미지 제거
}


// 이미지 업로드 및 endIndex 갱신
// document.addEventListener('change', function(event) {
//     if (event.target && event.target.classList.contains('image-upload')) {
//         const roomId = event.target.getAttribute('data-room-id');
//         const selectedFiles = Array.from(event.target.files);
//
//         // roomFiles 객체에 해당 roomId가 없으면 빈 배열로 초기화
//         if (!roomFiles[roomId]) {
//             roomFiles[roomId] = [];
//         }
//
//         // 이미지 파일 추가
//         selectedFiles.forEach(file => {
//             roomFiles[roomId].push(file);
//         });
//
//         // endIndex 갱신
//         let currentTotalFiles = 0;
//         for (let i = 1; i <= roomCount; i++) {
//             if (roomFiles[i]) {
//                 currentTotalFiles += roomFiles[i].length;
//             }
//             endIndex[i] = currentTotalFiles; // 각 객실의 현재 endIndex 저장
//         }
//
//         console.log(`Room ${roomId} - Updated endIndex: ${endIndex[roomId]}`);
//     }
// });






/**
 * 인원 증가/감소 버튼의 이벤트 리스너 추가
 */
document.addEventListener('click', function(event) {
    if (event.target && event.target.classList.contains('decrement')) {
        var roomId = event.target.getAttribute('data-room-id');
        var type = event.target.getAttribute('data-type');
        var inputField;

        if (type === 'basic') {
            inputField = document.getElementById('basicPeople-' + roomId);
        } else if (type === 'max') {
            inputField = document.getElementById('maxPeople-' + roomId);
        } else if (type === 'roomValues') {
            inputField = document.getElementById('roomValues-' + roomId);
        }

        var currentValue = parseInt(inputField.value);
        if (currentValue > 1) {
            inputField.value = currentValue - 1;
        }
    }

    if (event.target && event.target.classList.contains('increment')) {
        var roomId = event.target.getAttribute('data-room-id');
        var type = event.target.getAttribute('data-type');
        var inputField;

        if (type === 'basic') {
            inputField = document.getElementById('basicPeople-' + roomId);
        } else if (type === 'max') {
            inputField = document.getElementById('maxPeople-' + roomId);
        }else if (type === 'roomValues') {
            inputField = document.getElementById('roomValues-' + roomId);
        }

        var currentValue = parseInt(inputField.value);
        inputField.value = currentValue + 1;
    }

    // 객실 삭제 버튼 클릭 시 해당 객실 삭제
    if (event.target && event.target.classList.contains('btn-delete')) {
        var roomId = event.target.getAttribute('data-room-id');
        var roomContainer = document.getElementById('room-' + roomId);
        roomContainer.remove();
        delete roomFiles[roomId]; // 해당 객실의 이미지 파일 배열 삭제
    }
});


document.querySelectorAll('.tag input[type="radio"]').forEach(function (radio) {
    radio.addEventListener('change', function () {
        var typeInput = document.getElementById('type');
        typeInput.value = this.getAttribute('data-tag');
    });
});


document.querySelector('form').addEventListener('submit', function(event) {
    if (!this.checkValidity()) {
        event.preventDefault(); // 폼 제출 중지
        alert('모든 필드를 채워주세요!');
    }
});


/**
 * form data 생성
 */
document.getElementById('accommodationForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 기본 폼 제출 방지

    // FormData 객체 생성
    const formData = new FormData(this);


    // 부대시설 체크박스 선택된 값들을 FormData에 추가
    const selectedTags = [];
    document.querySelectorAll('.tags input[type="checkbox"]:checked').forEach(tag => {
        selectedTags.push(tag.getAttribute('data-tag'));
    });
    // 에디터의 내용을 FormData에 추가
    const editorContent = document.getElementById('editor').innerHTML;
    formData.append('accommodationInfo', editorContent);

    // 숙소 유형 추가
    const selectedType = document.querySelector('input[class="accommodationType"]:checked');
    if (selectedType) {
        formData.append('accommodationType', selectedType.value);
    }

    // 대표사진 파일이 있으면 FormData에 추가
    if (mainPhotoFile) {
        formData.append('mainPhoto', mainPhotoFile);
    }

    // 객실 정보 추가
    for (let i = 1; i <= roomCount; i++) {
        // Room Name
        formData.append(`rooms[${i}].roomName`, document.querySelector(`#room-${i} input[name="roomName"]`).value);

        // Room Rates
        formData.append(`rooms[${i}].weekdayRate`, document.querySelector(`#room-${i} input[name="weekdayRate"]`).value);
        formData.append(`rooms[${i}].fridayRate`, document.querySelector(`#room-${i} input[name="fridayRate"]`).value);
        formData.append(`rooms[${i}].saturdayRate`, document.querySelector(`#room-${i} input[name="saturdayRate"]`).value);
        formData.append(`rooms[${i}].sundayRate`, document.querySelector(`#room-${i} input[name="sundayRate"]`).value);

        // Room Category
        const selectedCategory = document.querySelector(`#room-${i} input[name="type-${i}"]:checked`);
        if (selectedCategory) {
            formData.append(`rooms[${i}].roomCategory`, selectedCategory.getAttribute('data-tag'));
        }

        // Standard and Max Occupation
        formData.append(`rooms[${i}].standardOccupation`, document.getElementById(`basicPeople-${i}`).value);
        formData.append(`rooms[${i}].maxOccupation`, document.getElementById(`maxPeople-${i}`).value);
        formData.append(`rooms[${i}].roomValues`, document.getElementById(`roomValues-${i}`).value);

        // Check-in and Check-out Time
        formData.append(`rooms[${i}].checkInTime`, document.getElementById(`checkInTime-${i}`).value);
        formData.append(`rooms[${i}].checkOutTime`, document.getElementById(`checkOutTime-${i}`).value);



        // endIndex 객체를 JSON 문자열로 변환하여 추가
        // formData.append(`rooms[${i}].endIndex`, JSON.stringify({ [i]: endIndex[i] }));
        formData.append(`rooms[${i}].endIndex`, JSON.stringify(endIndex[i]));

        // Room Images
        if (roomFiles[i]) {
            roomFiles[i].forEach(image => {
                formData.append('previewFiles', image)
            });
        }
    }

    /**
     * AJAX 요청 생성
     */
    fetch('/save-location', {
        method: 'POST',
        body: formData,
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                alert('업소 등록이 완료되었습니다!');
                window.location.href = '/enroll';
            } else {
                alert('업소 등록 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('업소 등록 중 오류가 발생했습니다.');
        });
});

