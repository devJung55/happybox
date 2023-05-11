/* 공지사항 페이징 처리 */

const urlParams = new URLSearchParams(window.location.search);
const selectedType = urlParams.get('srchType');
const selectedKeyword = urlParams.get('keyword');

function pageMove(page) {
    if(selectedType == null) {
        location.href = `/cs/notice-list?page=${page}`;
    } else {
        location.href = `/cs/notice-list?page=${page}&srchType=${selectedType}&keyword=${selectedKeyword}`;
    }
}