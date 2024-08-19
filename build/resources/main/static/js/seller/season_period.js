document.addEventListener("DOMContentLoaded", function() {
    const addPeriodBtn = document.getElementById('add-period-btn');
    const periodsTbody = document.getElementById('periods-tbody');

    addPeriodBtn.addEventListener('click', function() {
        const newRow = document.createElement('tr');

        newRow.innerHTML = `
            <td>
                <select class="form-select form-select-sm">
                    <option value="비수기">비수기</option>
                    <option value="준성수기">준성수기</option>
                    <option value="성수기">성수기</option>
                </select>
            </td>
            <td><input type="text" class="form-control form-control-sm" placeholder="기간명"></td>
            <td>
                <input type="date" class="form-control form-control-sm d-inline" style="width: 45%;"> ~
                <input type="date" class="form-control form-control-sm d-inline" style="width: 45%;">
            </td>
            <td>
                <button class="btn btn-outline-danger btn-sm delete-period-btn">삭제</button>
            </td>
        `;

        periodsTbody.appendChild(newRow);
    });

    periodsTbody.addEventListener('click', function(event) {
        if (event.target.classList.contains('delete-period-btn')) {
            event.target.closest('tr').remove();
        }
    });
});