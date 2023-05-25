/* recipe-board.html */


const setList = $('.list-append-wrap');
let page = 1;
let previousItemCount = 0; // 이전에 추가된 항목 수를 저장하는 변수

function showList(recipeBoardDTOS){
    if (recipeBoardDTOS.content.length === 0 && previousItemCount === 0) {
        $(window).off('scroll');
        return;
    }

    // 이전에 추가된 항목 수를 업데이트
    previousItemCount = recipeBoardDTOS.length;

    let text ="";
    console.log(recipeBoardDTOS);
    recipeBoardDTOS.content.forEach((recipeDetail, i) => {
        let userFile = recipeDetail.memberDTO.userFileDTO;
        let userFilePath;

        if(userFile){
            userFilePath = '/image/display?fileName=' + userFile.filePath + "/t_" + userFile.fileUuid + "_" + userFile.fileOrgName;
        }else{
            userFilePath = "/img/mypage/nomalProfile.png";
        }

        // 기본 이미지 경로
        let filePath = "";
        let boardFiles = recipeDetail.recipeBoardFiles;
        console.log(recipeDetail);
        for (let i = 0; i < boardFiles.length; i++) {
            if(boardFiles[i].fileRepresent == "REPRESENT"){
                filePath = '/image/display?fileName=' + boardFiles[i].filePath + "/t_" + boardFiles[i].fileUuid + "_" + boardFiles[i].fileOrgName;
            }
        }

        text += `
            <article class="board-item-wrap">
                 <div class="profile-area">
                    <a href="javascript:void(0)"
                      ><div class="profile-wrap">
                        <div class="writer-image rw" style="margin-bottom: 0;">
                          <img
                            src="${userFilePath}"
                            alt=""
                          />
                        </div>
                        <div class="writer-info-wrap">
                          <div class="writer-info">
                            <div class="writer-name" style="margin-left: 5px; margin-right: 0; width: 50px;">${recipeDetail.memberDTO.memberName}</div>
                            <span class="write-date" style="width: 65px;">${recipeDetail.boardRegisterDate}</span>
                          </div>
                        </div>
                      </div>
                    </a>
                  </div>
                  <a href="/user-board/recipe-board-detail/${recipeDetail.id}"
                    ><h3 class="board-title">${recipeDetail.boardTitle}</h3>
                    <p class="board-content">
                      ${recipeDetail.boardContent}
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
                        <span class="recommend-count">${recipeDetail.recipeLikeCount}</span>
                      </div>
                      <div class="reply-btn">
                        <svg width="18" height="18" viewBox="0 0 18 18">
                          <path
                            fill="currentColor"
                            transform="matrix(-1 0 0 1 18 0)"
                            d="M9 1c4.377 0 8 3.14 8 7s-3.623 7-8 7c-.317 0-.593-.026-.954-.088l-.395-.074-.205-.043-3.295 2.089a.75.75 0 0 1-.968-.143l-.067-.09a.75.75 0 0 1 .143-.968l.09-.067 3.55-2.25a.75.75 0 0 1 .551-.1l.652.132.301.052c.228.036.408.05.597.05 3.592 0 6.5-2.52 6.5-5.5S12.592 2.5 9 2.5C5.407 2.5 2.5 5.02 2.5 8c0 1.858 1.039 3.573 2.773 4.348a.75.75 0 1 1-.612 1.37C2.37 12.693 1 10.432 1 8c0-3.86 3.622-7 8-7z"
                          ></path></svg
                        ><span class="reply-count">${recipeDetail.recipeReplyCount}</span>
                      </div>
                    </div></a
                  >
                </article>
    
    `
    })
    setList.append(text);
}

// 최신순 클릭 이벤트
$('.last-pop-btn a:first-child').click(function(e) {
    e.preventDefault();

    // 최신순에 'on' 클래스 추가, 인기순에서 'on' 클래스 제거
    $(this).addClass('on');
    $(this).siblings().removeClass('on');

    // 초기화 후 첫 페이지 데이터 로드
    setList.empty();
    page = 1;
    loadRecipeBoardList('/user-board/recipe-board-list/recent', { page: page });
    $(window).scroll(function() {
        if($(window).scrollTop() + $(window).height() > $(document).height() * 0.7) {
            page++;
            loadRecipeBoardList('/user-board/recipe-board-list/recent', { page: page });
            // 이전에 추가된 항목 수를 업데이트
            previousItemCount = setList.children('.board-item-wrap').length;
        }
    });
});

// 인기순 클릭 이벤트
$('.last-pop-btn a:last-child').click(function(e) {
    e.preventDefault();

    // 인기순에 'on' 클래스 추가, 최신순에서 'on' 클래스 제거
    $(this).addClass('on');
    $(this).siblings().removeClass('on');

    // 초기화 후 첫 페이지 데이터 로드
    setList.empty();
    page = 1;
    loadRecipeBoardList('/user-board/recipe-board-list/popular', { page: page });
    $(window).scroll(function() {
        if($(window).scrollTop() + $(window).height() > $(document).height() * 0.7) {
            page++;
            loadRecipeBoardList('/user-board/recipe-board-list/popular', { page: page });
            // 이전에 추가된 항목 수를 업데이트
            previousItemCount = setList.children('.board-item-wrap').length;
        }
    });
});

// 리뷰 게시물 목록 로드 함수
function loadRecipeBoardList(url, data) {
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        dataType: "json",
        success: function(response) {
            showList(response);
            console.log(response.content.length);
        }
    });
}


// 초기 페이지 로드
loadRecipeBoardList('/user-board/recipe-board-list/recent', { page: page });

// 스크롤 이벤트 핸들러
$(window).scroll(function() {
    if($(window).scrollTop() + $(window).height() > $(document).height() * 0.7) {
        page++;
        if ($('.last-pop-btn a:last-child').text() == "최신순") {
            loadRecipeBoardList('/user-board/recipe-board-list/recent', { page: page });
        } else if ($('.last-pop-btn a:last-child').text() == "인기순") {
            loadRecipeBoardList('/user-board/recipe-board-list/popular', { page: page });
        }

        // 이전에 추가된 항목 수를 업데이트
        previousItemCount = setList.children('.board-item-wrap').length;
    }
});

function elapsedTime(date) {
    const start = new Date(date);
    const end = new Date();

    const diff = (end - start) / 1000;

    const times = [
        { name: '년', milliSeconds: 60 * 60 * 24 * 365 },
        { name: '개월', milliSeconds: 60 * 60 * 24 * 30 },
        { name: '일', milliSeconds: 60 * 60 * 24 },
        { name: '시간', milliSeconds: 60 * 60 },
        { name: '분', milliSeconds: 60 },
    ];

    for (const value of times) {
        const betweenTime = Math.floor(diff / value.milliSeconds);

        if (betweenTime > 0) {
            return `${betweenTime}${value.name} 전`;
        }
    }
    return '방금 전';
}

/* 남은 일자 구하기 */
function remainingDays(date) {
    const start = new Date();
    const end = new Date(date);

    const diff = (end - start) / (1000 * 60 * 60 * 24);

    if (diff < 0) {
        return '모집이 마감되었습니다.';
    } else if (diff === 0) {
        return '오늘이 마감일 입니다.';
    } else {
        return `모집 마감까지 ${Math.ceil(diff)}일`;
    }
}

