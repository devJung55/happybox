/* 복지관 상세페이지 */

/* =================================== 구독 옵션 ============================================ */

const SUB_OPTIONS = {
    NORMAL: "NORMAL",
    LOW_SALT: "LOW_SALT",
    MORE_AMT: "MORE_AMT",
    LESS_AMT: "LESS_AMT",
}


/* ======================Radio 버튼 활성화 및, 선택된 가격으로 총 상품 가격으로 선택 ========================*/
const $radioLabels = $('.custom-radio label');
const $orderNormalTotalPrice = $('.orderNormalTotalPrice');

$radioLabels.on('click', function () {
    const $clickedRadio = $(this).prev('input[type="radio"]');
    let selectedPrice = subscription.subscriptionPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");

    $radioLabels.siblings('input[type="radio"]').prop('checked', false);
    $clickedRadio.prop('checked', true);

    $orderNormalTotalPrice.html(selectedPrice);
});

$radioLabels.filter(':has(input[type="radio"]:checked)').trigger('click');

/* ========================카카오맵 js ======================================================================*/
const WELFARE_ADDRESS = subscription.address;

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

/* db에 있는 location이 들어갈 곳 */
geocoder.addressSearch(`${WELFARE_ADDRESS.firstAddress}`, function (result, status) {
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

/* ============================================= 음식 리스트 뿌리기 =================================== */

function showFoodList(foodCalendars) {
    let $container = $(".swiperDataContainer");
    let count = 0;
    let limit;

    // container 안의 음식 비우기
    $container.empty();

    foodCalendars.forEach(calendar => {
        // 음식 리스트 길이가 0 이면 return
        if (calendar.foodList.length == 0) return;

        // list append
        calendar.foodList.forEach(food => appendFood(food, $container));
        // 카운트 증가
        ++count;
    });

    limit = Math.floor(count / 3);
    $container.data("limit", limit);
}

function appendFood(food, $target) {
    let text;
    let filePath = "";

    /* 추후 주석풀기 */
    if(food.filePath == null || food.fileOrgName == null || food.fileUuid == null) filePath = "/img/welfare/welfare_img_default.png";
    else filePath = `/image/display?fileName=${food.filePath}/t_${food.fileUuid}_${food.fileOrgName}`;

    text = `
        <li
            class="bnr-item-slide swiper-slide swiper-slide-active"
            style="width: 227px; margin-right: 20px">
            <div class="bnr-item">
                <div class="img">
                    <img
                            alt=""
                            src="${filePath}"
                            data-loaded="true"
                    />
                </div>
                <em class="tit text-elps"
                >${food.foodName}</em
                >
            </div>
        </li>
    `

    $target.append(text);
}

/* ============================================== 리스트 스와이프========================================= */
const MARGIN = 20;

const $prevBtn = $(".swiper-button-prev");
const $nextBtn = $(".swiper-button-next");

$nextBtn.each((i, e) => {
    $(e).on("click", function () {
        if ($(this).hasClass("swiper-button-disabled")) return;

        let $dataContainer = $($(this).prev().prev());
        let offsetWidth = 742;
        let indexLimit = $dataContainer.data("limit");
        let offset;

        let i = $dataContainer.data("index");
        offset = offsetWidth * ++i;

        $dataContainer.css({'transform': `translateX(-${offset}px)`});
        $dataContainer.data("index", i);

        if (i + 2 > indexLimit) $(this).addClass("swiper-button-disabled");

        $(this).prev().removeClass("swiper-button-disabled");
    });
});

$prevBtn.each((i, e) => {
    $(e).on("click", function () {
        if ($(this).hasClass("swiper-button-disabled")) return;

        let $dataContainer = $($(this).prev());
        let offsetWidth = 742;
        let offset;

        let i = $dataContainer.data("index");
        offset = offsetWidth * --i;

        $dataContainer.css({'transform': `translateX(-${offset}px)`});
        $dataContainer.data("index", i);

        if (i - 1 < 0) $(this).addClass("swiper-button-disabled");

        $(this).next().removeClass("swiper-button-disabled");
    });
});

/* =================================================================================================== */
/* ================ 장바구니 ================ */

const SUB_OPTIONS_CLIENT = {
    NORMAL: "옵션 선택안함",
    LOW_SALT: "저염식",
    MORE_AMT: "양 많이",
    LESS_AMT: "양 적게"
};

const CART_URL = `/welfare/cart/add/${subscription.id}`;
$(".productCart-btn").on("click", function () {
    /* 장바구니 */
    $(".cart-name").text(subscription.subscriptionTitle)
    $(".cart-welfare").text(subscription.welfareName);
    $(".cart-price").text(subscription.subscriptionPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + " 원");
    $(".cart-option").text(SUB_OPTIONS_CLIENT[$("select[name='option']").val()]);

    $("#cart-modal").css("display", "block");
});

// 닫기 버튼을 클릭했을 때
$(".close").on("click", function () {
    $("#cart-modal").css("display", "none");
});

// 예 버튼을 클릭했을 때
$("#modal-yesBtn").on("click", function () {
    console.log(CART_URL);
    $doAjaxPost("POST",
        CART_URL, // 장바구니 URL
        {
            subscriptionTitle: subscription.subscriptionTitle, // 구독상품 이름 (굳이 ?)
            subOption: $("select[name='option']").val()
        },
        (result) => {  // callback
            $("#cart-modal").css("display", "none");
        }
    );

});

// 모달창 외부를 클릭했을 때
$(window).on("click", function (event) {
    if ($(event.target).is('.modal')) {
        $("#cart-modal").css("display", "none");
    }
});

/* 찜하기 */
const SUB_LIKE_URL = `/welfare/detail/like/${subscription.id}`;
const likeSrc = "/img/mypage/heart-pull.png";
const unlikeSrc = "/img/mypage/heart.png";
/* 이미 좋아요인지 검사 */

$(".like-btn img").attr("src", `${isLike ? likeSrc : unlikeSrc}`);

/* 좋아요 눌렀을 때 */
function checkLike() {
    $doAjax("POST", SUB_LIKE_URL, {}, (result) => {
        if (result === -1) {
            $("#like-modal").css("display", "block");
            return;
        }

        $(".like-btn img").attr("src", `${result === 1 ? unlikeSrc : likeSrc}`);
    });
}

/* ====================================상단으로 이동하기=================================== */
$(document).ready(function () {
    // "fixed-img" 버튼 숨기기
    $('.fixed-img').hide();

    // 스크롤 이벤트 처리
    $(window).scroll(function () {
        // 스크롤 위치가 200 픽셀보다 크면 "fixed-img" 버튼 보이기
        if ($(this).scrollTop() > 200) {
            $('.fixed-img').fadeIn();
        } else {
            // 그렇지 않으면 숨기기
            $('.fixed-img').fadeOut();
        }
    });

    // "fixed-img" 버튼에 대한 클릭 이벤트 처리
    $('.fixed-img').click(function () {
        // 문서 맨 위로 스크롤
        $('html, body').animate({scrollTop: 0}, 'slow');
    });
});
/* =================================================================================================== */
/* ==================================== 슬라이드 이미지 뿌리기   =================================================== */

const $slide = $('.slide-img-wrap');

function showSlideImg() {

    let text =
        `
                    <li class="bnr-item-slide swiper-slide swiper-slide-active" style="width: 227px; margin-right: 20px">
                        <div class="bnr-item">
                            <div class="img">
                                <img src="" />
                            </div>
                            <em class="tit text-elps"> </em>
                            <span class="desc"> </span>
                                <a href="" class="btn-blank">
                                    <span class="blind"> </span>
                                </a>
                        </div>
                    </li>
                `
    $slide.append();
}

/* ==============================================  수정삭제를 위한 모달 ================================= */
// const $showDelete = $(".delete-btn");
// const $cancelDelete = $(".cancel-delete");
// $showDelete.on('click', function (e) {
//     $(".delete-modal").show();
//     $('.confirm-delete').on('click', function () {
//         $(location).attr('href', '../../templates/welfare/dontae-list.html');
//     });
// })
// $cancelDelete.on('click', function (e) {
//     $(".delete-modal").hide();
// })

/* ==============================================  구독하기 버튼 눌렀을 때 장바구니로 이동 ================================= */

const $sub = $('.subscribe-btn');
const $confirm = $(".confirm-delete");
const CHECK_SUB_URL = "/welfare/sub/check";
$sub.on('click', function () {
    console.log(subscription.id);
    $doAjax("GET",
        CHECK_SUB_URL,
        {subscriptionId: subscription.id},
        (result) => {
            console.log(result);
            if(result !== 0) {
                $('.delete-modal').show();
            } else {
                $doAjaxPost("POST",
                    CART_URL,
                    {
                        subscriptionTitle: subscription.subscriptionTitle,
                        subOption: $("select[name='option']").val()
                    },
                    (result) => {
                        location.href = "/order/subscription";
                    }
                );
            }
        }
    );
});


$confirm.on('click', function () {
    $('.delete-modal').hide();
})

console.log($(".quantity-input").val());
console.log(CART_URL);


/*=======================================================================================================*/
console.log("==================================================");
console.log(subscription.subscriptionContent);
$('.welfare-content').text(subscription.subscriptionContent);

let src = "/img/welfare/welfare_detail_default.png";

if(file.filePath != null && file.fileUuid != null && file.fileOrgName != null){
    src = `/image/display?fileName=${file.filePath}/${file.fileUuid}_${file.fileOrgName}`;
}

$('#profile-img').attr('src', src);