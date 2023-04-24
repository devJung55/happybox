
/* 상세보기 모달 ===================== */
const $showDetail = $(".show-detail");

$showDetail.on('click', function(e){
    $(".modal").show();
})

/* 상세 모달 닫기 */
const $modalCancel = $("#Capa_1");

$modalCancel.on("click", function(e) {
    $(".modal").hide();
});


/* 상세보기 <- captain에 있던 것 */
// globalThis.memberId = "";

// $("table.table").on("click", ".content__detail__btn",  function (e) {
//     globalThis.memberId = $($(this).parent().parent().children()[1]).text();
//     adminMemberService.memberDetail(memberId);
//     $(".user-modal").show();
// });


/* 삭제 모달 ======================== */
const $showDelete = $(".delete-button");
const $cancelDelete = $(".cancel-delete");

$showDelete.on('click', function(e){
    $(".delete-modal").show();
})

$cancelDelete.on('click', function(e){
    $(".delete-modal").hide();
})
