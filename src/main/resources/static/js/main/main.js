// 임시로 8개 넣어둠
for (let i = 0; i < 7; i++) {
    // 임시 함수들
    appendIndex1();
    // appendIndex3();
}

function appendIndex1() {
    let text;

    text = `
            <li
            class="swiper-slide"
            style="width: 320px; margin-right: 20px">
            <div class="prd-item">
                <div class="img">
                    <a href="javascript:void(0)">
                        <img
                            src="https://file.rankingdak.com/image/RANK/PRODUCT/MOBILE/20230413/IMG1681JtI348145628.jpg"
                        />
                    </a>
                </div>
                <div class="timer time-desc">
                    <span class="timer-title">
                        노원구 복지관 리뷰
                    </span>
                </div>
                <div class="desc-bottom">
                    <div class="top">
                        <em class="imgbadge-dlv">
                            <span>정기배송</span>
                        </em>
                        <div class="rating-simply">
                            <span class="rating-star"></span>
                            <span class="score">0</span>
                            <span class="total-num">5</span>
                        </div>
                    </div>
                    <p class="tit">
                        <a
                            href="/product/view?productCd=F000005527"
                            class="text-elps2">
                            노원구 복지관 최고네요!
                            </a
                        >
                    </p>
                    <div class="price-info">
                        <span class="sale"><strong>58</strong>%</span>
                        <span class="price">
                            <em class="num">19,200</em>원
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

    if(sub.representFood != null) filePath = '/images/display/' +  sub.representFood.filePath + '/t_' + sub.representFood.fileUuid + '_' + sub.representFood.fileOrgName;

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

    if(sub.representFood != null) filePath = '/images/display/' +  sub.representFood.filePath + '/t_' + sub.representFood.fileUuid + '_' + sub.representFood.fileOrgName;

    text =
        `
        <li
            class="bnr-item-slide swiper-slide swiper-slide-active"
            style="width: 256.25px; margin-right: 25px"
        >
            <div class="bnr-item">
                <div class="img">
                    <img src='${filePath}'/>
                </div>
                <em class="tit text-elps">${sub.subscriptionTitle}</em>
                <span class="desc"">${sub.welfareName}</span>
                <a href="/product/view?productCd=24931" class="btn-blank"></a>
            </div>
        </li>
    `

    $recentSubUl.append(text);
});

let $donationWrap = $(".donationWrap");

donation.forEach(board => {
    let text;
    let filePath = '/images/display/' +  board.boardFiles[0].filePath + '/t_' + board.boardFiles[0].fileUuid + '_' + board.boardFiles[0].fileOrgName;

    text = `
        <li class="swiper-slide" style="width: 340px">
            <div class="best-list-item">
                <a href="/display/brand/view?brandCd=1042">
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
