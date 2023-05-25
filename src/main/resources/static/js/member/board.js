/* review-board.html */


const setList = $('.list-append-wrap');
let page = 1;
let previousItemCount = 0; // 이전에 추가된 항목 수를 저장하는 변수

function showList(reviewBoardDTOS) {
    if (reviewBoardDTOS.content.length === 0 && previousItemCount === 0) {
        $(window).off('scroll');
        return;
    }

    // 이전에 추가된 항목 수를 업데이트
    previousItemCount = reviewBoardDTOS.content.length;

    let text = "";
    console.log(reviewBoardDTOS);
    reviewBoardDTOS.content.forEach((reviewDetail, i) => {

        let userFile = reviewDetail.memberDTO.userFileDTO;
        console.log(userFile);
        let userFilePath;

        if (userFile) {
            userFilePath = '/image/display?fileName=' + userFile.filePath + "/t_" + userFile.fileUuid + "_" + userFile.fileOrgName;
        } else {
            userFilePath = "/img/mypage/nomalProfile.png";
        }

        // 기본 이미지 경로
        let filePath = "";
        let boardFiles = reviewDetail.reviewBoardFiles;
        for (let i = 0; i < boardFiles.length; i++) {
            if (boardFiles[i].fileRepresent == "REPRESENT") {
                filePath = '/image/display?fileName=' + boardFiles[i].filePath + "/t_" + boardFiles[i].fileUuid + "_" + boardFiles[i].fileOrgName;
            }
        }

        text += `
    <article class="board-item-wrap">
                  <div class="profile-area">
                    <a href="javascript:void(0)"
                      ><div class="profile-wrap">
                        <div class="writer-image rw">
                          <img
                            src="${userFilePath}"
                            alt=""
                          />
                        </div>
                        <div class="writer-info-wrap">
                          <div class="writer-info">
                            <div class="writer-name">${reviewDetail.memberDTO.memberName}</div>
                            <span class="write-date">${reviewDetail.boardRegisterDate}</span>
                          </div>
                          <div>
                            <div id="welfare-name">${reviewDetail.welfareName}</div>
                            <em class="rating-point">
                                <img class="rating__point one"  src="/img/mypage/rating-pull.png">
                                <img class="rating__point two"
                                src="${reviewDetail.reviewRating > 1 ? '/img/mypage/rating-pull.png' : '/img/mypage/rating.png'}">
                                <img class="rating__point three"
                                src="${reviewDetail.reviewRating > 2 ? '/img/mypage/rating-pull.png' : '/img/mypage/rating.png'}">
                                <img class="rating__point four"
                                src="${reviewDetail.reviewRating > 3 ? '/img/mypage/rating-pull.png' : '/img/mypage/rating.png'}">
                                 <img class="rating__point five"
                                src="${reviewDetail.reviewRating > 4 ? '/img/mypage/rating-pull.png' : '/img/mypage/rating.png'}">
                            </em>
                          </div>
                        </div>
                      </div>
                    </a>
                  </div>
                  <a href="/user-board/review-board-detail/${reviewDetail.id}"
                    ><h3 class="board-title">${reviewDetail.boardTitle}</h3>
                    <p class="board-content">${reviewDetail.boardContent}
                    </p>
                    <picture>
                      <img
                        src="${filePath}"
                        class="board-image"
                    /></picture>
                    <div class="board-item-bottom">
                      <div class="recommend-btn">
                        <svg enable-background="new 0 0 32 32" id="Editable-line" version="1.1" viewBox="0 0 32 32" xml:space="preserve" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" 
                        style="
                         width: 18px;
                         margin-bottom: 2px;
                         ">
                            <path d="  M16,8.064c-2.974-2.753-7.796-2.753-10.77,0s-2.974,7.215,0,9.968L16,28l10.77-9.968c2.974-2.753,2.974-7.215,0-9.968  S18.974,5.312,16,8.064z" fill="none" id="XMLID_49_" stroke="#000000" stroke-linecap="round" stroke-linejoin="round" stroke-miterlimit="10" stroke-width="2">
                            </path>
                        </svg>
                        <span class="recommend-count">${reviewDetail.reviewLikeCount}</span>
                      </div>
                      <div class="reply-btn">
                        <svg width="18" height="18" viewBox="0 0 18 18">
                          <path
                            fill="currentColor"
                            transform="matrix(-1 0 0 1 18 0)"
                            d="M9 1c4.377 0 8 3.14 8 7s-3.623 7-8 7c-.317 0-.593-.026-.954-.088l-.395-.074-.205-.043-3.295 2.089a.75.75 0 0 1-.968-.143l-.067-.09a.75.75 0 0 1 .143-.968l.09-.067 3.55-2.25a.75.75 0 0 1 .551-.1l.652.132.301.052c.228.036.408.05.597.05 3.592 0 6.5-2.52 6.5-5.5S12.592 2.5 9 2.5C5.407 2.5 2.5 5.02 2.5 8c0 1.858 1.039 3.573 2.773 4.348a.75.75 0 1 1-.612 1.37C2.37 12.693 1 10.432 1 8c0-3.86 3.622-7 8-7z"
                          ></path></svg
                        ><span class="reply-count">${reviewDetail.reviewBoardReplyCount}</span>
                      </div>
                    </div></a
                  >
                </article>
    `
    })
    setList.append(text);
}


