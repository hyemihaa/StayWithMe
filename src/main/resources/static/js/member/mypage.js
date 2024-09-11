// 특정 섹션만 보이도록 설정하는 함수 (전역 스코프에 정의)
function showSection(sectionId) {
    // 모든 섹션 숨기기
    document.querySelectorAll('.section').forEach(function(section) {
        section.style.display = 'none';
    });

    // 선택된 섹션만 보이기
    const selectedSection = document.getElementById(sectionId);
    if (selectedSection) {
        selectedSection.style.display = 'block';

        // 섹션에 맞는 데이터 로드
        if (sectionId === 'activity-log') {
            loadLoginLogs();
        }
        if (sectionId === 'coupons') {
            loadCoupons();
        }
        if (sectionId === 'reservation') {
            loadReservations();
        }
    }
    else {
        console.error(`Section with ID ${sectionId} not found.`);
    }
}

/*---------------내정보수정--------------*/
// 비밀번호 저장 -> 비밀번호 변경 요청
function savePasswordButton() {
    const currentPassword = document.getElementById('currentPassword').value.trim(); // trim() 문자열의 시작과 끝에 있는 공백을 제거
    const newPassword = document.getElementById('newPassword').value.trim();
    const confirmNewPassword = document.getElementById('confirmNewPassword').value.trim();

    // 비밀번호 유효성 검사
    if (!currentPassword || !newPassword || !confirmNewPassword) {
        alert('모든 필드를 입력해 주세요.');
        return;
    }

    if (newPassword !== confirmNewPassword) {
        alert('새 비밀번호와 확인 비밀번호가 일치하지 않습니다.');
        return;
    }

    // 비밀번호 변경 요청
    $.ajax({
        type: "POST",
        url: "/update-password",
        contentType: "application/json",
        data: JSON.stringify({
            currentPassword: currentPassword,
            newPassword: newPassword
        }),
        success: function(response) {
            if (response.success) {
                alert('비밀번호가 성공적으로 변경되었습니다.');
                document.getElementById('passwordChangeFields').style.display = 'none';
                document.getElementById('currentPassword').value = '';
                document.getElementById('newPassword').value = '';
                document.getElementById('confirmNewPassword').value = '';
            }
            else {
                alert('비밀번호 변경에 실패했습니다: ' + response.error);
            }
        },
        error: function(xhr, status, error) {
            console.error('비밀번호 변경 중 오류 발생:', error);
            alert('비밀번호 변경 중 오류가 발생했습니다.');
        }
    });
}

/*-----------탈퇴 기타 사유 입력란 길이제한-----------*/
const textarea = document.getElementById('otherReason');
const charCount = document.getElementById('charCount');
const errorMessage = document.getElementById('errorMessage');
const maxChars = 250;

// 입력 이벤트 리스너 추가
textarea.addEventListener('input', function () {
    // 현재 입력된 문자 수를 가져옴
    let currentLength = textarea.value.length;

    // 250자를 초과하는지 확인
    if (currentLength > maxChars) {
        // 초과된 부분을 잘라내어 입력 제한
        textarea.value = textarea.value.slice(0, maxChars);
        currentLength = maxChars;
    }

    // 문자 수 업데이트
    charCount.textContent = `${currentLength} / ${maxChars}`;

    // 250자를 초과했는지 확인 후 에러 메시지 표시
    if (currentLength === maxChars) {
        errorMessage.style.display = 'inline';
    } else {
        errorMessage.style.display = 'none';
    }
});

/*------------이력관리(로그인기록)-------------*/
let logsData = []; // 모든 로그 데이터를 저장
let currentPage = 0; // 현재 페이지 번호
const logsPerPage = 3; // 한 번에 보여줄 로그 수

