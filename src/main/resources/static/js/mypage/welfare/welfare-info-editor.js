/* welfare-info-editor.html */

/* 내용 글자수 카운트 이벤트 */

const $counter = $("#counter");
const $textarea = $("#vQuestionCont");

$textarea.on("keyup", function() {
    let content = $(this).val();

    // 글자수 세기
    if(content.length < 1000) {
        $counter.text(content.length);
    }

    // 글자수 제한
    if (content.length > 1000) {
        // 1000자 부터는 타이핑 되지 않도록
        $(this).val($(this).val().substring(0, 1000));
        // 1000자 넘으면 알림창 뜨도록
        alert('글자수는 1000자까지 입력 가능합니다.');
    };
});