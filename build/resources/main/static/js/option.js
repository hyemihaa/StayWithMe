<script>
    const selectElement = document.getElementById('tour-option-select');
    const dataList = document.getElementById('data-list');

    selectElement.addEventListener('change', function() {
        const selectedValue = parseInt(this.value);
        const items = Array.from(dataList.children);

        // 정렬 함수
        const sortedItems = items.sort((a, b) => {
            switch (selectedValue) {
                case 1: // 평점높은순
                    return b.dataset.rating - a.dataset.rating;
                case 2: // 리뷰많은순
                    return b.dataset.reviews - a.dataset.reviews;
                case 3: // 낮은가격순
                    return a.dataset.price - b.dataset.price;
                case 4: // 높은가격순
                    return b.dataset.price - a.dataset.price;
                default: // 추천순 (기본 정렬)
                    return 0; // 여기서 기본 정렬 로직을 설정할 수 있습니다.
            }
        });

        // 정렬된 리스트 업데이트
        dataList.innerHTML = '';
        sortedItems.forEach(item => {
            dataList.appendChild(item);
        });
    });
</script>