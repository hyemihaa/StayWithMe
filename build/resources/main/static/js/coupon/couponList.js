


function showTab(tab, event) {
    // 모든 탭 내용 숨기기
    document.getElementById('all').style.display = 'none';
    document.getElementById('hotel').style.display = 'none';
    document.getElementById('pension').style.display = 'none';
    document.getElementById('resort').style.display = 'none';

    // 선택한 탭 내용 보여주기
    document.getElementById(tab).style.display = 'flex';

    // 모든 탭에서 active 클래스 제거
    const tabs = document.querySelectorAll('.event-tabs li');
    tabs.forEach(tab => tab.classList.remove('active'));

    // 클릭한 탭에 active 클래스 추가
    event.target.classList.add('active');
}