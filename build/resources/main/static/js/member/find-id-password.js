let isVerified = false; // 인증 여부를 확인하는 플래그 추가

let timer;
let timeLeft = 1200; // 3분 (180초)

function startTimer(authMsgId, authInputId) { // 타이머 시작 시 메시지와 입력란 ID를 인자로 받음
    clearInterval(timer); // 이전 타이머가 있다면 초기화
    timeLeft = 1200; // 시간 초기화

    const authMsg = document.getElementById(authMsgId);
    const authInput = document.getElementById(authInputId);
    authInput.disabled = false; // 인증번호 입력란 활성화

    timer = setInterval(function() {
        if (timeLeft <= 0) {
            clearInterval(timer); // 타이머 종료
            alert("인증시간이 초과되었습니다. 다시 시도해주세요.");
            authInput.disabled = true; // 인증번호 입력란 비활성화
            isVerified = false; // 인증시간이 초과되면 인증 실패 처리
        } else {
            authMsg.innerHTML = `제한 시간: ${timeLeft}초`;
            authMsg.style.color = "#aaa";
        }
        timeLeft--;
    }, 1000); // 1초마다 실행
}

// 휴대전화 인증
// 인증번호 전송 요청
function requestSMS(formType) { // 폼 타입을 인자로 받음
    const phoneInputId = formType === 'findPassword' ? 'findPassword_phone' : 'findUserId_phoneNumber';
    const phoneMsgId = formType === 'findPassword' ? 'findPassword_phoneMsg' : 'findUserId_phoneMsg';
    const randomNumId = formType === 'findPassword' ? 'findPassword_randomNum' : 'findUserId_randomNum';
    const authMsgId = formType === 'findPassword' ? 'findPassword_authMsg' : 'findUserId_authMsg';
    const authInputId = formType === 'findPassword' ? 'findPassword_authNumber' : 'findUserId_authNumber';

    const phone = document.getElementById(phoneInputId).value; // 사용자 입력 휴대전화 번호
    const phoneMsg = document.getElementById(phoneMsgId); // 휴대전화 번호 유효성 메시지
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
            document.getElementById(randomNumId).value = response.certificationCode; // 인증번호를 hidden input에 저장
            startTimer(authMsgId, authInputId); // 인증번호 제한시간 시작
            isVerified = false; // 인증요청 후 인증되지 않은 상태로 설정
        },
        error: function(xhr, status, error) {
            alert("인증번호 발송에 실패했습니다. 다시 시도해주세요.");
        }
    });
}

// 인증번호 확인
function checkSMS(formType) { // 폼 타입을 인자로 받음
    const authInputId = formType === 'findPassword' ? 'findPassword_authNumber' : 'findUserId_authNumber';
    const randomNumId = formType === 'findPassword' ? 'findPassword_randomNum' : 'findUserId_randomNum';

    const userCode = document.getElementById(authInputId).value; // 사용자가 입력한 인증번호
    const serverCode = document.getElementById(randomNumId).value;

    console.log("사용자 입력 인증번호:", userCode);
    console.log("서버에서 받은 인증번호:", serverCode);

    if (!userCode) {
        alert('인증번호를 입력해 주세요.');
        return;
    }

    if (userCode === serverCode) {
        clearInterval(timer); // 타이머 중지
        alert("인증이 정상적으로 완료되었습니다.");
        isVerified = true; // 인증 성공 시 플래그 설정
    } else {
        alert("인증번호가 올바르지 않습니다.");
        isVerified = false; // 인증 실패 시 플래그 해제
    }
}

// 아이디 찾기
function findUserId() {
    const username = document.getElementById("findUserId_username").value;
    const phone = document.getElementById("findUserId_phoneNumber").value;

    // 빈 문자열 체크
    if (!username) {
        alert('이름을 입력해 주세요.');
        return;
    }

    if (!phone) {
        alert('휴대전화번호를 입력해 주세요.');
        return;
    }

    if (!isVerified) { // 인증 여부를 확인
        alert("인증이 완료되지 않았습니다. 인증을 먼저 진행해 주세요.");
        return;
    }

    $.ajax({
        type: "POST",  // 여전히 POST 요청을 사용하여 파라미터를 전달
        url: "/find-userId",
        data: { userName: username, userPhone: phone },  // 쿼리 파라미터로 전달
        success: function(response) {
            if (response.startsWith("error: ")) {
                alert(response.substring(7));
                window.location.href = "/lostpass"; // 아이디 찾기
            } else {
                alert("아이디는 '" + response + "' 입니다.");
                window.location.href = "/signform"; // 로그인 페이지로 리디렉션
            }
        },
        error: function(xhr, status, error) {
            console.error("에러 발생:", status, error);
            alert("아이디 찾기 요청에 실패했습니다. 다시 시도해주세요.");
        }
    });
}

// 비밀번호 찾기
function findPassword() {
    const userId = document.getElementById("findPassword_userId").value;
    const phone = document.getElementById("findPassword_phone").value;

    // 빈 문자열 체크
    if (!userId) {
        alert('아이디를 입력해 주세요.');
        return;
    }

    if (!phone) {
        alert('휴대전화번호를 입력해 주세요.');
        return;
    }

    if (!isVerified) { // 인증 여부를 확인
        alert("인증이 완료되지 않았습니다. 인증을 먼저 진행해 주세요.");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/find-password",
        data: { userId: userId, userPhone: phone },  // 쿼리 파라미터로 전달
        success: function(response) {
            if (response.startsWith("error: ")) {
                alert(response.substring(7));
                window.location.href = "/lostpass"; // 에러발생시 아이디 찾기 & 비밀번호 찾기페이지
            } else {
                alert("임시 비밀번호는 '" + response + "' 입니다. \n마이 페이지에서 비밀번호를 변경해 주세요");
                window.location.href = "/signform"; // 로그인 페이지로 리디렉션
            }
        },
        error: function(xhr, status, error) {
            console.error("에러 발생:", status, error);
            alert("임시 비밀번호 발급 요청에 실패했습니다. 다시 시도해주세요.");
        }
    });
}

