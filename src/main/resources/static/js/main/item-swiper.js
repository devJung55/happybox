let globalIndex = 0;
let nextCheck;

let itemListIndex = 0;
let timerListIndex = 0;
let recommendIndex = 0;
// 임시
let recommendIndex2 = 0;

const $swiperBtnNext = $(".swiper-button-next");
const $swiperBtnPrev = $(".swiper-button-prev");

// 다음 슬라이드 불러옴
$swiperBtnNext.on("click", function (e) {
    // 버튼이 disabled 면 그냥 리턴
    if ($(this).hasClass("swiper-button-disabled")) return;

    let displayCount = $(e.target).hasClass("swiper-button-displayCount2") ? 2 : 4;

    swipe(e, displayCount, this, addDisabledClass);
});

$swiperBtnPrev.on("click", function (e) {
    // 버튼이 disabled 면 그냥 리턴
    if ($(this).hasClass("swiper-button-disabled")) return;

    let displayCount = $(e.target).hasClass("swiper-button-displayCount2") ? 2 : 4;

    swipe(e, displayCount, this, addDisabledClass);
});

// itemSwiper 실행 함수
function itemSwiper($itemList, $button, transformWidth) {
    $button.removeClass("swiper-button-disabled");
    $itemList.css({
        transform: `translateX(${transformWidth}px)`,
    });
}

// 실제 slider 움직이는 함수
function swipe(e, displayCount, button, callback) {
    let $itemList = $(e.target).siblings("ul.swiperDataContainer");

    let $li = $itemList.find("li");
    // 각각 다른 인덱스 변수를 분기처리하여 증가시켜줌
    let index = setitemListIndex(e);

    let marginRight = Number($li[0].style.marginRight.replace("px", ""));
    let itemCount = $li.length;
    let itemWidth = $li[0].clientWidth + marginRight;

    // console.log(itemCount, itemWidth);

    let swipeLimit = itemCount / displayCount - 1;
    let siblingBtn = $(button).siblings('.swiper-btn');

    callback(swipeLimit, button);

    // 움직이는 width 길이
    let transformWidth = itemWidth * displayCount * index * -1;
    itemSwiper($itemList, siblingBtn, transformWidth);
}

function setitemListIndex(e) {
    nextCheck = $(e.target).hasClass("swiper-button-next");
    let i;

    if (nextCheck) {
        i = $(".swiper-button-next").index(e.target);
    } else i = $(".swiper-button-prev").index(e.target);

    switch (i) {
        case 0:
            globalIndex = nextCheck ? ++itemListIndex : --itemListIndex;
            break;
        case 1:
            globalIndex = nextCheck ? ++timerListIndex : --timerListIndex;
            break;
        case 2:
            globalIndex = nextCheck ? ++recommendIndex : --recommendIndex;
            break;
        case 3:
            globalIndex = nextCheck ? ++recommendIndex2 : --recommendIndex2;
            break;
        default:
            break;
    }

    return globalIndex;
}

function addDisabledClass(swipeLimit, button) {
    if (nextCheck && globalIndex + 1 > swipeLimit) {
        console.log(globalIndex + 1, swipeLimit, button);
        $(button).addClass("swiper-button-disabled");
        return;
    }

    if (globalIndex - 1 < 0) {
        $(button).addClass("swiper-button-disabled");
        return;
    }
}
