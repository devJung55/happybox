const $selectList = $(".select-list");
const $selectVal = $(".select-value");
const $value = $("#value");
const $srchOption = $(".srchOpt");
const $srchType = $("input[name='srchType']");
const $keyword = $("input[name='keyword']");
const $srchButton = $("#noticeSrch");

$srchType.val($srchOption.eq(0).data('value'));

$selectVal.on("click", () => disNone());

$srchOption.on('click', function() {
    let val = $(this).data('value');
    $value.text(val);
    disNone();
    $srchType.val(val);
})

function disNone() {
    let check = $selectList.hasClass("disNone");

    if (check) {
        $selectList.removeClass("disNone");
    } else $selectList.addClass("disNone");
}

$srchButton.on('click', function (e) {
    e.preventDefault();
    goSearch();
})

// 검색 form 활성화
function goSearch() {
    const frm = $('#frm');
    const srchType = $srchType.val();
    const keyword = $keyword.val();

    $('#srchType').val(srchType);
    $('#item1-1').val(keyword);

    frm.attr("action", `/cs/notice-list`);
    frm.submit();
}

// keyword 작성후 엔터 누르면 기능 수행
$keyword.keypress(function (e) {
    if (e.which == 13) {
        e.preventDefault();
        goSearch();
    }
})
