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