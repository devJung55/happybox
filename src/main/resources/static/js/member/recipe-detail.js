/* recipe-board-detail.html */

/* 이미지 slide 이벤트 */

$(document).ready(function () {
    // 변수 설정
    var slideIndex = 0;
    var slideLength = $(".slider__sec .slick-slide").length;

    // 이전 버튼 클릭 시
    $(".swiper-button-prev").on("click", function () {
        if (slideIndex === 0) {
            slideIndex = slideLength - 1;
        } else {
            slideIndex--;
        }
        $(".slider__sec .slick-slide").removeClass("slick-current");
        $(".slider__sec .slick-slide")
            .eq(slideIndex)
            .addClass("slick-current");
        $(".slick-counter .current").text(slideIndex + 1);
    });

    // 다음 버튼 클릭 시
    $(".swiper-button-next").on("click", function () {
        if (slideIndex === slideLength - 1) {
            slideIndex = 0;
        } else {
            slideIndex++;
        }
        $(".slider__sec .slick-slide").removeClass("slick-current");
        $(".slider__sec .slick-slide")
            .eq(slideIndex)
            .addClass("slick-current");
        $(".slick-counter .current").text(slideIndex + 1);
        console.log(this);
    });

    // 첫 번째 사진에 slick-current 클래스 추가
    $(".slider__sec .slick-slide")
        .eq(slideIndex)
        .addClass("slick-current");
});

const $files = recipe.files;
const setList = $('.detail-container');
console.log(recipe);

