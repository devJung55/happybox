/*-- 유통회원 목록 href --*/
const $toDetail = $(".toDetail");

$toDetail.on("click", function() {
    let distributorId = $($(this).parent().children()[1]).text();
    location.href= "/admin/distributor-detail/" + distributorId;
});

/*-- 복지관회원 목록 href --*/
const $toWelfareDetail = $(".toWelfareDetail");

$toWelfareDetail.on("click", function() {
    let welfareId = $($(this).parent().children()[1]).text();
    location.href = "/admin/welfare-detail/" + welfareId;
});