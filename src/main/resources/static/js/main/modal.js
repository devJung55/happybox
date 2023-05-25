$("#check-modal").css("display", "block");

// 모달창 외부를 클릭했을 때
$(window).on("click", function (event) {
    if ($(event.target).is('.modal')) {
        $("#check-modal").css("display", "none");
    }
});

/* 게시글 삭제 */
function deleteModal(){
    $("#check-modal").css("display", "none");
}