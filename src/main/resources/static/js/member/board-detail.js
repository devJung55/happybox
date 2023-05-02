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

/* 하트 이벤트 */
$('.btn-heart').on('click', function(){
    if($(this).hasClass('on')){
        $(this).removeClass('on');
    } else{
        $(this).addClass('on');
    }
});


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
  