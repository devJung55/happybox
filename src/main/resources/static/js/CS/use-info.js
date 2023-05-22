const $useInfo = $(".use-info");
const $arrowWrap = $(".arrow-wrap");
const arrowUp = `
    <svg height="15px" viewBox="0 0 512 512" width="15px">
        <polygon
            points="396.6,352 416,331.3 256,160 96,331.3 115.3,352 256,201.5 "
        />
    </svg>
`;
const arrowDown = `
    <svg height="15px" viewBox="0 0 512 512" width="15px">
        <polygon
            points="396.6,160 416,180.7 256,352 96,180.7 115.3,160 256,310.5 "
        />
    </svg>
`

// 펼쳐진 답변
let $openAnswer = null;

$useInfo.on('click', function() {
    let $answerWrap = $(this).next('.answer-wrap');
    let check = $answerWrap.hasClass('disNone');

    if (check) {
        // 만약 먼저 펼쳐놓은 답변이 있다면 닫아주기
        if ($openAnswer !== null) {
            $openAnswer.addClass('disNone');
            $openAnswer.prev('.board-wrap').find('.arrow-wrap').html(arrowDown);
        }

        // 현재 답변을 펼쳐놓기
        $answerWrap.removeClass('disNone');
        $arrowWrap.html(arrowUp);

        // 펼쳐진 답변 업데이트 해주기
        $openAnswer = $answerWrap;
    } else {
        // 펼친 답변 닫기
        $answerWrap.addClass('disNone');
        $arrowWrap.html(arrowDown);

        // 펼쳐진 답변 초기화
        $openAnswer = null;
    }
});