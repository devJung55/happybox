/* review.html */
myPageService.reviewBoardListAjax();
const $reviewBoardAppend = $(".review__append");

function showReviewBoardList(reviewBoards) {
    let text = "";

    reviewBoards.content.forEach(board => {
        let img = "";
        let rating = "";

        text += `
            <li class="colum reviewUnit">
                <div class="review-detail">
                    <div class="img">
                    `;

            if(board.reviewBoardFiles.length != 0) {
                for (let i = 0; i < board.reviewBoardFiles.length; i++) {
                    if(i == 0) {
                        img = `
                            <img class="lozad" src="/image/display?fileName=${board.reviewBoardFiles[i].filePath}/${board.reviewBoardFiles[i].fileUuid}_${board.reviewBoardFiles[i].fileOrgName}">
                        `;
                    }
                }
            } else {
                img = "";
            }

            text += img;

            text += `
                    <div class="detail__item">
                        <a href="/user-board/review-board-detail/${board.id}">
                            <span class="txt-change">상세보기</span>
                            <img class="arrow-0deg arrow" src="/img/mypage/inquiry-arrow.png" width="15">
                        </a>
                    </div>
                    <div class="txt">
                        <em class="rating-point">
                    `;

            for (let i = 0; i < 5; i++) {
                if(i < board.reviewRating) {
                    rating += `
                        <img class="rating__point one" src="/img/mypage/rating-pull.png" width="18">
                    `;
                } else {
                    rating += `
                        <img class="rating__point one" src="/img/mypage/rating.png" width="18">
                    `;
                }
            }

            text += rating;

            let boardContent = `${board.boardContent}`.trim();
            text += `
                        </em>
                        <ul class="sep-list type3">
                            <li><span class="name">${board.memberDTO.userId}</span></li>
                            <li class=""><span class="date">${board.boardRegisterDate}</span></li>
                        </ul>
                        <p class="detail text-elps3" style="margin: 22px 0 22px 0; width: 713px;">
                            <span>${boardContent}</span>
                        </p>
                    </div>
                </div>
            </li>
            <div class="border__bottom"></div>
        `;
    });
    $reviewBoardAppend.empty();
    $reviewBoardAppend.append(text);
}
