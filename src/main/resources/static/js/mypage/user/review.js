/* review.html */
let page = 0;
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
                        <a href="javascript:void(0)">
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

            text += `
                        </em>
                        <ul class="sep-list type3">
                            <li><span class="name">${board.memberDTO.userId}</span></li>
                            <li class=""><span class="date">${board.boardRegisterDate}</span></li>
                        </ul>
                        <p class="detail text-elps3">
                            <span style="white-space:pre-line">${board.boardContent}</span>
                        </p>
                    </div>
                </div>
            </li>
            <div class="border__bottom"></div>
        `;
    });
    $reviewBoardAppend.append(text);
    showPage(reviewBoards);
}

$(".paging-div").on("click", "a", function(e) {
    e.preventDefault();
    const targetPage = $(this).text();
    page = parseInt(targetPage);
    $reviewBoardAppend.empty();
    myPageService.reviewBoardListAjax(page);
});