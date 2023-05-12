/*-- a 태그 href --*/
const $toDetail = $(".toDetail");

$toDetail.on("click", function() {
    let distributorId = $($(this).children()[1]).text();
    location.href= "/admin/distributor-detail/" + distributorId;
});