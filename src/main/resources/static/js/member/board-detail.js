/* recipe-board-detail.html */
/* review-board-detail.html */


/* 텍스트 더보기 */
$('.info-area__box').on('click', function() {
    $(this).find('.info-area__box-list').css('height', '100%');
  });

/* 이미지 slide 이벤트 */

$(document).ready(function() {
    // 변수 설정
    var slideIndex = 0;
    var slideLength = $(".slider__sec .slick-slide").length;
  
    // 이전 버튼 클릭 시
    $(".swiper-button-prev").on("click", function() {
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
    $(".swiper-button-next").on("click", function() {
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

const $files = review.files;
const setList = $('.detail-container');

function showDetail(){
    console.log(review);
    let text ="";
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
    review.reviewBoardFiles.forEach((file, i) => {
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
              <span class="current">1</span> / <span class="total">${review.reviewBoardFiles.length}</span>
            </div>
          </div>
          <div class="refrig-bnr">
            <a href="javascript:void(0)">
              <img
                src="https://www.rankingdak.com/resources/pc/images/img/pc_delivery_banner2.jpg"
            /></a>
          </div>
        </div>

        <div class="info-area">
          <div class="info-area__btn">
            <button type="button" class="update-btn">
              <svg
                class="write-button-icon"
                style="enable-background: new 0 0 1696.162 1696.143"
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
            </button>
            <button type="button" class="delete-btn">
              <svg viewBox="0 0 448 512" class="delete-button-icon" xmlns="http://www.w3.org/2000/svg">
                <path
                d="M432 80h-82.38l-34-56.75C306.1 8.827 291.4 0 274.6 0H173.4C156.6 0 141 8.827 132.4 23.25L98.38 80H16C7.125 80 0 87.13 0 96v16C0 120.9 7.125 128 16 128H32v320c0 35.35 28.65 64 64 64h256c35.35 0 64-28.65 64-64V128h16C440.9 128 448 120.9 448 112V96C448 87.13 440.9 80 432 80zM171.9 50.88C172.9 49.13 174.9 48 177 48h94c2.125 0 4.125 1.125 5.125 2.875L293.6 80H154.4L171.9 50.88zM352 464H96c-8.837 0-16-7.163-16-16V128h288v320C368 456.8 360.8 464 352 464zM224 416c8.844 0 16-7.156 16-16V192c0-8.844-7.156-16-16-16S208 183.2 208 192v208C208 408.8 215.2 416 224 416zM144 416C152.8 416 160 408.8 160 400V192c0-8.844-7.156-16-16-16S128 183.2 128 192v208C128 408.8 135.2 416 144 416zM304 416c8.844 0 16-7.156 16-16V192c0-8.844-7.156-16-16-16S288 183.2 288 192v208C288 408.8 295.2 416 304 416z"
                />
              </svg>
            </button>
            <button type="button" class="btn-heart">
            </button>
          </div>
          <span class="writer-button-wrap">
            <p class="writer-name">${review.memberDTO.memberName}</p>
          </span>
          <h3 class="info-area__name">
            <span class="strong">${review.boardTitle}</span>
          </h3>
          <div class="info-area__box">
            <div class="info-area__box-list">
              <div class="info-area__box-cont">
                <p id="welfare-name">
                    ${review.welfareName}
                </p>
                        <em class="rating-point">
                            <img class="rating__point one" src="/img/mypage/rating-pull.png">
                            <img class="rating__point two" src="/img/mypage/rating-pull.png">
                            <img class="rating__point three" src="/img/mypage/rating-pull.png">
                            <img class="rating__point four" src="/img/mypage/rating-pull.png">
                            <img class="rating__point five" src="/img/mypage/rating.png">
                        </em>
                <div class="detail-content">
                    ${review.boardContent}
                </div>
              </div>
            </div>
          </div>
        </div>
    `
    setList.append(text);
}

showDetail();



  /* 댓글 관련 js */

const $reviewListWrap = $(".review-list-wrap");

/* 임시 이미지 갯수 */
const imgCount = 7;

/* 임시 리뷰 요소 append */
for (let i = 0; i < imgCount; i++) {
    let text = `
    <div class="review-list">
        <div class="user-info-wrap">
            <div class="user-info">
                <span class="user-type">일반</span>
                <span class="user-id">kjp1234</span>
            </div>
        </div>
        <div class="review-wrap">
            <div>
                <h3 class="review-item-name">
                    [4.22원데이] 더라인 순면 피그먼트
                </h3>
            </div>
            <p class="review-content">
                이거 엄청나요 망설이신다면 지금당장 구매해보세요. 너무
                데치면 질겨지니 2분안쪽으로 데쳐서 초고추장 찍어서 먹으면
                그곳이 천국입니다
            </p>
            <div class="review-footer">
                <span class="review-date">2022.11.12</span>
                <div class="review-btn-wrap">
                    <button class="review-rec-btn">
                        <span>도움돼요</span>
                        <span class="rec-count">1</span>
                    </button>
                    <button class="review-rec-btn update_review">
                        <span>수정하기</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
    `;
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
const $updateReviewBtn = $(".update_review");

$updateReviewBtn.on("click", function () {
    /* 수정 중임을 의미하는 클래스 */
    const ON_UPDATE = "review-on-update";

    let parent = $(this).parent().parent().parent();

    if(parent.hasClass(ON_UPDATE)) return;

    let text = `
    <div class="write-content-wrap">
        <form>
            <textarea
                class="write-textarea"
                placeholder="댓글 남기기"
            ></textarea
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
    $(".write-cancel-btn").on("click", function() {
        $(this).parent().parent().remove();
        parent.removeClass(ON_UPDATE);
    })
});

/* 하트 이벤트 */
$('.btn-heart').on('click', function(){
    if($(this).hasClass('on')){
        $(this).removeClass('on');
    } else{
        $(this).addClass('on');
        heartInsert();
    }
});

function heartInsert() {
    $.ajax({
        type: "POST",
        url: "/user-board/review-board-detail/heart-insert",
        data: data,
        dataType: "json",
        success: function (response) {
            showList(response);
        }
    });
}