function loadLoginLogs() {
    $.ajax({
        type: 'POST',
        url: '/login-log',
        contentType: 'application/json',
        success: function(data) {
            if (data.logs && data.logs.length > 0) {
                logsData = data.logs; // 데이터를 저장
                renderLogs(); // 초기 로딩 시 날짜 필터 적용
            }
        },
        error: function(error) {
            console.error('로그인 로그 데이터를 불러오는 중 오류가 발생했습니다:', error);
        }
    });
}


// 로그 데이터를 현재 페이지에 맞게 렌더링
function renderLogs() {
    const logTableBody = document.getElementById('log-table-body');

    const start = currentPage * logsPerPage;
    const end = start + logsPerPage;
    const logsToRender = logsData.slice(start, end);

    logsToRender.forEach(log => {
        const row = document.createElement('tr');

        // 최근 접속일자와 IP 주소를 새로운 행(row)에 추가
        const dateCell = document.createElement('td');
        dateCell.textContent = log.lastLoginDate.replace('T', ' ');
        row.appendChild(dateCell);

        const ipCell = document.createElement('td');
        ipCell.textContent = log.userIp;
        row.appendChild(ipCell);

        // 완성된 행을 테이블 본체에 추가
        logTableBody.appendChild(row);
    });

    // "더보기" 버튼 표시 여부 결정
    const loadMoreButton = document.getElementById('load-more-button');
    if (end >= logsData.length) {
        loadMoreButton.style.display = 'none';
    } else {
        loadMoreButton.style.display = 'block';
    }
}

// "더보기" 버튼 클릭 시 호출되는 함수
function loadMoreLogs() {
    currentPage++;
    renderLogs(); // 기존 로그를 유지하면서 새로운 로그를 추가로 렌더링
}

