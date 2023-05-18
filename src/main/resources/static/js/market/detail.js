const $listSelecter = $(".list-normal a");
const $sectionWrap = $(".section-wrap");
const $mainCategoryList = $(".main-category-list");
const mainCategoryTopLoc = $mainCategoryList[0].offsetTop;


const $infoImgThumbnail = $(".info-img-thumbnail img");

const $reviewListWrap = $(".review-list-wrap");

const $moreReview = $(".more-review");

const $imgContainer = $(".info-img-container");

const $supplierInfoList = $(".supplier-info-list");

let productPrice = $product.productPrice;
let productFiles = $product.productFileDTOS;
let productFileRep = $product.productFileDTOS[0];
let productName = $product.productName;
let id = $product.id;

console.log($product);

const category = {
    VEGETABLES: "야채",
    FRUITS: "과일",
    SEAFOOD: "해산물",
    MEAT: "육류",
    DAIRY: "유제품",
    SPICES: "양념",
    OTHER: "기타"
}

// 판매자
$(".supplier-name").text($product.distributorName);
$(".detail-info-category").next().find("p").eq(1).text($product.distributorName);

// 상품이름
$(".item-name").eq(0).text('[' + category[$product.productCategory] + ']');
$(".item-name").eq(1).text($product.productName);

// 상품 가격
$(".item-price-wrap .number").text(productPrice);

// 판매자 정보
$supplierInfoList.eq(0).find("li").eq(1).text($product.distributorName);
$supplierInfoList.eq(1).find("li").eq(1).text($product.address.firstAddress + " " + $product.address.addressDetail);

// 상품 댓글 수
$(".reply-count").text($product.productReplyCount);
$(".review-count span").text($product.productReplyCount);

// 상품 대표사진
let filePath = "";
if (productFileRep) {
    filePath = "/image/display?fileName=" + productFileRep.filePath + '/t_' + productFileRep.fileUuid + '_' + productFileRep.fileOrgName;
} else {
    filePath = "/img/market/no_img_market.png";
}

productFiles.forEach((file) => {
    let text;
    let filePath = file.filePath + '/t_' + file.fileUuid + '_' + file.fileOrgName;

    text = `
            <button class="img-btn" onclick="changeImgThumbnail(this)">
                <img src="/image/display?fileName=${filePath}">
            </button>
        `

    $imgContainer.append(text);
});

// 현재 페이지
let page = 1;
// 정렬 순서
let isOrderByDate = null;
// 마지막 여부
let isLastPage = false;

/* common/ajax.js */
$doAjax("get", `/product/detail/reply/${$product.id}`,
    {},
    (result) => {
        console.log(result);
        result.content.forEach((reply) => appendReplyList(reply));
        if (result.last) $moreReview.css("display", "none");
    }
);

$moreReview.on("click", function () {
    $doAjax("get", `/product/detail/reply/${$product.id}`,
        {page: ++page, isOrderByDate : isOrderByDate},
        (result) => {
            console.log(result);
            result.content.forEach((reply) => appendReplyList(reply));
            if (result.last) $(this).css("display", "none");
        }
    );
});

// 최신순
$(".orderDate").on("click", function () {
    page = 1;
    isOrderByDate = true
    $reviewListWrap.empty();
    $doAjax("get", `/product/detail/reply/${$product.id}`,
        {page: page, isOrderByDate: isOrderByDate},
        (result) => {
            result.content.forEach((reply) => appendReplyList(reply));
            $moreReview.css("display", "block")
        }
    );
});

// 인기순
$(".orderLikeCount").on("click", function () {
    page = 1;
    isOrderByDate = false;
    $reviewListWrap.empty();
    $doAjax("get", `/product/detail/reply/${$product.id}`,
        {page: page, isOrderByDate: isOrderByDate},
        (result) => {
            result.content.forEach((reply) => appendReplyList(reply));
            $moreReview.css("display", "block")
        }
    );
});

const USER_ROLE = {
    MEMBER: "일반",
    WELFARE: "복지관"
}

window.scroll()

