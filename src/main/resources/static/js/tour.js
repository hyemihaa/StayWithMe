document.addEventListener('DOMContentLoaded', function() {
    // 검색 폼, 검색 버튼, 컨텐츠 영역, 로더 아이콘 요소들을 가져옴
    const searchForm = document.getElementById('searchForm');  // 검색 필터 폼
    const searchButton = document.getElementById('searchButton');  // 검색 버튼
    const contentDiv = document.getElementById('content');  // 결과가 추가될 영역
    const loader = document.getElementById('loader');  // 로딩 표시

    // 페이지 상태를 관리하는 변수들
    let currentPage = 2;  // 페이지 번호. 첫 번째 페이지는 이미 로드된 상태이므로 2페이지부터 시작
    let isLoading = false;  // 현재 로딩 중인지 여부를 표시
    let allContentLoaded = false;  // 모든 콘텐츠가 로드되었는지 여부를 나타냄

    // URL에서 검색 조건을 불러와 폼에 채우고 초기 데이터를 로드하지 않음
    loadSearchConditionsFromURL();

    // 검색 버튼 클릭 시 새로운 검색을 수행
    searchButton.addEventListener('click', function() {
        // 페이지를 초기화하고 기존 검색 결과를 지움
        currentPage = 1;
        contentDiv.innerHTML = '';  // 기존 검색 결과 초기화
        allContentLoaded = false;

        // 검색 결과를 로드
        loadContent();

        // URL에 현재 검색 조건을 반영
        const formData = new FormData(searchForm);
        updateURLWithSearchConditions(formData);
    });

    // 스크롤 시 추가 컨텐츠를 로드
    window.addEventListener('scroll', () => {
        // 스크롤이 끝에 가까워졌을 때, 추가 콘텐츠를 로드
        if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 200 && !isLoading && !allContentLoaded) {
            loadContent();
        }
    });

    // 검색 조건을 사용하여 컨텐츠를 로드하는 함수
    function loadContent() {
        // 모든 콘텐츠가 이미 로드된 경우 함수 종료
        if (allContentLoaded) return;

        // 로더 아이콘을 표시하고 로딩 상태를 true로 설정
        if (loader) {
            isLoading = true;
            loader.style.display = 'block';
        }

        // 폼 데이터를 생성하고 현재 페이지 번호를 추가
        const formData = new FormData(searchForm);
        formData.append('currentPage', currentPage);  // 현재 페이지를 폼 데이터에 추가

        const params = new URLSearchParams();
        formData.forEach((value, key) => {
            params.append(key, value);
        });

        // AJAX 요청을 통해 데이터 가져오기
        fetch(`/get-place-items?${params.toString()}`, { method: 'GET' })
            .then(response => {
                if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                return response.json();
            })
            .then(data => {
                if (data.length === 0) {
                    allContentLoaded = true;
                } else {
                    data.forEach(place => {
                        const placeHtml = `
                            <div class="destination">
                                <a href="/hotel-single?boardNo=${place.boardNo}"
                                   class="img img-3 d-flex justify-content-center align-items-center"
                                   style="background-image: url('/accommodationImages/${place.fileName}');">
                                    <div class="icon d-flex justify-content-center align-items-center">
                                        <span class="icon-search2"></span>
                                    </div>
                                </a>
                                <div class="text-tour p-3">
                                    <div class="d-flex">
                                        <div class="one">
                                            <h3><a href="/hotel-single?boardNo=${place.boardNo}">${place.boardName}</a></h3>
                                        </div>
                                        <div class="two">
                                            <p>${place.boardType}</p>
                                        </div>
                                    </div>
                                    <span><i class="icon-map-o"></i> &nbsp; <span>${place.boardAddress}</span></span>
                                    <hr>
                                    <span class="price" style="font-weight:bold; font-size: 17px;">
                                        ${place.boardCount}원~
                                        <span style="font-size: 10px;">&nbsp;/&nbsp;1박</span>
                                    </span>
                                    <p class="bottom-area d-flex">
                                        <span class="ml-auto"><a href="/hotel-single?boardNo=${place.boardNo}">자세히보기</a></span>
                                    </p>
                                </div>
                            </div>`;
                        contentDiv.insertAdjacentHTML('beforeend', placeHtml);
                    });
                    currentPage++;
                }
                isLoading = false;
                if (loader) {
                    loader.style.display = 'none';
                }
            })
            .catch(error => {
                console.error('Error:', error);
                isLoading = false;
                if (loader) {
                    loader.style.display = 'none';
                }
            });

    }

    // URL에서 검색 조건을 불러와 폼에 채우는 함수
    function loadSearchConditionsFromURL() {
        const urlParams = new URLSearchParams(window.location.search);

        for (const [key, value] of urlParams.entries()) {
            const element = searchForm.elements.namedItem(key);
            if (element) element.value = value;  // URL의 검색 조건을 폼에 반영
        }
    }

    // 검색 조건을 URL에 반영하는 함수
    function updateURLWithSearchConditions(formData) {
        const searchParams = new URLSearchParams();
        formData.forEach((value, key) => searchParams.append(key, value));

        const newURL = `${window.location.pathname}?${searchParams.toString()}`;
        history.replaceState(null, '', newURL);  // 검색 조건이 반영된 URL로 대체
    }
});
