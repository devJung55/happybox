/* inquiry.html */

/* 문의 내역 클릭 이벤트 */

const $arrows = $(".arrow-0deg");
const $inquiryDetail = $(".ui-accordion-view");

$arrows.each((i, arrow) => {
    $(arrow).on("click", function() {
        if($(arrow).hasClass("arrow-0deg")) {
            $(arrow).closest("a").next().css("display", "block");
            $(arrow).removeClass("arrow-0deg");
            $(arrow).addClass("arrow-180deg");
        } else {
            $(arrow).closest("a").next().css("display", "none");
            $(arrow).removeClass("arrow-180deg");
            $(arrow).addClass("arrow-0deg");
        }
    })
});

/* 이미지 모달 이벤트 */

const $imgModal = $(".layer-wrap");
const $imgFiles = $(".added-file");
const $closeButton = $(".btn-x-md2");
const $inquiryTitle = $(".inquiry__title");

$imgFiles.on("click", function() {
    if($imgModal.css("display") == "none") {
        $imgModal.css("display", "block");
    }
});

$closeButton.on("click", function() {
    if($imgModal.css("display") == "block") {
        $imgModal.css("display", "none");
    }
});