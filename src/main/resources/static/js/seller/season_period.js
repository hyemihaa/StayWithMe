document.addEventListener("DOMContentLoaded", function() {
    const periodsTbody = document.getElementById('periods-tbody');

    // 서버에서 추가 요금 데이터를 받아오는 함수
    function loadExtraRates() {
        fetch('/getExtraRates')
            .then(response => response.json())
            .then(data => {
                data.forEach(item => {
                    const newRow = document.createElement('tr');
                    newRow.innerHTML = `
                        <td>${item.extraName}</td>
                        <td><span style="color: grey;">${item.extraDateStart} ~ ${item.extraDateEnd}</span></td>
                        <td>
                            <input type="date" class="form-control form-control-sm d-inline" style="width: 45%;" value="${item.extraDateStart}" data-extra-name="${item.extraName}" data-type="start"> ~
                            <input type="date" class="form-control form-control-sm d-inline" style="width: 45%;" value="${item.extraDateEnd}" data-extra-name="${item.extraName}" data-type="end">
                        </td>
                        <td>
                            <button class="btn btn-outline-danger btn-sm delete-period-btn" data-extra-name="${item.extraName}">삭제</button>
                        </td>
                    `;
                    periodsTbody.appendChild(newRow);
                });
            });
    }

    // 삭제 버튼 처리
    periodsTbody.addEventListener('click', function(event) {
        if (event.target.classList.contains('delete-period-btn')) {
            const extraName = event.target.getAttribute('data-extra-name');
            fetch(`/deletePeriod?extraName=${extraName}`, { method: 'DELETE' })
                .then(response => response.json())
                .then(result => {
                    if (result.success) {
                        event.target.closest('tr').remove();
                    } else {
                        alert('삭제에 실패했습니다.');
                    }
                });
        }
    });

    // 기간 수정 버튼 클릭 시 서버에 수정된 데이터 전송
    document.getElementById('savePeriodsBtn').addEventListener('click', function() {
        const periods = [];
        const inputs = document.querySelectorAll('input[type="date"]');

        inputs.forEach(input => {
            const extraName = input.getAttribute('data-extra-name');
            const type = input.getAttribute('data-type');
            const value = input.value;

            let period = periods.find(p => p.extraName === extraName);
            if (!period) {
                period = { extraName: extraName, extraDateStart: '', extraDateEnd: '' };
                periods.push(period);
            }

            if (type === 'start') {
                period.extraDateStart = value;
            } else if (type === 'end') {
                period.extraDateEnd = value;
            }
        });

        fetch('/updatePeriods', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(periods)
        })
            .then(response => response.json())
            .then(result => {
                if (result.success) {
                    alert('기간이 성공적으로 수정되었습니다.');
                } else {
                    alert('기간 수정에 실패했습니다.');
                }
            });
    });

    loadExtraRates(); // 페이지 로드 시 추가 요금 데이터 로드
});