function showDetail() {
    let text = "";
    text += `
    <div class="slider__sec">
          <div class="slider slick-initialized slick-slider">
            <button class="swiper-button-prev swiper-btn">
              <svg viewBox="0 0 512 512">
                <polygon
                  points="160,128.4 192.3,96 352,256 352,256 352,256 192.3,416 160,383.6 287.3,256 "
                ></polygon>
              </svg>
            </button>
            <div class="slick-list draggable">
              <div class="slick-track" style="opacity: 1; height: 100%">
              `
    recipe.recipeBoardFiles.forEach((file, i) => {
        console.log(file);
        let filePath = '/image/display?fileName=' + file.filePath + "/t_" + file.fileUuid + "_" + file.fileOrgName;
        text +=
            `
                <div
                  class="slick-slide"
                  data-slick-index="0"
                  aria-hidden="true"
                  style="
                    width: 400px;
                    height: 400px;
                    position: relative;
                    left: 0px;
                    top: 0px;
                    z-index: 1;
                  "
                  tabindex="-1"
                >
                  <div>
                    <div class="slider__list" style="width: 100%; display: inline-block">
                      <figure>
                        <img
                        src="${filePath}"
                        />
                      </figure>
                    </div>
                  </div>
                </div>
                 `
    })
    text +=
        `
              </div>
            </div>
            
            <button class="swiper-button-next swiper-btn">
              <svg viewBox="0 0 512 512">
                <polygon
                  points="160,128.4 192.3,96 352,256 352,256 352,256 192.3,416 160,383.6 287.3,256 "
                ></polygon>
              </svg>
            </button>
            <div class="slick-counter">
              <span class="current">1</span> / <span class="total">${recipe.recipeBoardFiles.length}</span>
            </div>
          </div>
          <div class="refrig-bnr">
            <a href="javascript:void(0)">
              <img
                src="/img/market/free_delivery.jpg"
            /></a>
          </div>
        </div>

        <div class="info-area">
          <div class="info-area__btn">
            `
    if ($userId == recipe.memberDTO.userId) {
        text +=
            `
            <a href="/user-board/recipe-board-modify/${recipe.id}" class="update-btn">
              <svg
                class="write-button-icon"
                style="enable-background: new 0 0 1696.162 1696.143;margin-top: 6px;margin-left: 6px;"
                version="1.1"
                viewBox="0 0 1696.162 1696.143"
                width="1696.162px"
                xml:space="preserve"
                xmlns="http://www.w3.org/2000/svg"
                xmlns:xlink="http://www.w3.org/1999/xlink"
              >
                <g id="pen">
                  <path
                    d="M1648.016,305.367L1390.795,48.149C1359.747,17.098,1318.466,0,1274.555,0c-43.907,0-85.188,17.098-116.236,48.148   L81.585,1124.866c-10.22,10.22-16.808,23.511-18.75,37.833L0.601,1621.186c-2.774,20.448,4.161,41.015,18.753,55.605   c12.473,12.473,29.313,19.352,46.714,19.352c2.952,0,5.923-0.197,8.891-0.601l458.488-62.231   c14.324-1.945,27.615-8.529,37.835-18.752L1648.016,537.844c31.049-31.048,48.146-72.33,48.146-116.237   C1696.162,377.696,1679.064,336.415,1648.016,305.367z M493.598,1505.366l-350.381,47.558l47.56-350.376L953.78,439.557   l302.818,302.819L493.598,1505.366z M1554.575,444.404l-204.536,204.533l-302.821-302.818l204.535-204.532   c8.22-8.218,17.814-9.446,22.802-9.446c4.988,0,14.582,1.228,22.803,9.446l257.221,257.218c8.217,8.217,9.443,17.812,9.443,22.799   S1562.795,436.186,1554.575,444.404z"
                  />
                </g>
                <g id="Layer_1" />
              </svg>
            </a>
            <button type="button" class="delete-btn" onclick="deleteModal()"
            data-id="${recipe.id}">
              <svg viewBox="0 0 448 512" class="delete-button-icon" xmlns="http://www.w3.org/2000/svg">
                <path
                d="M432 80h-82.38l-34-56.75C306.1 8.827 291.4 0 274.6 0H173.4C156.6 0 141 8.827 132.4 23.25L98.38 80H16C7.125 80 0 87.13 0 96v16C0 120.9 7.125 128 16 128H32v320c0 35.35 28.65 64 64 64h256c35.35 0 64-28.65 64-64V128h16C440.9 128 448 120.9 448 112V96C448 87.13 440.9 80 432 80zM171.9 50.88C172.9 49.13 174.9 48 177 48h94c2.125 0 4.125 1.125 5.125 2.875L293.6 80H154.4L171.9 50.88zM352 464H96c-8.837 0-16-7.163-16-16V128h288v320C368 456.8 360.8 464 352 464zM224 416c8.844 0 16-7.156 16-16V192c0-8.844-7.156-16-16-16S208 183.2 208 192v208C208 408.8 215.2 416 224 416zM144 416C152.8 416 160 408.8 160 400V192c0-8.844-7.156-16-16-16S128 183.2 128 192v208C128 408.8 135.2 416 144 416zM304 416c8.844 0 16-7.156 16-16V192c0-8.844-7.156-16-16-16S288 183.2 288 192v208C288 408.8 295.2 416 304 416z"
                />
              </svg>
            </button>
            `
    }
    text +=
        `
            <div class="like-btn-wrap">
                <a href="javascript:checkRecipeLike()">
                    <span class="like-btn">
                        <img src="/img/mypage/heart.png" alt=""/>
                    </span>
                </a>
            </div>
          </div>
          <span class="writer-button-wrap">
            <p class="writer-name">${recipe.memberDTO.memberName}</p>
          </span>
          <h3 class="info-area__name">
            <span class="strong">${recipe.boardTitle}</span>
          </h3>
          <div class="info-area__box" onclick="showMoreText()";>
            <div class="info-area__box-list">
              <div class="info-area__box-cont">
                <div class="detail-content">
                    ${recipe.boardContent}
                </div>
              </div>
            </div>
          </div>
        </div>
    `
    setList.append(text);
}

showDetail();

/* 텍스트 더보기 */
$('.info-area__box').on('click', function () {
    $(this).find('.info-area__box-list').css('height', '100%');
});

const goDelete = `/user-board/recipe-board-detail/delete/${recipe.id}`;

/* 게시글 삭제 */
function deleteModal(){
    $("#check-modal").css("display", "block");
}

