/* sales-list.html */

/* 주문 회원정보 상세보기 모달창 */

const $modal = $(".layer-wrap");
const $userDetailButton = $(".user__name");
const $closeButton = $(".btn-x-md2");

$userDetailButton.on("click", function() {
    if($modal.css("display") == "none") {
        $modal.css("display", "block");
    }
});

$closeButton.on("click", function() {
    if($modal.css("display") == "block") {
        $modal.css("display", "none");
    }
});