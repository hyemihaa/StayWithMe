document.addEventListener('DOMContentLoaded', function () {
            // 체크인 날짜 입력 필드에 대한 날짜 선택기 초기화
            var checkinDateInput = document.getElementById('checkin_date');
            $(checkinDateInput).datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayHighlight: true
            }).on('changeDate', function (selected) {
                // 선택된 체크인 날짜를 기준으로 체크아웃 날짜 선택기의 최소 날짜 설정
                var minDate = new Date(selected.date.valueOf());
                $('#checkout_date').datepicker('setStartDate', minDate);

                // 콘솔에 체크인 날짜 로그 출력
                console.log('체크인 날짜가 변경되었습니다: ' + selected.format('yyyy-mm-dd'));
            });

            // 체크아웃 날짜 입력 필드에 대한 날짜 선택기 초기화
            var checkoutDateInput = document.getElementById('checkout_date');
            $(checkoutDateInput).datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayHighlight: true
            }).on('changeDate', function (selected) {
                // 선택된 체크아웃 날짜를 기준으로 체크인 날짜 선택기의 최대 날짜 설정
                var maxDate = new Date(selected.date.valueOf());
                $('#checkin_date').datepicker('setEndDate', maxDate);

                // 콘솔에 체크아웃 날짜 로그 출력
                console.log('체크아웃 날짜가 변경되었습니다: ' + selected.format('yyyy-mm-dd'));
            });


                // 버튼 클릭 이벤트 핸들러
                document.getElementById('submit_button').addEventListener('click', function(event) {
                // 기본 폼 제출 동작을 막지 않도록 함
                // event.preventDefault(); // 주석 처리 또는 제거


                //날짜가 선택되면 폼 제출
                    document.querySelector('form').submit();

            });
        });