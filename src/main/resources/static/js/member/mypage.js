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
    }
}

// 비밀번호 저장 -> 비밀번호 변경 요청 (전역 스코프에 정의)
function savePasswordButton() {
    const currentPassword = document.getElementById('currentPassword').value.trim();
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
            } else {
                alert('비밀번호 변경에 실패했습니다: ' + response.error);
            }
        },
        error: function(xhr, status, error) {
            console.error('비밀번호 변경 중 오류 발생:', error);
            alert('비밀번호 변경 중 오류가 발생했습니다.');
        }
    });
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

    // 비밀번호 변경 취소 버튼 클릭 시 비밀번호 변경 필드 숨기기 및 입력 필드 초기화
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
    let timeLeft = 1200; // 1200초 (20분)

    function startTimer() {
        const timerDisplay = document.getElementById("timerMsg");

        // 기존 타이머가 있다면 제거
        if (timer) {
            clearInterval(timer);
        }

        timerDisplay.innerHTML = "1200초 남음";

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

    // 인증번호 유효성 검사
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

            // 인증번호 전송 버튼 비활성화
            this.disabled = true;

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

    // 인증번호 입력 필드 변경 시 유효성 검사
    document.getElementById("verificationCode").addEventListener('input', validateVerificationCode);

    // 휴대전화 번호 변경 저장 버튼
    document.getElementById("savePhoneButton").addEventListener('click', function() {
        // 새로운 휴대폰 번호의 유효성을 검증하는 함수 호출
        validatePhone();

        // 유효할 경우에만 sms 전송 요청
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
});
