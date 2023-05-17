/* Header.js */


/* ============================== 선언부  ============================ */

/* ===================== 복지마켓 / 유통마켓 효과 ======================*/
$(document).ready(function () {
    // 초기 상태에서 product-market에 글씨 굵게 설정
    if (window.location.href.includes('/market')) {
        $('#product-market').css('font-weight', '700');
    } else {
        $('#welfare-market').css('font-weight', '700');
    }

    // welfare-market을 클릭했을 때 글씨 굵게 설정하고 product-market 글씨 굵기 초기화
    $('#welfare-market').click(function () {
        $(this).css('font-weight', '700');
        $('#product-market').css('font-weight', '');
        $('#product-market').removeClass('selected');
        $(this).addClass('selected');
    });

    // product-market을 클릭했을 때 글씨 굵게 설정하고 welfare-market 글씨 굵기 초기화
    $('#product-market').click(function () {
        $(this).css('font-weight', '700');
        $('#welfare-market').css('font-weight', '');
        $('#welfare-market').removeClass('selected');
        $(this).addClass('selected');
    });

    // 마우스 오버시 글씨 굵게 설정
    $('#welfare-market, #product-market').mouseover(function () {
        if (!$(this).hasClass('selected')) {
            $(this).css('font-weight', '700');
        }
    });

    // 마우스 아웃시 버튼이 선택되어 있지 않은 경우에만 글씨 굵기 초기화
    $('#welfare-market, #product-market').mouseout(function () {
        if (!$(this).hasClass('selected')) {
            $(this).css('font-weight', '');
        }
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

/* ========================= 유틸 효과============== ======================*/

$(document).ready(function () {
    $('.util li').on('mouseover', function () {
        $(this).css('transform', 'scale(1.03)');
        $(this).find('a').css('font-weight', 'bold');
    });
    $('.util li').on('mouseout', function () {
        $(this).css('transform', 'scale(1)');
        $(this).find('a').css('font-weight', '');
    });
});

/* ========================= fixed 용 =============================== */
$('document').ready(function () {
    $(window).scroll(function () {
        let position = $(window).scrollTop(); // 현재 스크롤바의 위치값을 반환합니다.

        if (position >= 120) {
            $('.gnb-wrap').addClass('fixed');
        } else {
            $('.gnb-wrap').removeClass('fixed');
        }
    });
});

/*============================== 장바구니 모달용 =============================== */
function showCartModal() {
    $(".productCart-modal").css("display", "flex");
}

$(window).on("click", function (event) {
    if (!$(event.target).is('.productCart-modal')) {
        $(".productCart-modal").css("display", "none");
    }
});