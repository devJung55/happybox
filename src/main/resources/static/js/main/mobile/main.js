const $swiperWrapper = $(".swiper-wrapper");

// let postion = $swiperWrapper.data("position");
let startX, endX;
let prevX;

$swiperWrapper.on("touchstart", (e) => touchStart(e));
$swiperWrapper.on("touchmove", function (e) {
    touchMove(e, $(this));
});
$swiperWrapper.on("touchend", function (e) {
    touchEnd(e, $(this));
});

function touchStart(event) {
    startX = event.touches[0].pageX;
    prevX = startX;
    console.log("앙");
}

function touchMove(event, $slide) {
    let offsetX = event.changedTouches[0].pageX;
    let check = prevX - offsetX;
    let position = $slide.data("position");
    let positionChange = 0;
    let imageWidth = $slide.find(".swiper-slide").eq(0).outerWidth(true);

    if (check == 0) return;

    if (Math.abs(check) > imageWidth / 2) {
        check = check / 3;
    }

    if (check < 0) {
        positionChange = position - check;
    } else positionChange = position - check;

    $slide.css("transform", `translateX(${positionChange}px)`);
}

function touchEnd(event, $slide) {
    endX = event.changedTouches[0].pageX;
    let absolute = Math.abs(startX - endX);
    let imageWidth = $slide.find(".swiper-slide").eq(0).outerWidth(true);
    let position = $slide.data("position");

    if (absolute < imageWidth / 2) {
        $slide.css("transform", `translateX(${position}px)`);
        return;
    }

    if (startX > endX) {
        nextSlide($slide, position,imageWidth);
    } else {
        prevSlide($slide, position,imageWidth);
    }
}

function prevSlide($slide, position, imageWidth) {
    let curpos = $slide.data("curpos");
    
    if (curpos == 0) {
        $slide.css("transform", `translateX(${position}px)`);
        return;
    }

    if (curpos > 0) {
        position += imageWidth;
        $slide.css("transform", `translateX(${position}px)`);
        $slide.data("curpos", curpos - 1);
        $slide.data("position", position);
    }
}
function nextSlide($slide, position, imageWidth) {
    let curpos = $slide.data("curpos");
    let maxcurpos = $slide.find(".swiper-slide").length - 1;
    let i = $swiperWrapper.index($slide);

    /* 큰 배너는 max 1 추가 */
    if(i == 2) maxcurpos++;

    if (curpos + 1 == maxcurpos) {
        $slide.css("transform", `translateX(${position}px)`);
        return;
    }

    if (curpos < maxcurpos) {
        position -= imageWidth;
        $slide.css("transform", `translateX(${position}px)`);
        $slide.data("curpos", curpos + 1);
        $slide.data("position", position);
    }
}
