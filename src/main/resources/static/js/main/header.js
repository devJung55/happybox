/* Header.js */


/* ============================== 선언부  ============================ */

const $changeMain = $('.main-swiper-wrap a');


/* ===================== 복지마켓 효과 ======================*/
$(document).ready(function () {
    $changeMain.click(function () {
        $changeMain.removeClass('fontW700');
        $(this).addClass('fontW700');
    });
});


/* ===================== 마이 메뉴 효과 ======================*/

$(document).ready(function () {
    $('.my-menu li').on('mouseover', function () {
        $(this).css('transform', 'scale(1.05)');
    });
    $('.my-menu li').on('mouseout', function () {
        $(this).css('transform', 'scale(1.0)');
    });
});


/* ===================== 네비 효과 ======================*/

$(document).ready(function () {
    $('.gnb li').on('mouseover', function () {
        $(this).css('transform', 'scale(1.05)');
        $(this).find('span').css('font-weight', 'bold');
    });
    $('.gnb li').on('mouseout', function () {
        $(this).css('transform', 'scale(1)');
        $(this).find('span').css('font-weight', '');
    });
});

/* ======================================= ======================*/
