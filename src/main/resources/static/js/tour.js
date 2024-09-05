document.addEventListener('DOMContentLoaded', function() {
    const searchForm = document.getElementById('searchForm');
    const searchButton = document.getElementById('searchButton');
    const contentDiv = document.getElementById('content');

    let currentPage = 1;  // 초기 페이지는 1
    let isLoading = false;
    let allContentLoaded = false;

    searchButton.addEventListener('click', function() {
        currentPage = 1; // 검색 버튼 클릭 시 1페이지로 초기화
        contentDiv.innerHTML = '';  // 기존 검색 결과 초기화
        allContentLoaded = false;   // 모든 데이터가 로드되지 않음으로 설정

        loadContent();  // 첫 데이터를 로드

        if (searchForm) {
            const formData = new FormData(searchForm);
            updateURLWithSearchConditions(formData);
        } else {
            console.error("검색 폼을 찾을 수 없습니다.");
        }
    });

    window.addEventListener('scroll', () => {
        if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 200 && !isLoading && !allContentLoaded) {
            currentPage++;  // 다음 페이지로 이동
            loadContent();
        }
    });

    function loadContent() {
        if (allContentLoaded) return;  // 모든 데이터가 로드된 경우 종료

        isLoading = true;

        if (searchForm) {
            const formData = new FormData(searchForm);
            formData.append('currentPage', currentPage);  // 현재 페이지 번호 추가

            const params = new URLSearchParams();
            formData.forEach((value, key) => {
                params.append(key, value);
            });

            fetch(`/get-place-items?${params.toString()}`, { method: 'GET' })
                .then(response => {
                    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                    return response.text();
                })
                .then(text => {
                    if (!text) {
                        console.log('No more data to load.');
                        allContentLoaded = true;
                        return;  // 더 이상 데이터가 없으면 종료
                    }

                    let data;
                    try {
                        data = JSON.parse(text);
                    } catch (e) {
                        throw new Error('Invalid JSON format');
                    }

                    if (!Array.isArray(data) || data.length === 0) {
                        allContentLoaded = true;
                        return;  // 데이터가 없으면 종료
                    }

                    data.forEach(place => {
                        if (!document.querySelector(`#place-${place.boardNo}`)) {
                            const placeHtml = `
                                <div id="place-${place.boardNo}" class="destination">
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
                        }
                    });
                    isLoading = false;
                })
                .catch(error => {
                    console.error('Error:', error);
                    isLoading = false;
                });
        } else {
            console.error("검색 폼을 찾을 수 없습니다.");
        }
    }

    function updateURLWithSearchConditions(formData) {
        const searchParams = new URLSearchParams();
        formData.forEach((value, key) => searchParams.append(key, value));

        const newURL = `${window.location.pathname}?${searchParams.toString()}`;
        history.replaceState(null, '', newURL);
    }
});
