/* review.html */
let page = 0;
myPageService.reviewBoardListAjax();
const $reviewBoardAppend = $(".review__append");

function showReviewBoardList(reviewBoards) {
    let text = "";
    let img = "";
    let rating = "";

    reviewBoards.content.forEach(board => {
        text += `
            <li class="colum reviewUnit">
                <div class="review-detail">
                    <div class="img">
                    `;

            img = `
                        <img class="lozad" src="https://file.rankingdak.com/image/RANK/REVIEW/PD_RV_IMG1/20230419/IMG1681zzz880436446.jpg">
                    </div>
                    `;

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

            rating = `
                            <img class="rating__point one" src="/img/mypage/rating-pull.png" width="18">
                            <img class="rating__point two" src="/img/mypage/rating-pull.png" width="18">
                            <img class="rating__point three" src="/img/mypage/rating-pull.png" width="18">
                            <img class="rating__point four" src="/img/mypage/rating-pull.png" width="18">
                            <img class="rating__point five" src="/img/mypage/rating.png" width="18">
                    `;
            text += rating;

            text += `
                        </em>
                        <ul class="sep-list type3">
                            <li><span class="name">ILovePizza</span></li>
                            <li class=""><span class="date">2023.04.19</span></li>
                        </ul>
                        <p class="detail text-elps3">
                            <span style="white-space:pre-wrap">
        먹어본 닭가슴살중 맛있닭 닭가슴살 스테이크가 탑이라
        몇년째 이거만 먹어요~ 간편하고 정말 너무 너무 맛있습니다❤️
                            </span>
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
    $append.empty();
    myPageService.recipeBoardListAjax(page);
});