// 회원가입 유효성 검사
// 인증 완료 여부를 저장하는 변수
let isVerified = false;
// 유효성 검사 결과 저장 변수
let isNameValid = false;
let isPwdValid = false;
let isIdValid = false;
let isPwdMatch = false;


// 아이디 중복체크
function idCheck() {
    const signUpId = document.getElementById("signUpId").value;
    const idMsg = document.getElementById("idMsg");

    // 영문 소문자와 숫자만 허용, 6자리 이상 20자리 이하
    const idPattern = /^(?=.*[a-z])[a-z0-9]{6,20}$/;

    // 아이디 입력란이 비어있는지 확인
    if (!signUpId) {
        alert('아이디를 입력해 주세요.');
        return; // 아이디가 없으면 함수를 종료
    }

    // 유효성 검사
    if (!idPattern.test(signUpId)) {
        idMsg.innerHTML = "영어 소문자 + 숫자 6~20자리로 입력하세요.";
        idMsg.style.color = "red";
        isIdValid = false;
        return;
    }

    console.log(signUpId);
    console.log("Checking ID");

    // AJAX를 통해 중복 체크
    $.ajax({
        type: "POST",
        url: "/idcheck",
        data: JSON.stringify({ userId: signUpId }), // JSON 형식으로 데이터를 전송
        contentType: "application/json", // 서버가 JSON 형식을 인식하도록 지정
        success: function(response) {
            console.log(response);
            if (response.result > 0) {
                alert('이미 사용 중인 아이디입니다.');
                idMsg.innerHTML = "아이디가 이미 사용 중입니다.";
                idMsg.style.color = "red";
                isIdValid = false;
            } else {
                alert('사용 가능한 아이디입니다.');
                idMsg.innerHTML = "사용 가능한 아이디입니다.";
                idMsg.style.color = "#aaa";
                isIdValid = true;
            }
        },
        error: function(err) {
            console.error('AJAX 요청 실패:', err);
            idMsg.textContent = '서버 오류가 발생했습니다. 나중에 다시 시도해 주세요.';
            idMsg.style.color = "red";
            isIdValid = false;
        }
    });
}

// 이름 유효성 검사
function validName() {
    const signUpName = document.getElementById("signUpName").value;
    const nameMsg = document.getElementById("nameMsg");

    /*최소 2자, 최대 10자의 한글만 가능합니다*/
    const idPattern = /^[가-힣]{2,10}$/;

    if(idPattern.test(signUpName)) {
        nameMsg.innerHTML = "사용 가능합니다."
        nameMsg.style.color = "#aaa";
        isNameValid = true;
    }
    else {
        nameMsg.innerHTML = "한글만 가능 합니다."
        nameMsg.style.color = "red";
        isNameValid = false;
    }
}

// 비밀번호 유효성 검사
function validatePassword() {
    const signUpPwd = document.getElementById("signUpPwd").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const pwdMsg = document.getElementById("pwdMsg");
    const pwdConfirmMsg = document.getElementById("pwdConfirmMsg");

    const pwdPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;


    if (pwdPattern.test(signUpPwd)) {
        pwdMsg.innerHTML = "유효한 비밀번호입니다.";
        pwdMsg.style.color = "#aaa";
        isPwdValid = true;
    } else {
        pwdMsg.innerHTML = "영문+숫자+특수문자를 포함하여 8~20자로 입력하세요.";
        pwdMsg.style.color = "red";
        isPwdValid = false;
    }

    // 비밀번호 확인 일치 검사
    if (signUpPwd !== "" && signUpPwd === confirmPassword) {
        pwdConfirmMsg.innerHTML = "비밀번호가 일치합니다.";
        pwdConfirmMsg.style.color = "#aaa";
        isPwdMatch = true;
    } else if (confirmPassword !== "") {
        pwdConfirmMsg.innerHTML = "비밀번호가 일치하지 않습니다.";
        pwdConfirmMsg.style.color = "red";
        isPwdMatch = false;
    } else {
        pwdConfirmMsg.innerHTML = "";
        isPwdMatch = false; // 아무 값도 없을 경우에도 false로 설정
    }
}


// 타이머 함수 추가
let timer;
let timeLeft = 180; // 3분 (180초)

function startTimer() {
    clearInterval(timer); // 이전 타이머가 있다면 초기화
    timeLeft = 180; // 시간 초기화

    const authMsg = document.getElementById("authMsg");
    const authInput = document.getElementById("Authentication");
    authInput.disabled = false; // 인증번호 입력란 활성화

    timer = setInterval(function() {
        if (timeLeft <= 0) {
            clearInterval(timer); // 타이머 종료
            alert("인증시간이 초과되었습니다. 다시 시도해주세요.");
            authInput.disabled = true; // 인증번호 입력란 비활성화
        } else {
            authMsg.innerHTML = `제한 시간: ${timeLeft}초`;
            authMsg.style.color = "#aaa";
        }
        timeLeft--;
    }, 1000); // 1초마다 실행
}

// 휴대전화 인증
// 인증번호 전송 요청
function requestSMS() {
    const phone = document.getElementById("phone").value; // 사용자 입력 휴대전화 번호
    const phoneMsg = document.getElementById("phoneMsg"); // 휴대전화 번호 유효성 메시지
    // 휴대전화 번호 정규식: 010으로 시작하며, 뒤에 8자리 숫자가 나옴
    const phonePattern = /^010\d{8}$/;

    if (!phone) {
        alert('휴대전화 번호를 입력해 주세요.');
        return;
    }
    // 휴대전화 번호 유효성 검사
    if (!phonePattern.test(phone)) {
        phoneMsg.innerHTML = "유효한 휴대전화 번호를 입력해 주세요.";
        phoneMsg.style.color = "red";
        return;
    }
    // 유효한 번호라면 메시지를 제거
    else {
        phoneMsg.innerHTML = "";
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

// 인증번호 확인
function checkSMS() {
    const userCode = document.getElementById("Authentication").value; // 사용자가 입력한 인증번호
    const serverCode = document.getElementById("randomNum").value;

    console.log("사용자 입력 인증번호:", userCode);
    console.log("서버에서 받은 인증번호:", serverCode);

    if (!userCode) {
        alert('인증번호를 입력해 주세요.');
        return;
    }

    if (userCode === serverCode) {
        clearInterval(timer); // 타이머 중지
        isVerified = true;
        alert("인증이 정상적으로 완료되었습니다.");
    } else {
        alert("인증번호가 올바르지 않습니다.");
        isVerified = false;
    }
}

// 폼 제출 시 호출되는 함수
function validateForm(event) {
    if (!isIdValid || !isPwdValid || !isNameValid || !isVerified || !isPwdMatch) { // 하나라도 false가 있으면 폼 제출 막음
        alert("올바르게 기입하였는지 확인해주세요.");
        event.preventDefault(); // 폼 제출을 막습니다
    }
    else {
         // 모든 검사가 통과되었을 때 회원가입 성공 메시지
         alert("회원가입에 성공하였습니다.");
         }
}

// 폼 요소에 이벤트 리스너 추가
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('signUpForm'); // 폼 선택
    form.addEventListener('submit', validateForm); // submit 이벤트 리스너 추가
});


// 로그인
document.addEventListener("DOMContentLoaded", function() {
    // 로그인 실패 메시지
    const errorMessage = document.getElementById("errorMessage");
    if (errorMessage && errorMessage.value.trim() !== "") {
        alert(errorMessage.value.trim());
    }
});


