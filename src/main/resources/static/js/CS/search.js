const $selectList = $(".select-list");
const $selectVal = $(".select-value");
const $value = $("#value");
const $srchOption = $(".srchOpt");
const $srchType = $("input[name='srchType']");

$srchType.val($srchOption.eq(0).data('value'));

$selectVal.on("click", () => disNone());

$srchOption.on('click', function() {
    let val = $(this).data('value');
    $value.text(val);
    disNone();
})

function disNone() {
    let check = $selectList.hasClass("disNone");

    if (check) {
        $selectList.removeClass("disNone");
    } else $selectList.addClass("disNone");
}