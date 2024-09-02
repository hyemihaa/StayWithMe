document.addEventListener('DOMContentLoaded', function () {
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
