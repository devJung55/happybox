/* recipe-board.html */
/* review-board.html */

$(".sort-btn").on('click', function(e){
    e.preventDefault();

    // 현재 클릭한 요소의 하위 .tit-type 요소에만 active 클래스 추가
    $(this).find('.tit-type').addClass('active').end().siblings().find('.tit-type').removeClass('active');

    // 모든 .tit-type 요소에서 active 클래스 제거
    $(".tit-type").not($(this).find('.tit-type')).removeClass("active");
});

$(document).ready(function() {
    // 최신순 클릭 이벤트
    $('.last-pop-btn a:first-child').click(function() {
      // 최신순에 'on' 클래스 추가, 인기순에서 'on' 클래스 제거
      $(this).addClass('on');
      $(this).siblings().removeClass('on');

      setList.empty();
        $doAjax("/user-board/review-board-list/recent", {
            page: 1,
            size: 5
        });
    });
  
    // 인기순 클릭 이벤트
    $('.last-pop-btn a:last-child').click(function() {
      // 인기순에 'on' 클래스 추가, 최신순에서 'on' 클래스 제거
      $(this).addClass('on');
      $(this).siblings().removeClass('on');

        setList.empty();
        $doAjax("/user-board/review-board-list/popular", {
            page: 1,
            size: 5
        });
    });
  });

const setList = $('.list-append-wrap');
// let page = 1;

function showList(reviewList){
    let text ="";
    reviewList.content.forEach((reviewDetail, i) => {
        console.log(reviewDetail);

        // 기본 이미지 경로
        let filePath = "";
        let boardFiles = reviewDetail.reviewBoardFiles;

        if(boardFiles){
            filePath = '/image/display?fileName=' + boardFiles[0].filePath + "/t_" + boardFiles[0].fileUuid + "_" + boardFiles[0].fileOrgName;
        }

        text += `
    <article class="board-item-wrap">
                  <div class="profile-area">
                    <a href="javascript:void(0)"
                      ><div class="profile-wrap">
                        <div class="writer-image rw">
                          <img
                            src=""
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
                              <img
                                class="rating__point one"
                                src="/img/mypage/rating-pull.png"
                              />
                              <img
                                class="rating__point two"
                                src="/img/mypage/rating-pull.png"
                              />
                              <img
                                class="rating__point three"
                                src="/img/mypage/rating-pull.png"
                              />
                              <img
                                class="rating__point four"
                                src="/img/mypage/rating-pull.png"
                              />
                              <img
                                class="rating__point five"
                                src="/img/mypage/rating.png"
                              />
                            </em>
                          </div>
                        </div>
                      </div>
                    </a>
                  </div>
                  <a href="javascript:void(0)"
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
                        <svg width="18" height="18" viewBox="0 0 18 18">
                          <path
                            fill="currentColor"
                            d="M13.353 2.214c.082.164.15.332.204.502.325 1.032.13 2.08-.396 3.092l-.105.191L16.253 6a.75.75 0 0 1 .743.648l.007.102v5.75a.75.75 0 0 1-.106.385l-.058.084-3.004 3.75a.75.75 0 0 1-.472.273L13.25 17H9.22a.75.75 0 0 1-.101-1.493l.102-.007h3.668l2.614-3.264V7.5h-3.91a.75.75 0 0 1-.604-1.195l.066-.077c.137-.14.36-.415.584-.778.5-.808.702-1.6.487-2.283a1.858 1.858 0 0 0-.113-.278c-.278-.551-1.075-.442-1.075-.056a3.17 3.17 0 0 1-.777 2.125c-.293.338-.59.555-.774.647l-.472.292c-.89.568-1.459 1.04-1.762 1.409l-.097.128-.058.095v.062l-.004.016-.006.093a.75.75 0 0 1-.641.641l-.102.007-.102-.007a.75.75 0 0 1-.648-.743V7.5H2.496v8h2.999l-.001-4.535.007-.102a.75.75 0 0 1 1.493.102v5.286l-.007.102a.75.75 0 0 1-.743.648H1.747l-.102-.007a.75.75 0 0 1-.648-.743v-9.5l.007-.102A.75.75 0 0 1 1.747 6h4.498l.066.005c.387-.38.92-.796 1.621-1.256l.472-.3.253-.154c.07-.035.217-.143.37-.32.226-.26.37-.576.403-.969l.008-.173c0-2.082 2.972-2.491 3.915-.619z"
                          ></path></svg
                        ><span class="recommend-count">${reviewDetail.reviewLikeCount}</span>
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

showList(reviewList);

function $doAjax(url, data) {
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        dataType: "json",
        success: function (response) {
            showList(response);
        }
    });
}



// $(window).scroll(
//     function() {
//         if (Math.ceil($(window).scrollTop()) >= $(document).height() - $(window).height() - 50) {
//             page++;
//             $doAjax(page, "/user-board/review-board-list/popular");
//         }
//     }
// );
