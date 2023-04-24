const $listSelecter = $(".list-normal a");
const $sectionWrap = $(".section-wrap");
const $mainCategoryList = $(".main-category-list");
const mainCategoryTopLoc = $mainCategoryList[0].offsetTop;

const $imgContainer = $(".info-img-container");
const $infoImgThumbnail = $(".info-img-thumbnail img");

const $reviewListWrap = $(".review-list-wrap");

/* 임시 이미지 갯수 */
const imgCount = 7;

/* 임시 이미지 append */
for (let i = 0; i < imgCount; i++) {
    let text = `
        <button class="img-btn" onclick="changeImgThumbnail(this)">
            <img src="https://file.rankingdak.com/image/RANK/PRODUCT/PRD001/20220510/IMG1652xdA145362055_600_600.jpg">
        </button>
    `;
    $imgContainer.append(text);
}

/* 임시 리뷰 요소 append */
for (let i = 0; i < imgCount; i++) {
    let text = `
    <div class="review-list">
        <div class="user-info-wrap">
            <div class="user-info">
                <span class="user-type">일반</span>
                <span class="user-id">kjp1234</span>
            </div>
        </div>
        <div class="review-wrap">
            <div class="css-18pn4xv e36z05c1">
                <h3 class="review-item-name">
                    [4.22원데이] 더라인 순면 피그먼트
                </h3>
            </div>
            <p class="review-content">
                이거 엄청나요 망설이신다면 지금당장 구매해보세요. 너무
                데치면 질겨지니 2분안쪽으로 데쳐서 초고추장 찍어서 먹으면
                그곳이 천국입니다
            </p>
            <div class="review-footer">
                <span class="review-date">2022.11.12</span>
                <button class="review-rec-btn">
                    <span>도움돼요</span>
                    <span class="rec-count">1</span>
                </button>
            </div>
        </div>
    </div>
    `;
    $reviewListWrap.append(text);
}

/* 썸네일 이미지 바꾸기 */
function changeImgThumbnail(img) {
    let $img = $(img).find("img");

    $infoImgThumbnail.attr("src", $img.attr("src"));
}

$listSelecter.on("click", function () {
    let i = $listSelecter.index($(this));
    /* 스크롤이 이동할 위치 */
    $sectionWrap.eq(i)[0].scrollIntoView({ behavior: "smooth" });
});

/* item list menu 고정용 */
$("document").ready(function () {
    $(window).scroll(function () {
        var position = $(window).scrollTop(); // 현재 스크롤바의 위치값을 반환합니다.

        if (position >= mainCategoryTopLoc - 200) {
            $mainCategoryList.addClass("list-menu-pos-fixed");
        } else {
            $mainCategoryList.removeClass("list-menu-pos-fixed");
        }
    });
});
