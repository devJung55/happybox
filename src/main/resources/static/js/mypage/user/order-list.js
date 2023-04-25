/* order-list.html */

/* 기간 클릭 이벤트 */
const $radios = $(".custom-radio");
let text = "";

$radios.on("click", function() {
    $radios.each((i, radio) => {
        if($(radio).hasClass("default")) {
            $(radio).removeClass("default");
            text = $($(this).children()[1]).text();
            console.log(text);
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