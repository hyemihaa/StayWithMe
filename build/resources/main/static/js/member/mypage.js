// 전체 mypage 적용 js
// 특정 섹션만 보이도록 설정하는 함수
function showSection(sectionId) {
    // 모든 섹션 숨기기
    document.querySelectorAll('.section').forEach(function(section) {
        section.style.display = 'none';
    });

    // 선택된 섹션만 보이기
    document.getElementById(sectionId).style.display = 'block';
}

// 페이지 로드 시 기본으로 예약 내역 섹션을 보이게 설정
window.onload = function() {
    showSection('reservation');
}

// 내정보수정--------------------------------------------------------------
document.addEventListener('DOMContentLoaded', function() {
    let timer; // 타이머 변수
    let timerStart; // 타이머 시작 시간

    // 비밀번호 변경 필드 보이기/숨기기
    document.getElementById('changePasswordButton').addEventListener('click', function() {
        var passwordChangeFields = document.getElementById('passwordChangeFields');
        if (passwordChangeFields.style.display === 'none' || passwordChangeFields.style.display === '') {
            passwordChangeFields.style.display = 'block';
        } else {
            passwordChangeFields.style.display = 'none';
        }
    });

    // 비밀번호 변경 취소 버튼 클릭 시 비밀번호 변경 필드 숨기기
    document.getElementById('cancelPasswordChange').addEventListener('click', function() {
        document.getElementById('passwordChangeFields').style.display = 'none';
    });

    // 휴대폰 번호 수정 필드 보이기/숨기기
    document.getElementById('changePhoneButton').addEventListener('click', function() {
        var phoneChangeFields = document.getElementById('phoneChangeFields');
        if (phoneChangeFields.style.display === 'none' || phoneChangeFields.style.display === '') {
            phoneChangeFields.style.display = 'block';
        } else {
            phoneChangeFields.style.display = 'none';
        }
    });

    // 휴대폰 번호 변경 취소 버튼 클릭 시 휴대폰 변경 필드 숨기기
    document.getElementById('cancelPhoneChange').addEventListener('click', function() {
        document.getElementById('phoneChangeFields').style.display = 'none';
    });

    // 비밀번호 유효성 검사 함수
    function validatePassword() {
        const password = document.getElementById("password").value;
        const currentPassword = document.getElementById("currentPassword").value;
        const newPassword = document.getElementById("newPassword").value;
        const confirmNewPassword = document.getElementById("confirmNewPassword").value;
        const currentPwdMsg = document.getElementById("currentPwdMsg");
        const pwdMsg = document.getElementById("pwdMsg");
        const pwdConfirmMsg = document.getElementById("pwdConfirmMsg");

        const pwdPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;

        if (currentPassword === password) {
            currentPwdMsg.innerHTML = "현재 비밀번호가 일치합니다.";
            currentPwdMsg.style.color = "#aaa";
        } else {
            currentPwdMsg.innerHTML = "현재 비밀번호가 일치하지 않습니다.";
            currentPwdMsg.style.color = "red";
        }

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
    document.getElementById("currentPassword").addEventListener('input', validatePassword);

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

    function startTimer() {
        const timerDisplay = document.getElementById("timerMsg");
        let timeLeft = 60;

        timerDisplay.innerHTML = "60초 남음";

        timer = setInterval(function() {
            timeLeft--;
            if (timeLeft <= 0) {
                clearInterval(timer);
                timerDisplay.innerHTML = "";
                alert("60초가 초과되었습니다.");
                document.getElementById('sendVerificationCode').disabled = false;
            } else {
                timerDisplay.innerHTML = `${timeLeft}초 남음`;
            }
        }, 1000);
    }

    function validateVerificationCode() {
        const verificationCode = document.getElementById("verificationCode").value;
        const verificationCodeMsg = document.getElementById("codeMsg"); // 같은 ID를 사용해 메시지 표시

        const codePattern = /^\d{6}$/;

        if (codePattern.test(verificationCode)) {
            verificationCodeMsg.innerHTML = "유효한 인증번호입니다.";
            verificationCodeMsg.style.color = "#aaa";
        } else {
            verificationCodeMsg.innerHTML = "6자리 숫자로 입력하세요.";
            verificationCodeMsg.style.color = "red";
        }
    }

    document.getElementById("sendVerificationCode").addEventListener('click', function() {
        validatePhone();
        if (document.getElementById("phoneMsg").style.color === "rgb(170, 170, 170)") {
            this.disabled = true; // 인증번호 전송 버튼 비활성화
            startTimer(); // 타이머 시작
        }
    });

    // 인증번호 입력 필드에 이벤트 리스너 추가
    document.getElementById("verificationCode").addEventListener('input', validateVerificationCode);

    document.getElementById('newPhone').addEventListener('input', validatePhone);
});



// 회원탈퇴-----------------------------------------------------------------
// 탈퇴시 기타 사유 텍스트 박스 유효성 검사
document.addEventListener('DOMContentLoaded', function() {
    // 'otherReason' 텍스트 영역, 'charCount' 카운터 문구, 'errorMessage' 에러 메시지 요소 가져오기
    var otherReason = document.getElementById('otherReason');
    var charCount = document.getElementById('charCount');
    var errorMessage = document.getElementById('errorMessage');

    otherReason.addEventListener('input', function () {
        // 입력된 텍스트의 길이 계산
        var textLength = this.value.length;
        // 현재 문자 수와 최대 문자 수를 표시
        charCount.textContent = textLength + " / 250";

        // 문자 수가 250자를 초과하면 에러 메시지를 표시하고 추가 입력 방지
        if (textLength > 250) {
            errorMessage.style.display = 'block';
            // 초과된 문자 삭제
            this.value = this.value.substring(0, 250);
            // 문자 수를 다시 계산하고 표시
            charCount.textContent = "250 / 250";
        } else {
            // 문자 수가 250자 이내면 에러 메시지를 숨김
            errorMessage.style.display = 'none';
        }
    });

    // 폼 제출 이벤트 리스너 추가
    document.getElementById('withdrawalForm').addEventListener('submit', function(event) {
        // 'otherReason' 텍스트 영역의 값 가져오기
        var otherReasonText = otherReason.value;
        // 텍스트의 길이가 250자를 초과하면 폼 제출 방지 및 경고 메시지 표시
        if (otherReasonText.length > 250) {
            event.preventDefault(); // 폼 제출 방지
            alert("기타 사유는 250자 이내로 입력해 주세요.");
        }
    });
});