// 날짜 포맷팅 함수
function formatDate(dateString) {
    const options = { year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric' };
    const date = new Date(dateString);
    return date.toLocaleDateString('ko-KR', options);

}

/*------------ 쿠폰 조회 -------------*/
function loadCoupons() {
    $.ajax({
        type: 'POST',
        url: '/coupons',
        success: function(response) {
            if (response.success) {
                const coupons = response.coupons || []; // 쿠폰 데이터 배열
                renderCoupons(coupons); // 데이터를 화면에 렌더링하는 함수 호출
            } else {
                console.error('쿠폰 정보를 가져오는 데 실패했습니다.', response.error);
                alert('쿠폰 정보를 가져오는 데 실패했습니다.');
            }
        },
        error: function(xhr, status, error) {
            console.error('쿠폰 정보를 가져오는 데 실패했습니다.', status, error);
            alert('쿠폰 정보를 가져오는 데 실패했습니다.');
        }
    });
}

// 쿠폰 데이터 렌더링
function renderCoupons(coupons) {
    var couponSection = document.querySelector('#coupons .coupon-list');
    var noCouponsMessage = document.querySelector('#coupons .no-coupons');

    couponSection.innerHTML = ''; // 기존 내용 비우기

    if (coupons.length === 0) {
        noCouponsMessage.style.display = 'block'; // 쿠폰이 없을 때 메시지 보이기
    } else {
        noCouponsMessage.style.display = 'none'; // 쿠폰이 있을 때 메시지 숨기기
        coupons.forEach(function(coupon) {
            // 쿠폰 타입에 따라 할인 텍스트
            var discountText = coupon.couponType === 'DISCOUNT_AMOUNT'
                ? '원 할인'
                : (coupon.couponType === 'DISCOUNT_RATE'
                    ? '% 할인'
                    : '할인');
            var couponItem = `
                <div class="coupon-item">
                    <p>쿠폰 이름: ${coupon.couponName}</p>
                    <p>쿠폰코드: ${coupon.couponCode}</p>
                    <p>유효기간: ${coupon.couponStartDate} - ${coupon.couponEndDate}</p>
                    <p>할인 값: ${coupon.couponDiscount}${discountText}</p>
                    <p>사용 최소 금액: ${coupon.couponMinimumAmount}원</p>
                </div>
            `;
            couponSection.innerHTML += couponItem; // 쿠폰 항목 추가
        });
    }
}

/*-------------예약내역 조회-----------------*/
function loadReservations() {
    $.ajax({
        type: 'POST',
        url: '/reservation-list',
        success: function(response) {
            if (response.success) {
                const reservation = response.reservation || []; // 예약 내역 배열
                renderReservation(reservation); // 데이터를 화면에 렌더링하는 함수 호출
            } else {
                console.error('예약 정보를 가져오는 데 실패했습니다.', response.error);
                alert('예약 정보를 가져오는 데 실패했습니다.');
            }
        },
        error: function(xhr, status, error) {
            console.error('예약 정보를 가져오는 데 실패했습니다.', status, error);
            alert('예약 정보를 가져오는 데 실패했습니다.');
        }
    });
}

// 예약 데이터 렌더링
function renderReservation(reservation) {
    var reservationList = document.querySelector('#reservation .reservation-list');
    var noReservation = document.querySelector('#reservation .no-reservation');

    if (!noReservation) {
        return;
    }

    reservationList.innerHTML = ''; // 기존 내용 비우기

    if (reservation.length === 0) {
        noReservation.style.display = 'block'; // 예약이 없을 때 메시지 보이기
        reservationList.style.display = 'none';
    } else {
        noReservation.style.display = 'none'; // 예약이 있을 때 메시지 숨기기

        reservation.forEach(function(reservation) {
            const imageUrl = `/accommodationImages/${reservation.accommodationImageDto.uploadUniqueName}`;


            // 조건에 따라 버튼을 다르게 추가
            let cancelButton = '';
            if (reservation.reservationStatus === 'Confirmed') {
                cancelButton = `<button class="btn-danger" onclick="cancelReservation(${reservation.reserveRoomNo})">예약 취소</button>`;
            }

            const reservationItem = `
                  <div class="reservation-item">
                       <div class="details">
                           <h4 class="hotel-name">${reservation.accommodationName}</h4>
                           <div class="image-info">
                                <img src="${imageUrl}" alt="Hotel Image">
                                <div class="room-info">
                                    <p>객실 이름: ${reservation.roomName}</p>
                                    <p>${reservation.reserveCheckIn} - ${reservation.reserveCheckOut}</p>
                                    <p>체크인: ${reservation.roomCheckIn} 체크아웃: ${reservation.roomCheckOut}</p>
                                    <p>기준 인원: ${reservation.roomPersonnel}명 / 최대 인원: ${reservation.roomMaxPersonnel}명</p>
                                </div>
                           </div>
                           <p class="price" style="text-align: right;">
                                금액: <strong><span style="font-size: 1.2em;">${reservation.reserveAmount}원</span></strong>
                           </p>
                           <div class="button-group" style="float: right;">
                                <button class="btn-danger" onclick="requestRefund(${reservation.reserveRoomNo}, '${reservation.approvalCode}', ${reservation.reserveAmount} )">예약 취소</button>
                                <button class="btn-primary" onclick="viewReservationDetails(${reservation.accommodationNo})">예약 상세</button>
                           </div>
                       </div>
                  </div>
                  `;
                  reservationList.innerHTML += reservationItem; // 예약 리스트 항목 추가
        });
    }
}
  // 예약 상세 페이지로 이동
    function viewReservationDetails(accommodationNo) {
    // 상세 페이지 URL 생성
    const url = `/hotel-single?boardNo=${accommodationNo}`;

    // 해당 URL로 이동
    window.location.href = url;
    }

    function findTravelDestinations() {

    const url = `/`;

    // 해당 URL로 이동
    window.location.href = url;
    }


// DOMContentLoaded 이벤트 리스너 내부의 코드
document.addEventListener('DOMContentLoaded', function() {
    // 페이지 로드 시 기본으로 예약 내역 섹션을 보이게 설정
    showSection('reservation');

    // 비밀번호 변경 필드 보이기/숨기기
    document.getElementById('changePasswordButton').addEventListener('click', function() {
        const passwordChangeFields = document.getElementById('passwordChangeFields');
        passwordChangeFields.style.display = passwordChangeFields.style.display === 'block' ? 'none' : 'block';
    });

    // 비밀번호 취소 버튼 클릭 시 비밀번호 변경 필드 숨기기 및 입력 필드 초기화
    document.getElementById('cancelPasswordChange').addEventListener('click', function() {
        document.getElementById('passwordChangeFields').style.display = 'none';
        document.getElementById('currentPassword').value = '';
        document.getElementById('newPassword').value = '';
        document.getElementById('confirmNewPassword').value = '';
    });

    // 휴대폰 번호 수정 필드 보이기/숨기기
    document.getElementById('changePhoneButton').addEventListener('click', function() {
        const phoneChangeFields = document.getElementById('phoneChangeFields');
        phoneChangeFields.style.display = phoneChangeFields.style.display === 'block' ? 'none' : 'block';
    });

    // 휴대폰 번호 변경 취소 버튼 클릭 시 휴대폰 변경 필드 숨기기
    document.getElementById('cancelPhoneChange').addEventListener('click', function() {
        document.getElementById('phoneChangeFields').style.display = 'none';
        document.getElementById("newPhone").value = '';
        document.getElementById("verificationCode").value = '';
        document.getElementById("phoneMsg").innerText = '';
        document.getElementById("codeMsg").innerText = '';
        document.getElementById("timerMsg").innerHTML = '';
        clearInterval(timer);
    });

    // 새로운 비밀번호 유효성 검사 함수
    function validatePassword() {
        const newPassword = document.getElementById("newPassword").value;
        const confirmNewPassword = document.getElementById("confirmNewPassword").value;

        const pwdMsg = document.getElementById("pwdMsg");
        const pwdConfirmMsg = document.getElementById("pwdConfirmMsg");

        const pwdPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;

        if (newPassword !== "" && pwdPattern.test(newPassword)) {
            pwdMsg.innerHTML = "유효한 비밀번호입니다.";
            pwdMsg.style.color = "#aaa";
        } else if (newPassword !== "") {
            pwdMsg.innerHTML = "영문+숫자+특수문자를 포함하여 8~20자로 입력하세요.";
            pwdMsg.style.color = "red";
        } else {
            pwdMsg.innerHTML = "";
        }

        if (newPassword !== "" && newPassword === confirmNewPassword) {
            pwdConfirmMsg.innerHTML = "비밀번호가 일치합니다.";
            pwdConfirmMsg.style.color = "#aaa";
        } else if (confirmNewPassword !== "") {
            pwdConfirmMsg.innerHTML = "비밀번호가 일치하지 않습니다.";
            pwdConfirmMsg.style.color = "red";
        } else {
            pwdConfirmMsg.innerHTML = "";
        }
    }

    // 비밀번호 유효성 검사 이벤트 리스너 추가
    document.getElementById("newPassword").addEventListener('input', validatePassword);
    document.getElementById("confirmNewPassword").addEventListener('input', validatePassword);

    // 휴대폰 번호 유효성 검사 함수
    function validatePhone() {
        const newPhone = document.getElementById("newPhone").value;
        const phoneMsg = document.getElementById("phoneMsg");

        const phonePattern = /^010\d{8}$/;

        if (phonePattern.test(newPhone)) {
            phoneMsg.innerHTML = "유효한 전화번호입니다.";
            phoneMsg.style.color = "#aaa";
        } else {
            phoneMsg.innerHTML = "01012345678 형식으로 입력하세요.";
            phoneMsg.style.color = "red";
        }
    }

    // 타이머 함수 추가
    let timer;
    let timeLeft = 180;

    function startTimer() {
        const timerDisplay = document.getElementById("timerMsg");

        // 기존 타이머가 있다면 제거
        if (timer) {
            clearInterval(timer);
        }

        timerDisplay.innerHTML = "180초 남음";

        timer = setInterval(function() {
            timeLeft--;
            if (timeLeft <= 0) {
                clearInterval(timer);
                timerDisplay.innerHTML = "";
                alert("인증 시간이 초과되었습니다. 다시 시도해주세요.");
                document.getElementById('sendVerificationCode').disabled = false;
            } else {
                timerDisplay.innerHTML = `${timeLeft}초 남음`;
            }
        }, 1000);
    }

    // 인증번호 유효성 검사 함수
    function validateVerificationCode() {
        const verificationCode = document.getElementById("verificationCode").value;
        const verificationCodeMsg = document.getElementById("codeMsg");

        const codePattern = /^\d{6}$/;

        if (codePattern.test(verificationCode)) {
            verificationCodeMsg.innerHTML = "유효한 인증번호입니다.";
            verificationCodeMsg.style.color = "#aaa";
        } else {
            verificationCodeMsg.innerHTML = "6자리 숫자로 입력하세요.";
            verificationCodeMsg.style.color = "red";
        }
    }

    // 휴대전화 인증번호 요청 함수
    function requestSMS() {
        const phone = document.getElementById("newPhone").value; // 사용자 입력 휴대전화 번호
        const phoneMsg = document.getElementById("phoneMsg"); // 휴대전화 번호 유효성 메시지
        const phonePattern = /^010\d{8}$/; // 휴대전화 번호 정규식: 010으로 시작하며, 뒤에 8자리 숫자가 나옴

        if (!phone) {
            alert('휴대전화 번호를 입력해 주세요.');
            return;
        }

        // 휴대전화 번호 유효성 검사
        if (!phonePattern.test(phone)) {
            phoneMsg.innerHTML = "유효한 휴대전화 번호를 입력해 주세요.";
            phoneMsg.style.color = "red";
            return;
        } else {
            phoneMsg.innerHTML = ""; // 유효한 번호라면 메시지를 제거
        }

        $.ajax({
            type: "POST",
            url: "/sms/send",
            contentType: "application/json",
            data: JSON.stringify({ phoneNumber: phone }), // controller로 보낼 데이터 JSON 형태로 변환
            success: function(response) {
                console.log("SMS response:", response); // 응답 로그 추가
                alert("인증번호가 발송되었습니다.");
                document.getElementById("randomNum").value = response.certificationCode; // 인증번호를 hidden input에 저장
                startTimer(); // 인증번호 제한시간 시작
            },
            error: function(xhr, status, error) {
                alert("인증번호 발송에 실패했습니다. 다시 시도해주세요.");
            }
        });
    }

    // 인증번호 전송 버튼
    document.getElementById("sendVerificationCode").addEventListener('click', function() {
        // 새로운 휴대폰 번호의 유효성을 검증하는 함수 호출
        validatePhone();

        // 유효할 경우에만 sms 전송 요청
        if (document.getElementById("phoneMsg").style.color === "rgb(170, 170, 170)") {

            // SMS 인증번호 요청 함수 호출
            requestSMS();

            // 타이머 시작
            startTimer();
        }
    });

    // 인증번호 확인
    document.getElementById("verifyCodeButton").addEventListener('click', function() {
        const userCode = document.getElementById("verificationCode").value; // 사용자가 입력한 인증번호
        const serverCode = document.getElementById("randomNum").value;

        console.log("사용자 입력 인증번호:", userCode);
        console.log("서버에서 받은 인증번호:", serverCode);

        if (!userCode) {
            alert('인증번호를 입력해 주세요.');
            return;
        }

        if (userCode === serverCode) {
            clearInterval(timer); // 타이머 중지
            alert("인증이 정상적으로 완료되었습니다.");
            // 인증 성공 플래그 설정
            window.isVerified = true;
        } else {
            alert("인증번호가 올바르지 않습니다.");
            // 인증 성공 플래그 설정
            window.isVerified = false;
        }
    });

    // 인증번호 입력 필드 변경 시 유효성 검사 및 인증 상태 초기화
    document.getElementById("verificationCode").addEventListener('input', function() {
        validateVerificationCode();

        // 사용자가 인증번호를 수정할 경우, 다시 인증 필요
        if (window.isVerified) {
            window.isVerified = false;
            alert('인증번호가 수정되었습니다. 다시 요청해 주세요.');

            // 인증번호 요청 버튼을 다시 활성화
            document.getElementById('sendVerificationCode').disabled = false;
        }
    });

    // 인증번호 입력 필드 변경 시 유효성 검사
    document.getElementById("verificationCode").addEventListener('input', validateVerificationCode);

    // 휴대전화 번호 변경 저장 버튼
    document.getElementById("savePhoneButton").addEventListener('click', function() {
        // 새로운 휴대폰 번호의 유효성을 검증하는 함수 호출
        validatePhone();

        // 유효할 경우(유효할 때 나타나는 색상)에만 sms 전송 요청
        validatePhoneCheck = document.getElementById("phoneMsg").style.color === "rgb(170, 170, 170)";

        const newPhone = document.getElementById("newPhone").value;

        if (window.isVerified && validatePhoneCheck) {
            $.ajax({
                type: "POST",
                url: "/update-phone",
                contentType: "application/json",
                data: JSON.stringify({
                    newPhone: newPhone,
                }),
                success: function(response) {
                    if (response.success) {
                        alert('휴대폰 번호가 성공적으로 변경되었습니다.');
                        document.getElementById('phoneChangeFields').style.display = 'none';

                        // 성공적으로 변경되었을 경우 필드값 비우기
                        document.getElementById("newPhone").value = '';
                        document.getElementById("verificationCode").value = '';
                        document.getElementById("phoneMsg").innerText = '';
                        document.getElementById("codeMsg").innerText = '';
                        document.getElementById("timerMsg").innerHTML = '';

                        // 현재 표시된 사용자 휴대폰 번호 업데이트
                        document.getElementById("phone").value = newPhone;

                        // window.isVerified 초기화
                        window.isVerified = false;
                    }
                    else {
                        alert('휴대폰 번호 변경에 실패했습니다: ' + response.error);
                    }
                },
                error: function(xhr, status, error) {
                    console.error('휴대폰 번호 변경 중 오류 발생:', error);
                    alert('휴대폰 번호 변경 중 오류가 발생했습니다.');
                }
            });
        }
        else {
            alert('전화번호 입력이 유효하지 않거나 인증번호가 잘못되었습니다.');
        }
    });


    /*-------회원 탈퇴 섹션------*/
    document.getElementById("withdrawalForm").addEventListener("submit", function(event) {
        event.preventDefault(); // 기본 제출 막음

        // 동의 체크 박스 체크 여부 확인
        const confirmationChecked = document.getElementById("confirmation").checked;

        if (!confirmationChecked) {
            alert("회원 탈퇴를 진행하시려면 동의란에 체크해 주세요.");
            return;
        }

        // 탈퇴 사유 가져오기
        const selectedReason = document.querySelector('input[name="reason"]:checked');
        let reason = "";

        if (selectedReason) {
            reason = selectedReason.value;
            console.log("탈퇴사유 : ", reason);
        }

        // 기타 선택한 경우, 기타사유 입력값
        if (reason === "기타") {
            reason = document.getElementById("otherReason").value;
            console.log("기타탈퇴사유 : ", reason);
        }

        // 회원 탈퇴 요청 보내기
        $.ajax({
            type: "POST",
            url: "/withdraw-account",
            contentType: "application/json",
            data: JSON.stringify({
                withdrawalReason: reason // 서버에 전달될 값
            }),
            success: function(response) {
                alert('회원 탈퇴가 정상적으로 처리되었습니다.');

                 // 페이지 이동
                 window.location.href = '/';
            },
            error: function(xhr, status, error) {
                console.error('회원 탈퇴 중 오류 발생: ', error);
                alert('회원 탈퇴 중 오류가 발생했습니다.');
            }
        });
    });
});