// 댓글 삭제 모달
function showReplyDeleteModal(deleteBtn) {
    replyDeleteId = $(deleteBtn).data("id");
    $("#reply-modal").css("display", "block");
}

/* 댓글 삭제 */
const xBtn = $('.xBtn');

// 닫기 버튼을 클릭했을 때
$(".close").on("click", function () {
    $(this).closest(".modal").css("display", "none");
});

// 예 버튼을 클릭했을 때
function deleteBoard() {
    $.ajax({
        url: goDelete,
        type: 'DELETE',
        contentType: "application/json; charset=utf-8",
        success: function() {
            location.href = "/user-board/recipe-board-list";
        },
        error: function(error) {
            console.log(error);
        }
    });
}


// 모달창 외부를 클릭했을 때
$(window).on("click", function (event) {
    if ($(event.target).is('.modal')) {
        $("#check-modal").css("display", "none");
    }
});

/* 댓글 관련 js */
const $moreReview = $(".more-review");
const $reviewListWrap = $(".review-list-wrap");
const USER_ROLE = {
    MEMBER: "일반",
    WELFARE: "복지관",
    DISTRIBUTOR: "유통"
}

// 현재 페이지
let page = 1;
// 정렬 순서
let isReviewByDate = null;
// 마지막 여부
let isLastPage = false;

/* common/ajax.js */
$doAjax("get", `/user-board/recipe-board-detail/reply/${recipe.id}`,
    {},
    (result) => {
        console.log(result);
        result.content.forEach((reply) => appendReplyList(reply));
        if (result.last) $moreReview.css("display", "none");
    }
);

$moreReview.on("click", function () {
    $doAjax("get", `/user-board/recipe-board-detail/reply/${recipe.id}`,
        {page: ++page, isReviewByDate: isReviewByDate},
        (result) => {
            console.log(result);
            result.content.forEach((reply) => appendReplyList(reply));
            if (result.last) $(this).css("display", "none");
        }
    );
});

if ($userId) {
    $(".reply-writer-info").append(
        `
        <span class="user-type">${USER_ROLE[$userRole]}</span>
        <span class="user-id">${$userId}</span>
    `
    );
}

// 최신순
$(".reviewDate").on("click", function () {
    page = 1;
    isReviewByDate = true
    $reviewListWrap.empty();
    $doAjax("get", `/user-board/recipe-board-detail/reply/${recipe.id}`,
        {page: page, isReviewByDate: isReviewByDate},
        (result) => {
            result.content.forEach((reply) => appendReplyList(reply));
            $moreReview.css("display", "block")
        }
    );
});

// 인기순
$(".orderLikeCount").on("click", function () {
    page = 1;
    isReviewByDate = false;
    $reviewListWrap.empty();
    $doAjax("get", `/user-board/recipe-board-detail/reply/${recipe.id}`,
        {page: page, isReviewByDate: isReviewByDate},
        (result) => {
            result.content.forEach((reply) => appendReplyList(reply));
            $moreReview.css("display", "block")
        }
    );
});

