/* 복지관 상세페이지 */

/* ================================하트 클릭 이벤트 =====================================================*/
$('.like-btn').on('click', function () {
    var $image = $(this).find('img');
    var src = $image.attr('src');
    var activeSrc = '../../static/img/mypage/heart-pull.png';
    var defaultSrc = '../../static/img/mypage/heart.png';

    if (src === defaultSrc) {
        $image.attr('src', activeSrc);
    } else {
        $image.attr('src', defaultSrc);
    }
});

/* Radio 버튼활성화 이벤트 */
/* const $radioLabels = $('.custom-radio label');

$radioLabels.on('click', function() {
  const $clickedRadio = $(this).prev('input[type="radio"]');
  $radioLabels.siblings('input[type="radio"]').prop('checked', false);
  $clickedRadio.prop('checked', true);
});

$radioLabels.filter(':has(input[type="radio"]:checked)').trigger('click'); */

/* ======================Radio 버튼 활성화 및, 선택된 가격으로 총 상품 가격으로 선택 ========================*/
const $radioLabels = $('.custom-radio label');
const $orderNormalTotalPrice = $('.orderNormalTotalPrice');

$radioLabels.on('click', function () {
    const $clickedRadio = $(this).prev('input[type="radio"]');
    const $totalPrice = $('.total-price');
    const $specialTotalPrice = $('.special-total-price');
    let selectedPrice = '';

    $radioLabels.siblings('input[type="radio"]').prop('checked', false);
    $clickedRadio.prop('checked', true);

    if ($clickedRadio.parent().text().trim() === '일반회원가') {
        $totalPrice.show();
        selectedPrice = $totalPrice.text();
    } else if ($clickedRadio.parent().text().trim() === 'Happy') {
        $specialTotalPrice.show();
        selectedPrice = $specialTotalPrice.text();
    }

    $orderNormalTotalPrice.html(selectedPrice);
});

$radioLabels.filter(':has(input[type="radio"]:checked)').trigger('click');

/* ========================카카오맵 js ======================================================================*/
// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

/* db에 있는 group location이 들어갈 곳 */
geocoder.addressSearch("서울시 노원구 석계로 49 현대아파트 105동", function (result, status) {
    // 정상적으로 검색이 완료됐으면
    if (status === kakao.maps.services.Status.OK) {
        /* 좌표 알아내는 코드 */
        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        // 지도 표시용 js
        var mapContainer = document.getElementById('map'), // 지도를 표시할 div
            mapOption = {
                center: coords, // 지도의 중심좌표
                level: 3, // 지도의 확대 레벨
            };

        // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
        var map = new kakao.maps.Map(mapContainer, mapOption);

        // 마커가 표시될 위치입니다
        var markerPosition = coords;

        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
            position: markerPosition,
        });

        // 마커가 지도 위에 표시되도록 설정합니다
        marker.setMap(map);

        function searchAddrFromCoords(coords, callback) {
            // 좌표로 행정동 주소 정보를 요청합니다
            geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
        }

        // 현재 지도 설정좌표로 주소를 검색해서 지정한 위치에 표시합니다
        searchAddrFromCoords(markerPosition, displayAdderessInfo);

        // 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
        function displayAdderessInfo(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                var infoDiv = document.getElementById('coordAddr');

                for (var i = 0; i < result.length; i++) {
                    // 행정동의 region_type 값은 'H' 이므로
                    if (result[i].region_type === 'H') {
                        infoDiv.innerHTML = result[i].address_name;
                        break;
                    }
                }
            }
        }
    }
});

/* ============================================= 스크롤 이벤트 =================================== */
const $fixedDiv = $('.welfare-info-area');
const $fixedPosition = 300; // 고정시킬 위치 값
const initialPosition = $fixedDiv.offset();
const initialTop = initialPosition.top;
const initialRight = $(window).width()*1.24 - ($fixedDiv.offset().left + $fixedDiv.outerWidth());

$(window).scroll(function() {
  const currentPosition = $(window).scrollTop();
  // 스크롤 위치가 고정시킬 위치보다 크거나 같을 때
  if (currentPosition >= $fixedPosition) {
    $fixedDiv.addClass('fixed');
    $fixedDiv.css({
      'top': 30,
      'right': initialRight
    });
  } else {
    $fixedDiv.removeClass('fixed');
    $fixedDiv.removeAttr('style');
  }

  if (Math.floor(currentPosition) == Math.floor(($(document).height()*0.8 - $(window).height()))) {
    console.log("아래닸냐");
    $fixedDiv.css({'top':-100, 'right':initialRight});
  }
});


