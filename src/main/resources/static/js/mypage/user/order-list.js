/* order-list.html */

/* 기간 클릭 이벤트 */
const $radios = $(".custom-radio");

$radios.on("click", function() {
    $radios.each((i, radio) => {
        if($(radio).hasClass("default")) {
            $(radio).removeClass("default");
        }
    });
    
    if(!$(this).hasClass("default")) {
        $(this).addClass("default");
    }
});

/* 초기화 클릭 이벤트 */
const $reset = $(".reset");

$reset.on("click", function() {
    $radios.each((i, radio) => {
        if($(radio).hasClass("default")) {
            $(radio).removeClass("default");
        }
    });
    $(".select__all").addClass("default");
});