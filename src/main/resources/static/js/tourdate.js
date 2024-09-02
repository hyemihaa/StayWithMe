document.addEventListener('DOMContentLoaded', function () {
    const checkinInputs = document.querySelectorAll('.checkin-date-input');
        const checkoutInputs = document.querySelectorAll('.checkout-date-input');

    if (checkinInputs.length > 0) {
        checkinInputs.forEach(function(checkinInput) {
            flatpickr(checkinInput, {
                locale: 'ko',
                dateFormat: "Y-m-d",
                minDate: "today",
                zIndex: 9999,
            });
        });
    }

    if (checkoutInputs.length > 0) {
        checkoutInputs.forEach(function(checkoutInput) {
            flatpickr(checkoutInput, {
                locale: 'ko',
                dateFormat: "Y-m-d",
                minDate: "today",
                zIndex: 9999,
            });
        });
    }

    // 인원 수 조절 기능
    const guestCountElement = document.getElementById('guest-count');
    if (guestCountElement) {
        let guestCount = parseInt(guestCountElement.value, 10);

        document.getElementById('decrease-guests').addEventListener('click', function() {
            if (guestCount > 1) {
                guestCount--;
                guestCountElement.value = guestCount;
            }
        });

        document.getElementById('increase-guests').addEventListener('click', function() {
            guestCount++;
            guestCountElement.value = guestCount;
        });
    }

    // 범위 슬라이더 초기화
    (function() {
        const parent = document.querySelector(".range-slider");
        if(!parent) return;

        const
            rangeS = parent.querySelectorAll("input[type=range]"), // 범위 슬라이더
            numberS = parent.querySelectorAll("input[type=number]"); // 값 입력 필드

        rangeS.forEach(function(el) {
            el.oninput = function() {
                let slide1 = parseFloat(rangeS[0].value), // 좌측 슬라이더 값
                    slide2 = parseFloat(rangeS[1].value); // 우측 슬라이더 값

                // 좌측 슬라이더가 우측 슬라이더 값을 넘지 않도록 조정
                if (this === rangeS[0] && slide1 >= slide2) {
                    slide1 = slide2 - 50000; // 슬라이더 간 최소 차이 설정
                } else if (this === rangeS[1] && slide2 <= slide1) {
                    slide2 = slide1 + 50000; // 슬라이더 간 최소 차이 설정
                }

                rangeS[0].value = slide1;
                rangeS[1].value = slide2;

                numberS[0].value = slide1;
                numberS[1].value = slide2;

            }
        });

        numberS.forEach(function(el) {
            el.oninput = function() {
                let number1 = parseFloat(numberS[0].value),
                    number2 = parseFloat(numberS[1].value);

                if (number1 >= number2) {
                    number1 = number2 - 1; // 값 입력 필드 간 최소 차이 설정
                }

                numberS[0].value = number1;
                numberS[1].value = number2;

                rangeS[0].value = number1;
                rangeS[1].value = number2;
            }
        });

    })();

    // 토글 버튼 기능 초기화
    const toggles = document.querySelectorAll('.toggle-input');

    toggles.forEach(toggle => {
        toggle.addEventListener('change', function() {
            if (this.checked) {
                console.log(`${this.nextElementSibling.innerText} 토글 버튼이 켜졌습니다.`);
            } else {
                console.log(`${this.nextElementSibling.innerText} 토글 버튼이 꺼졌습니다.`);
            }
        });
    });

    // 옵션 제목을 클릭하면 내용이 표시되도록 설정
    const toggleTitle = document.getElementById('toggle-options-title');
    const toggleContent = document.getElementById('toggle-options-content');

    toggleTitle.addEventListener('click', function() {
        toggleContent.classList.toggle('open');
    });
});
