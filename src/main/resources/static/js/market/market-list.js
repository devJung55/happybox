
const $navCtrl = $(".nav-control");
const $navBtn_region = $(".filter-nav-list").find("a.region");
const $navBtn_price = $(".filter-nav-list").find("a.price");

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
$navBtn_region.on("click", function () {
    let btn = $(this).find("button");
    let check = btn.data("checked") === true;

    searchProduct.page = null;

    $navBtn_region.not($(this)).each((i, e) => {
        $(e).find("button").data("checked", false);
        $(e).find("button").find(".check_path").attr("fill", "#fff=");
    });

    if(!check) {
        // 버튼의 색깔을 바꾸기
        btn.find(".check_path").attr("fill", "#467adc");

        // 검색 실행
        searchProduct.address = $(this).data("region");
    } else {
        btn.find(".check_path").attr("fill", "#fff");
        searchProduct.address = null;
    }
    // data 속성 바꾸기
    btn.data("checked", !check);
    doSearch();
});

$navBtn_price.on("click", function () {
    let btn = $(this).find("button");
    let check = btn.data("checked") === true;

    $navBtn_price.not($(this)).each((i, e) => {
        $(e).find("button").data("checked", false);
        $(e).find("button").find(".check_path").attr("fill", "#fff=");
    });

    searchProduct.page = null;

    if(!check) {
        // 버튼의 색깔을 바꾸기
        btn.find(".check_path").attr("fill", "#467adc");

        // 검색 실행
        searchProduct.price = $(this).data("price");
    } else {
        btn.find(".check_path").attr("fill", "#fff");
        searchProduct.price = null;
    }
    // data 속성 바꾸기
    btn.data("checked", !check);
    doSearch();
});

/* 카테고리 초기화 */
$reset.on("click", function () {
    let btns = $(".filter-nav-list a button");
    btns.each((i, e) => $(e).data("checked", false).find(".check_path").attr("fill", "#fff"));
    $itemOrder.each((i, e) => $(e).removeClass("order-selected"));
    $itemOrder.eq(0).addClass("order-selected");

    searchProduct.address = null;
    searchProduct.price = null;
    searchProduct.name = null;
    searchProduct.productCategory = null;
    searchProduct.productSearchOrder = null;
    searchProduct.page = null;

    doSearch();
});

/* 정렬순서 */
$itemOrder.on("click", function () {
    $(this).addClass("order-selected");
    $itemOrder.not($(this)).each((i, e) => $(e).removeClass("order-selected"));

    searchProduct.productSearchOrder = $(this).data("order");

    doSearch();
});
