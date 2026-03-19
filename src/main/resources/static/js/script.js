// 링크 클릭 시 텍스트 저장
document.querySelectorAll('.header-inner a').forEach(a => {
    a.addEventListener('click', function() {
        localStorage.setItem('pageTitle', this.textContent);
    });
});
// 페이지 로드 시 타이틀 표시
document.getElementById('page-title').textContent = localStorage.getItem('pageTitle') || '';