/* 댓글 append */
function appendReplyList(reply, isPrepend) {

    let date = reply.updatedDate.split("T")[0];

    let text = `
    <div class="review-list">
        <div class="user-info-wrap">
            <div class="user-info">
                <span class="user-type">${USER_ROLE[reply.userRole]}</span>
                <span class="user-id">${reply.userId}</span>
            </div>
        </div>
        <div class="review-wrap">
            <div>
                <h3 class="review-item-name">
                    ${$product.productName}
                </h3>
            </div>
            <p class="review-content">
                ${reply.replyContent}
            </p>
            <div class="review-footer">
                <span class="review-date">${date}</span>
                <div class="review-btn-wrap">
                    <button data-id="${reply.id}" onclick="checkOutLike(this)" class="review-rec-btn">
                        <span>도움돼요</span>
                        <span class="rec-count">${reply.replyLikeCount ? reply.replyLikeCount : 0}</span>
                    </button>
                    <button class="review-rec-btn update_review">
                        <span>수정하기</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
    `;

    // prepend 검사
    if (isPrepend) {
        $reviewListWrap.prepend(text);
        return;
    }
    $reviewListWrap.append(text);
}

/* 썸네일 이미지 바꾸기 */
function changeImgThumbnail(img) {
    let $img = $(img).find("img");

    $infoImgThumbnail.attr("src", $img.attr("src"));
}

$listSelecter.on("click", function () {
    let i = $listSelecter.index($(this));
    /* 스크롤이 이동할 위치 */
    $sectionWrap.eq(i)[0].scrollIntoView({behavior: "smooth"});
});

/* scroll 이벤트 */
$("document").ready(function () {
    $(window).scroll(function () {
        let position = $(window).scrollTop(); // 현재 스크롤바의 위치값을 반환합니다.

        if (position >= mainCategoryTopLoc - 200) {
            $mainCategoryList.addClass("list-menu-pos-fixed");
        } else {
            $mainCategoryList.removeClass("list-menu-pos-fixed");
        }
    });
});

/* 최신순, 추천순 정렬 */
const $reviewOrder = $(".review-orders button");

$reviewOrder.on("click", function () {
    $(this).addClass("fontW700");

    $reviewOrder.not($(this)).removeClass("fontW700");
});

/* 수정버튼은 session에 있는 유저와 댓글 작성자와 비교하여 */
/* 서로 일치할 때만 표시할 것 */
/* 수정버튼 클릭시 수정 textarea 등장 */
/* Ajax 콜백함수로 받아서 text에 데이터 꽃기 */
const $updateReviewBtn = $(".update_review");

$updateReviewBtn.on("click", function () {
    /* 수정 중임을 의미하는 클래스 */
    const ON_UPDATE = "review-on-update";

    let parent = $(this).parent().parent().parent();

    if (parent.hasClass(ON_UPDATE)) return;

    let text = `
    <div class="write-content-wrap">
        <form>
            <textarea
                class="write-textarea"
            >이거 엄청나요 망설이신다면 지금당장 구매해보세요. 너무 데치면 질겨지니 2분안쪽으로 데쳐서 초고추장 찍어서 먹으면 그곳이 천국입니다</textarea
            ><button class="write-regist-btn" type="button">
                <span class="regist">등록</span>
            </button>
            <button class="write-cancel-btn" type="button">
                <span class="regist">취소</span>
            </button>
        </form>
    </div>
    `;

    /* 수정 form append */
    parent.append(text);
    parent.addClass(ON_UPDATE);

    /* 등록버튼 이벤트 걸기 */
    /* 등록후 ajax 전송 */
    $(".write-regist-btn").on("click", function () {
        $(this).parent().parent().remove();
        parent.removeClass(ON_UPDATE);
    });

    /* 등록취소 이벤트 걸기 */
    $(".write-cancel-btn").on("click", function () {
        $(this).parent().parent().remove();
        parent.removeClass(ON_UPDATE);
    });
});


/* 주문수량 설정 */

let orderCount = 1;

