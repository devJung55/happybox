const $swiperDataContainer = $(".swiperDataContainer");
const MARGIN = 20;

$swiperDataContainer.each((i, e) => {
    let width = $(e).find(".swiper-slide")[0].offsetWidth;
});

const $prevBtn = $(".swiper-button-prev");
const $nextBtn = $(".swiper-button-next");

$nextBtn.each((i, e) => {
    $(e).on("click", function () {
        if ($(this).hasClass("swiper-button-disabled")) return;

        let $dataContainer = $($(this).prev().prev());
        let $listContainer = $($(this).prev().prev().parent());
        let offsetWidth = $listContainer[0].offsetWidth;
        let indexLimit = $dataContainer.data("limit");
        let offset;

        let i = $dataContainer.data("index");
        offset = offsetWidth * ++i;

        $dataContainer.css({'transform': `translateX(-${offset - MARGIN * i}px)`});
        $dataContainer.data("index", i);

        if (i + 2 > indexLimit) $(this).addClass("swiper-button-disabled");

        $(this).prev().removeClass("swiper-button-disabled");
    });
});

$prevBtn.each((i, e) => {
    $(e).on("click", function () {
        if ($(this).hasClass("swiper-button-disabled")) return;

        let $dataContainer = $($(this).prev());
        let $listContainer = $($(this).prev().parent());
        let offsetWidth = $listContainer[0].offsetWidth;
        let offset;

        let i = $dataContainer.data("index");
        offset = offsetWidth * --i;

        $dataContainer.css({'transform': `translateX(-${offset - MARGIN * i}px)`});
        $dataContainer.data("index", i);

        if (i - 1 < 0) $(this).addClass("swiper-button-disabled");

        $(this).next().removeClass("swiper-button-disabled");
    });
});