/* =================================================================================================== */

/* ============================================== 리스트 스와이프========================================= */
let globalIndex = 0;
let nextCheck;

let itemListIndex = 0;
let timerListIndex = 0;
let recommendIndex = 0;
// 임시
let recommendIndex2 = 0;

const $swiperBtnNext = $(".swiper-button-next");
const $swiperBtnPrev = $(".swiper-button-prev");

// 다음 슬라이드 불러옴
$swiperBtnNext.on("click", function (e) {
    // 버튼이 disabled 면 그냥 리턴
    if ($(this).hasClass("swiper-button-disabled")) return;

    let displayCount = $(e.target).hasClass("swiper-button-displayCount2") ? 2 : 4;

    swipe(e, displayCount, this, addDisabledClass);
});

$swiperBtnPrev.on("click", function (e) {
    // 버튼이 disabled 면 그냥 리턴
    if ($(this).hasClass("swiper-button-disabled")) return;

    let displayCount = $(e.target).hasClass("swiper-button-displayCount2") ? 2 : 4;

    swipe(e, displayCount, this, addDisabledClass);
});

// itemSwiper 실행 함수
function itemSwiper($itemList, $button, transformWidth) {
    $button.removeClass("swiper-button-disabled");
    $itemList.css({
        transform: `translateX(${transformWidth}px)`,
    });
}

// 실제 slider 움직이는 함수
function swipe(e, displayCount, button, callback) {
    let $itemList = $(e.target).siblings("ul.swiperDataContainer");

    let $li = $itemList.find("li");
    // 각각 다른 인덱스 변수를 분기처리하여 증가시켜줌
    let index = setitemListIndex(e);

    let marginRight = Number($li[0].style.marginRight.replace("px", ""));
    let itemCount = $li.length;
    let itemWidth = $li[0].clientWidth + marginRight;

    // console.log(itemCount, itemWidth);

    let swipeLimit = itemCount / displayCount;
    let siblingBtn = $(button).siblings('.swiper-btn');

    callback(swipeLimit, button);

    // 움직이는 width 길이
    let transformWidth = itemWidth * displayCount * index * -1;
    itemSwiper($itemList, siblingBtn, transformWidth);
}

function setitemListIndex(e) {
    nextCheck = $(e.target).hasClass("swiper-button-next");
    let i;

    if (nextCheck) {
        i = $(".swiper-button-next").index(e.target);
    } else i = $(".swiper-button-prev").index(e.target);

    switch (i) {
        case 0:
            globalIndex = nextCheck ? ++itemListIndex : --itemListIndex;
            break;
        case 1:
            globalIndex = nextCheck ? ++timerListIndex : --timerListIndex;
            break;
        case 2:
            globalIndex = nextCheck ? ++recommendIndex : --recommendIndex;
            break;
        case 3:
            globalIndex = nextCheck ? ++recommendIndex2 : --recommendIndex2;
            break;
        default:
            break;
    }

    return globalIndex;
}

function addDisabledClass(swipeLimit, button) {
    if (nextCheck && globalIndex + 1 > swipeLimit) {
        console.log(globalIndex + 1, swipeLimit, button);
        $(button).addClass("swiper-button-disabled");
        return;
    }

    if (globalIndex - 1 < 0) {
        $(button).addClass("swiper-button-disabled");
        return;
    }
}

/* =================================================================================================== */

/* ====================================상단으로 이동하기=================================== */
$(document).ready(function() {
    // "fixed-img" 버튼 숨기기
    $('.fixed-img').hide();
  
    // 스크롤 이벤트 처리
    $(window).scroll(function() {
      // 스크롤 위치가 200 픽셀보다 크면 "fixed-img" 버튼 보이기
      if ($(this).scrollTop() > 200) {
        $('.fixed-img').fadeIn();
      } else {
        // 그렇지 않으면 숨기기
        $('.fixed-img').fadeOut();
      }
    });
  
    // "fixed-img" 버튼에 대한 클릭 이벤트 처리
    $('.fixed-img').click(function() {
      // 문서 맨 위로 스크롤
      $('html, body').animate({scrollTop: 0}, 'slow');
    });
  });
/* =================================================================================================== */
