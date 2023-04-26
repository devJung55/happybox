let curPos = 0;
let postion = 0;
let startX, endX;
let prevX;
const IMAGE_WIDTH = $(".swiper-slide").eq(0).outerWidth(true);
const $swiperWrapper = $(".swiper-wrapper");
const maxCurPos = $swiperWrapper.find(".swiper-slide").length - 1;

$swiperWrapper.on("touchstart", (e) => touchStart(e));
$swiperWrapper.on("touchmove", (e) => touchMove(e));
$swiperWrapper.on("touchend", (e) => touchEnd(e));

function touchStart(event) {
    startX = event.touches[0].pageX;
    prevX = startX;
}

function touchMove(event) {
    let offsetX = event.changedTouches[0].pageX;
    let check = prevX - offsetX;
    let positionChange = 0;
    
    if(check == 0) return;

    if(Math.abs(check) > IMAGE_WIDTH / 2) {
        check = check / 3;
    }
    
    if(check < 0) {
        positionChange = postion - check;
    } else positionChange = postion - check;

    $swiperWrapper.css("transform", `translateX(${positionChange}px)`);
}

function touchEnd(event) {
    endX = event.changedTouches[0].pageX;
    let absolute = Math.abs(startX - endX);
    // console.log(endX);
    console.log(absolute);
    if (startX > endX) {
        nextSlide();
    } else {
        prevSlide();
    }
}

function prevSlide() {
    if (curPos == 0) {
        $swiperWrapper.css("transform", `translateX(${postion}px)`);
        return;
    }

    if (curPos > 0) {
        postion += IMAGE_WIDTH;
        $swiperWrapper.css("transform", `translateX(${postion}px)`);
        curPos = curPos - 1;
    }
}
function nextSlide() {
    if (curPos + 1 == maxCurPos) {
        $swiperWrapper.css("transform", `translateX(${postion}px)`);
        return;
    }

    if (curPos < maxCurPos) {
        postion -= IMAGE_WIDTH;
        $swiperWrapper.css("transform", `translateX(${postion}px)`);
        curPos = curPos + 1;
    }
}
