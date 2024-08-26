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
