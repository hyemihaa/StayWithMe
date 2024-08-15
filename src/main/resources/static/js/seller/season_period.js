document.addEventListener("DOMContentLoaded", function() {
    const savePeriodsBtn = document.getElementById('savePeriodsBtn'); // 기간 수정 버튼 요소를 가져옵니다.

    savePeriodsBtn.addEventListener('click', function(event) {
        event.preventDefault(); // 기본 폼 제출 동작을 막습니다.

        const form = document.querySelector('form'); // 폼 요소를 가져옵니다.
        const formData = new FormData(form); // FormData 객체로 변환합니다.

        // 서버로 수정된 기간 정보를 POST 요청으로 전송합니다.
        fetch('/periods-update', {
            method: 'POST',
            body: formData // 폼 데이터 형식으로 전송
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('기간 수정 요청에 실패했습니다.');
            }
        })
        .then(result => {
            if (result.success) {
                alert('기간이 성공적으로 수정되었습니다.');

                // 실시간으로 수정된 내용을 반영하는 코드
                const rows = document.querySelectorAll('#periods-tbody tr'); // 모든 테이블 행을 가져옵니다.
                rows.forEach(row => {
                    const extraNameElement = row.querySelector('.extra-name');
                    if (extraNameElement) { // 요소가 존재하는지 확인
                        const extraName = extraNameElement.innerText.trim(); // 요금타입 이름을 가져옵니다.
                        const startInput = row.querySelector('input[name*="extraDateStart"]'); // 시작일 입력 필드를 가져옵니다.
                        const endInput = row.querySelector('input[name*="extraDateEnd"]'); // 종료일 입력 필드를 가져옵니다.

                        // 수정된 기간을 텍스트로 업데이트
                        const periodText = `${startInput.value} ~ ${endInput.value}`;
                        const periodTextElement = row.querySelector('.period-text');
                        if (periodTextElement) {
                            periodTextElement.innerText = periodText;
                        }
                    }
                });

            } else {
                alert('기간 수정에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error("기간 수정 요청 중 오류가 발생했습니다.", error);
        });
    });

    // 삭제 버튼 클릭 이벤트 처리
    const periodsTbody = document.getElementById('periods-tbody'); // 기간 테이블의 tbody 요소를 가져옵니다.
    periodsTbody.addEventListener('click', function(event) {
        if (event.target.classList.contains('delete-period-btn')) { // 삭제 버튼 클릭 시
            const extraName = event.target.getAttribute('data-extra-name'); // 해당 요금타입 이름을 가져옵니다.
            console.log(`삭제 요청: ${extraName}`); // 디버그 메시지: 삭제 요청한 요금타입 이름 출력

            fetch(`/extra-delete?extraName=${extraName}`, { method: 'DELETE' }) // 서버로 삭제 요청을 전송합니다.
                .then(response => {
                    console.log(`서버 응답 상태: ${response.status}`); // 디버그 메시지: 서버 응답 상태 출력
                    return response.json();
                })
                .then(result => {
                    if (result.success) {
                        event.target.closest('tr').remove(); // 성공 시 해당 행을 삭제합니다.
                        console.log(`삭제 성공: ${extraName}`); // 디버그 메시지: 삭제 성공 메시지 출력
                    } else {
                        alert('삭제에 실패했습니다.');
                        console.error(`삭제 실패: ${extraName}`); // 디버그 메시지: 삭제 실패 메시지 출력
                    }
                })
                .catch(error => {
                    console.error("삭제 요청 중 오류가 발생했습니다.", error);
                });
        }
    });
});
