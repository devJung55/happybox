
// 파일 앞 기본경로
const FILE_DISPLAY_URL = '/image/display?fileName=';

// 복지관 리뷰
reviews.forEach(review => appendReview(review));

function appendReview(review) {
    let text;

    let filePath = "/img/welfare/welfare_img_default.png";

    if (review.reviewBoardFiles.length > 0) {
        // 대표사진 검사
        let repFile = review.reviewBoardFiles.filter(file => file.fileRepresent === "REPRESENT")[0];
        filePath = FILE_DISPLAY_URL + repFile.filePath + '/t_' + repFile.fileUuid + '_' + repFile.fileOrgName;
    }

    text = `
            <li
            class="swiper-slide"
            style="width: 320px; margin-right: 20px">
            <div class="prd-item">
                <div class="img">
                    <a href="/user-board/review-board-detail/${review.id}">
                        <img
                            src="${filePath}"
                        />
                    </a>
                </div>
                <div class="timer time-desc">
                    <span class="timer-title">
                        ${review.boardTitle}
                    </span>
                </div>
                <div class="desc-bottom">
                    <div class="top">
                        <em class="imgbadge-dlv">
                            <span>${review.boardRegisterDate}</span>
                        </em>
                        <div class="rating-simply">`
    for (let i = 0; i < review.reviewRating; i++) {
        text += `<span class="rating-star"></span>`;
    }

    text += `</div>
                    </div>
                    <p class="tit">
                        <a
                            href="/user-board/review-board-detail/${review.id}"
                            class="text-elps2">
                            ${review.boardContent}
                        </a>
                    </p>
                    <div class="price-info">
                        <span class="price">
                            <em class="num">${review.memberDTO.userId}</em>
                        </span>
                    </div>
                </div>
            </div>
        </li>
    `;

    $(".swiperDataContainer").eq(1).append(text);
}

topSale.forEach((sub, i) => {
    let text;

    let filePath = "/img/welfare/welfare_img_default.png";

    if (sub.representFood != null) filePath = FILE_DISPLAY_URL + sub.representFood.filePath + '/t_' + sub.representFood.fileUuid + '_' + sub.representFood.fileOrgName;

    text = `
        <li
            class="ext-li colum swiper-slide swiper-slide-active"
            style="width: 256.25px; margin-right: 25px"
        >
            <div class="prd-item type-sm">
                <div class="img w200">
                    <a href="/welfare/detail/${sub.id}">
                        <img src="${filePath}" class="height256"/>
                    </a>
                </div>
                <div class="desc-bottom">
                    <div class="top">
                        <em class="imgbadge-dlv">
                            <span>정기배송</span>
                        </em>
                        <div class="rating-simply">
                            <span class="score">주문수 : ${sub.orderCount}건</span>
                        </div>
                    </div>

                    <p class="tit">
                        <a
                            href="javascript:void(0)"
                            class="text-elps2"
                            >${sub.welfareName} | ${sub.subscriptionTitle}</a
                        >
                    </p>
                    <span class="price"
                        ><em class="num">26,450</em>원
                    </span>
                </div>
                <!--// desc-bottom -->
            </div>
        </li>
    `;

    $(".swiperDataContainer").eq(2).append(text);
});

const $recentSubUl = $(".recentSub");
recentSub.forEach((sub, i) => {
    let text;

    let filePath = "/img/welfare/welfare_img_default.png";

    if (sub.representFood != null) filePath = FILE_DISPLAY_URL + sub.representFood.filePath + '/t_' + sub.representFood.fileUuid + '_' + sub.representFood.fileOrgName;

    text =
        `
        <li
            class="bnr-item-slide swiper-slide swiper-slide-active"
            style="width: 256.25px; margin-right: 25px"
        >
            <a class="bnr-item" href="/welfare/detail/${sub.id}">
                <div class="img">
                    <img src='${filePath}'/>
                </div>
                <em class="tit text-elps">${sub.subscriptionTitle}</em>
                <span class="desc"">${sub.welfareName}</span>
            </a>
        </li>
    `

    $recentSubUl.append(text);
});

let $donationWrap = $(".donationWrap");

donation.forEach(board => {

    let text;

    let filePath = "/img/welfare/welfare_img_default.png";

    if (board.donationBoardFiles.length > 0) {
        filePath = FILE_DISPLAY_URL + board.donationBoardFiles[0].filePath + '/t_' + board.donationBoardFiles[0].fileUuid + '_' + board.donationBoardFiles[0].fileOrgName;
    }

    text = `
        <li class="swiper-slide" style="width: 340px">
            <div class="best-list-item">
                <a href="/user-board/donate-detail/${board.id}">
                    <img src="${filePath}"/>
                </a>
                <div class="txt-area">
                    <p class="slogan text-elps">${board.welfareName}</p>
                    <strong class="tit text-elps">${board.boardTitle}</strong>
                </div>
            </div>
            <!--// brand-list-item -->
        </li>
    `

    $donationWrap.append(text);
});
