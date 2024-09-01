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
                onOpen: function() {
                    console.log('Check-in 캘린더 열림');
                }
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
                onOpen: function() {
                    console.log('Check-out 캘린더 열림');
                }
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
});
