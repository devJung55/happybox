/* ajax 실행 */
/* ProductSearch */
// private String address;
// private Integer price;
// private String name;
// private ProductCategory productCategory;
// private ProductSearchOrder productSearchOrder;
$doAjax("get", "/product/search", {
    page: 1,
    size: 10,
});

const $navCtrl = $(".nav-control");
const $navBtn = $(".filter-nav-list").find("a");

// 초기화 버튼
const $reset = $("#reset-filter");

// 정렬순서 버튼
const $itemOrder = $(".item-order a");

/* 카테고리 네비게이션 열고 닫기 */
$navCtrl.on("click", function () {
    let navClass = "nav-show";
    let $nav = $(this).next();
    let check = $nav.hasClass(navClass);

    if (check) $nav.removeClass(navClass);
    else $nav.addClass(navClass);
});

/* 카테고리 항목 선택 */
$navBtn.on("click", function () {
    let btn = $(this).find("button");
    let check = btn.data("checked") === true;

    // 버튼의 색깔을 바꾼 후
    btn.find(".check_path").attr("fill", `${check ? "#fff" : "#467adc"}`);

    // data 속성 바꾸기
    btn.data("checked", !check);
});

/* 카테고리 초기화 */
$reset.on("click", function () {
    let btns = $(".filter-nav-list a button");
    btns.each((i, e) => $(e).data("checked", false).find(".check_path").attr("fill", "#fff"));
});

/* 정렬순서 */
const orderClass = "order-selected";

$itemOrder.on("click", function () {
    $(this).addClass("order-selected");
    $itemOrder.not($(this)).each((i, e) => $(e).removeClass(orderClass));
});

/* ajax 용 */
function $doAjax(type, url, data) {
    $.ajax({
        type: type,
        url: url,
        data: data,
        dataType: "json",
        success: function (response) {
            console.log(response);
        }
    });
}