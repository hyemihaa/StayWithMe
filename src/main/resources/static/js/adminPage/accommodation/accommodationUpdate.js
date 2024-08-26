


document.addEventListener('DOMContentLoaded', function() {
    var selectedType = document.getElementById("selectedAccommodationType").value; // 숨겨진 input의 값을 가져옴
    var options = document.getElementsByClassName("accommodationType"); // 모든 라디오 버튼을 가져옴

    for (var i = 0; i < options.length; i++) {
        if (options[i].value === selectedType) {
            options[i].checked = true; // 값이 일치하면 해당 라디오 버튼을 선택함
            break;
        }
    }
});


// document.addEventListener('DOMContentLoaded', function() {
//     var checkboxes = document.querySelectorAll('.accommodationType');
//
//     checkboxes.forEach(function(checkbox) {
//         checkbox.addEventListener('change', function() {
//             if (this.checked) {
//                 checkboxes.forEach(function(box) {
//                     if (box !== checkbox) {
//                         box.checked = false;
//                     }
//                 });
//             }
//         });
//     });
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
//
// document.addEventListener("DOMContentLoaded", function() {
//     // 숨겨진 input에서 roomCategory 값을 가져옴
//     var roomCategory = document.getElementById('roomCategory').value;
//
//     // 해당 roomCategory 값과 일치하는 라디오 버튼을 선택
//     var radioButtons = document.querySelectorAll(`input[type="radio"][name="type"]`);
//     radioButtons.forEach(function(radio) {
//         if (radio.getAttribute('data-tag') === roomCategory) {
//             radio.checked = true; // 라디오 버튼 체크
//         }
//     });
// });


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


        // Standard and Max Occupation
        formData.append(`rooms[${i}].standardOccupation`, document.getElementById(`basicPeople-${i}`).value);
        formData.append(`rooms[${i}].maxOccupation`, document.getElementById(`maxPeople-${i}`).value);

        // Check-in and Check-out Time
        formData.append(`rooms[${i}].checkInTime`, document.getElementById(`checkInTime-${i}`).value);
        formData.append(`rooms[${i}].checkOutTime`, document.getElementById(`checkOutTime-${i}`).value);



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
    fetch('/update-accommodation', {
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

