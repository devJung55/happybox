/* ===============기부 리스트 =============================*/

/* 랭킹 번호 작성해주는 이벤트 */
$('.left li').each(function (index) {
    var rankNum = $(this).find('.rank-num');
    rankNum.text(index + 1);

    if (index < 5) {
        rankNum.css('border-bottom', '1px solid #666');
    } else {
        rankNum.css('border-bottom', 'none');
    }
});

$('.right li').each(function (index) {
    var rankNum = $(this).find('.rank-num');
    rankNum.text(index + 1);

    if (index < 5) {
        rankNum.css('border-bottom', '1px solid #666');
    } else {
        rankNum.css('border-bottom', 'none');
    }
});

/*  사진 마우스오버시 스케일 커지는 이벤트 */
$(function () {
    $('.column.img img')
        .on('mouseover', function () {
            var $img = $(this);
            setTimeout(function () {
                $img.css('transform', 'scale(1.07)');
                $img.css('transition', 'all 0.3s');
            }, 250); // set delay
        })
        .on('mouseout', function () {
            $(this).css('transform', 'scale(1)');
            $(this).css('transition', 'all 0.3s');
        });
});