let $totalPrice = $("#totalPrice");
$totalPrice.text(orderCount * productPrice);

$(".minus-btn").click(function () {
    let currentValue = parseInt($(".quantity-input").val());
    if (currentValue > 1) {
        $(".quantity-input").val(--currentValue);
    }
    orderCount = currentValue;
    $totalPrice.text(orderCount * productPrice);
});

$(".plus-btn").click(function () {
    let currentValue = parseInt($(".quantity-input").val());
    $(".quantity-input").val(++currentValue);
    orderCount = currentValue;
    $totalPrice.text(orderCount * productPrice);
});

$(".quantity-input").on("input", function () {
    let currentValue = $(this).val();
    let regex = /^[0-9]*$/; // 정규식: 숫자만 입력받도록 함
    if (!regex.test(currentValue) || currentValue <= 0) {
        $(this).val(orderCount);
    } else {
        orderCount = currentValue;
    }
    $totalPrice.text(orderCount * productPrice);
});

$(".quantity-input").on("blur", function () {
    let currentValue = $(this).val();
    if (currentValue === "") {
        $(this).val("1");
    } else if (currentValue.charAt(0) === "0") {
        $(this).val(parseInt(currentValue));
    }
});

/* ========= 장바구니 모달창 ========= */
const CART_URL = "/product/cart/add/";
$(".productCart-btn").on("click", function () {
    $(".cart-name").text($product.productName);
    $(".cart-distributor").text($product.distributorName);
    $(".cart-price").text(productPrice);
    $(".cart-order-count").text(orderCount);
    $(".cart-total-price").text(orderCount * productPrice + " 원");

    $("#cart-modal").css("display", "block");
});

// 닫기 버튼을 클릭했을 때
$(".close").on("click", function () {
    $("#cart-modal").css("display", "none");
});

// 예 버튼을 클릭했을 때
$("#modal-yesBtn").on("click", function () {
    console.log($(".quantity-input").val());
    $doAjaxPost("POST",
        CART_URL + $product.id, // 상품 ID
        {cartOrderAmount: $(".quantity-input").val()}, // 주문수량
        (result) => {  // callback
            $("#cart-modal").css("display", "none");

            console.log(result);
        }
    );

});

// 모달창 외부를 클릭했을 때
$(window).on("click", function (event) {
    if ($(event.target).is('.modal')) {
        $("#cart-modal").css("display", "none");
    }
});

/* 댓글 작성 */
const REPLY_URL = `/product/detail/reply/write/${$product.id}`;

const $replyWriteBtn = $(".write-regist-btn");

$replyWriteBtn.on("click", function () {
    if ($('.write-textarea').val() == "") {
        return;
    }

    $doAjaxPost("POST",
        REPLY_URL,
        {replyContent: $('.write-textarea').val()},
        (result) => {
            let count = Number($(".review-count span").text());
            // 댓글 맨위에 append
            appendReplyList(result, true);
            // 댓글수 증가
            $(".review-count span").text(++count);
            $(".reply-count").text(count);
            // 댓글 내용 초기화
            $('.write-textarea').val("");
            console.log(result);
        }
    );
});

/* 댓글 좋아요 */
const $replyLikeBtn = $(".review-rec-btn");
const REPLY_LIKE_URL = "/product/detail/reply/like";

function checkOutLike(likeBtn) {
    let id = $(likeBtn).data("id");
    let $value = $(likeBtn).find(".rec-count");
    let count = Number($value.text());

    $doAjaxPost("POST",
        REPLY_LIKE_URL + `/${id}`,
        {},
        (result) => {
            $value.text(result ? --count : ++count);
        });
}




/* =====================================  결제하기 버튼 눌렀을때 AJAX    ==================================================== */
const $payBtn = $('#payment');
const $amount = $('#amount');
console.log($amount);
let productCartDTO = [$amount,productName];

$payBtn.on('click', function () {
    $doAjaxPost(
        "post",`/product/cart/add/${id}`,
        {productCartDTO:productCartDTO},
        function () {
            location.href="/order/product";
        }
    );
});