/* 댓글 append */
function appendReplyList(reply, isPrepend) {
    console.log(reply);

    let text ='';
    let date = reply.updatedDate.split("T")[0];

    text += `
    <div class="review-list">
    `
    if ($userId == reply.userId) {
        text += `<span
            class="xBtn" 
            style="
            cursor: pointer;
            position: absolute;
            right: 17px;
            top: -15px;
            font-size: 25px;"
            onclick="showReplyDeleteModal(this)"
            data-id="${reply.id}"
        >&times;</span>
    `
    }
    text +=
        `
        <div class="user-info-wrap">
            <div class="user-info">
                <span class="user-type">${USER_ROLE[reply.userRole]}</span>
                <span class="user-id">${reply.userId}</span>
            </div>
        </div>
        <div class="review-wrap">
            <div>
                <h3 class="review-item-name">
                    ${recipe.boardTitle}
                </h3>
            </div>
            <p class="review-content">${reply.replyContent}</p>
            <div class="review-footer">
                <span class="review-date">${date}</span>
                <div class="review-btn-wrap">
                    <button data-id="${reply.id}" onclick="checkOutLike(this)" class="review-rec-btn">
                        <span>도움돼요</span>
                        <span class="rec-count">${reply.replyLikeCount ? reply.replyLikeCount : 0}</span>
                    </button>`;
    if ($userId == reply.userId) {
        text += `<button onclick="showReplyUpdate(this)" data-onmodify="false" data-id="${reply.id}" class="review-rec-btn update_review">
                            <span>수정하기</span>
                        </button>`;
    }
    `</div>
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
function showReplyUpdate(button) {

    let updateBtn = $(button);

    // 수정 중이라면 return
    if (updateBtn.data("onmodify")) return;

    // 수정창 부모
    let parent = updateBtn.closest(".review-wrap");
    let replyContent = updateBtn.closest(".review-footer").prev(".review-content");

    // 댓글 내용
    let contentText = replyContent.text().trim();
    console.log(contentText);

    // 댓글 id
    let id = updateBtn.data("id");

    updateBtn.data("onmodify", true);

    let text = `
    <div class="write-content-wrap">
        <form>
            <textarea
                class="write-textarea">${contentText}</textarea
            ><button class="write-update-btn" type="button">
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

    /* 등록후 ajax 전송 */
    $(".write-update-btn").on("click", function () {
        let data = {
            replyContent: $(this).prev(".write-textarea").val()
        }

        $.ajax({
            type: "patch",
            url: `/user-board/recipe-board-detail/reply/modify/${id}`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                replyContent.text(response.replyContent);
            }
        });

        // 수정창 닫기
        updateBtn.data("onmodify", false);
        $(this).closest(".write-content-wrap").remove();
    });

    /* 등록취소 이벤트 걸기 */
    $(".write-cancel-btn").on("click", function () {
        // 수정창 닫기
        $(this).closest(".write-content-wrap").remove();
        updateBtn.data("onmodify", false);
    });
}

/* 댓글 작성 */
const REPLY_URL = `/user-board/recipe-board-detail/reply/write/${recipe.id}`;

const $replyWriteBtn = $(".write-regist-btn");

$replyWriteBtn.on("click", function () {
    console.log("들어옴");
    if ($('.write-textarea').val() == "") {
        return;
    }

    $doAjaxPost("POST",
        REPLY_URL,
        {replyContent: $('.write-textarea').val()},
        (result) => {
            appendReplyList(result, true);
            // 댓글 내용 초기화
            $('.write-textarea').val("");
            console.log(result);
        }
    );
    window.location.reload();
});

/* 댓글 삭제 */
const deleteUrl = `/user-board/recipe-board-detail/reply/delete/${recipe.id}`;

function deleteReply(deleteBtn) {
    $.ajax({
        url: deleteUrl + `/${replyDeleteId}`,
        type: 'DELETE',
        dataType: 'JSON',
        success: function(result) {
            console.log(result);
        },
        error: function(error) {
            console.log(error);
        }
    });
    window.location.reload();
}



/* 댓글 좋아요 */
const $replyLikeBtn = $(".review-rec-btn");
const REPLY_LIKE_URL = "/user-board/recipe-board-detail/reply/like";

function checkOutLike(likeBtn) {
    console.log(likeBtn);
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

/* ======================================================================================= */

const BOARD_LIKE_URL = `/user-board/recipe-board-detail/like/${recipe.id}`;
const likeSrc = "/img/mypage/heart-pull.png";
const unlikeSrc = "/img/mypage/heart.png";
/* 이미 좋아요인지 검사 */

$(".like-btn img").attr("src", `${isLike ? likeSrc : unlikeSrc}`);

/* 좋아요 눌렀을 때 */
function checkRecipeLike() {
    console.log(isLike);
    $doAjax("POST", BOARD_LIKE_URL, {}, (result) => {
        $(".like-btn img").attr("src", `${result ? unlikeSrc : likeSrc}`);
    });
}