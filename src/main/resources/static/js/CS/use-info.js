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

$useInfo.on('click', function() {
    let $answerWrap = $(this).next('.answer-wrap');
    let check = $answerWrap.hasClass('disNone');

    if(check) {
        $answerWrap.removeClass('disNone');
        $arrowWrap.html(arrowUp);
    } else {
        $answerWrap.addClass('disNone') ; 
        $arrowWrap.html(arrowDown);
    }
});