// 최신순 클릭 이벤트
$('.last-pop-btn a:first-child').click(function (e) {
    e.preventDefault();

    // 최신순에 'on' 클래스 추가, 인기순에서 'on' 클래스 제거
    $(this).addClass('on');
    $(this).siblings().removeClass('on');

    // 초기화 후 첫 페이지 데이터 로드
    setList.empty();
    page = 1;
    loadReviewBoardList('/user-board/review-board-list/recent', {page: page});
    $(window).scroll(function () {
        if ($(window).scrollTop() + $(window).height() > $(document).height() * 0.7) {
            page++;
            loadReviewBoardList('/user-board/review-board-list/recent', {page: page});
            // 이전에 추가된 항목 수를 업데이트
            previousItemCount = setList.children('.board-item-wrap').length;
        }
    });
});

// 인기순 클릭 이벤트
$('.last-pop-btn a:last-child').click(function (e) {
    e.preventDefault();

    // 인기순에 'on' 클래스 추가, 최신순에서 'on' 클래스 제거
    $(this).addClass('on');
    $(this).siblings().removeClass('on');

    // 초기화 후 첫 페이지 데이터 로드
    setList.empty();
    page = 1;
    loadReviewBoardList('/user-board/review-board-list/popular', {page: page});
    $(window).scroll(function () {
        if ($(window).scrollTop() + $(window).height() > $(document).height() * 0.7) {
            page++;
            loadReviewBoardList('/user-board/review-board-list/popular', {page: page});
            // 이전에 추가된 항목 수를 업데이트
            previousItemCount = setList.children('.board-item-wrap').length;
        }
    });
});

// 리뷰 게시물 목록 로드 함수
function loadReviewBoardList(url, data) {
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        dataType: "json",
        success: function (response) {
            showList(response);
            console.log(response.content.length);
        }
    });
}


// 초기 페이지 로드
loadReviewBoardList('/user-board/review-board-list/recent', {page: page});

// 스크롤 이벤트 핸들러
$(window).scroll(function () {
    if ($(window).scrollTop() + $(window).height() > $(document).height() * 0.7) {
        page++;
        if ($('.last-pop-btn a:last-child').text() == "최신순") {
            loadReviewBoardList('/user-board/review-board-list/recent', {page: page});
        } else if ($('.last-pop-btn a:last-child').text() == "인기순") {
            loadReviewBoardList('/user-board/review-board-list/popular', {page: page});
        }

        // 이전에 추가된 항목 수를 업데이트
        previousItemCount = setList.children('.board-item-wrap').length;
    }
});


