/* address-editor-form.html */

$(".btn_update").on("click", function() {
    console.log($("#sample6_postcode").text());
});

const $updateComplete = $(".btn_update");

$updateComplete.on("click", function() {
    $(document.addressForm).attr("href", "/mypage/member/address-editor");
    $(document.addressForm).submit();
});