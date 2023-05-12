// 임시로 8개 넣어둠
for (let i = 0; i < 7; i++) {
    // 임시 함수들
    appendIndex1();
    appendIndex2();
    appendIndex3();
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

function appendIndex2() {
    let text;

    text = `
        <li
            class="ext-li colum swiper-slide swiper-slide-active"
            style="width: 256.25px; margin-right: 25px"
        >
            <div class="prd-item type-sm">
                <div class="img w200">
                    <a href="/product/view?productCd=9005&amp;RC_Rview">
                        <img
                            class="lozad"
                            src="https://file.rankingdak.com/image/RANK/PRODUCT/PRD001/20230405/IMG1680Lig659554866_330_330.jpg"
                        />
                    </a>
                </div>
                <div class="desc-bottom">
                    <div class="top">
                        <em class="imgbadge-dlv">
                            <span>정기배송</span>
                        </em>
                        <div class="rating-simply">
                            <span class="rating-star"></span>
                            <span class="score">4.8</span>
                            <span class="total-num">5</span>
                        </div>
                    </div>

                    <p class="tit">
                        <a
                            href="javascript:void(0)"
                            class="text-elps2"
                            >맛있닭 NEW 더담은 닭가슴살 도시락 시즌2
                            다섯가지나물밥 &amp; 한입소스닭가슴살 짬뽕
                            275g</a
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
}

function appendIndex3() {
    let text;

    text = `
        <li
            class="ext-li colum swiper-slide swiper-slide-active"
            style="width: 256.25px; margin-right: 25px"
        >
            <div class="prd-item type-sm">
                <div class="img w200">
                    <a href="/product/view?productCd=9005&amp;RC_Rview">
                        <img
                            class="lozad"
                            src="https://file.rankingdak.com/image/RANK/PRODUCT/PRD001/20230405/IMG1680Lig659554866_330_330.jpg"
                        />
                    </a>
                </div>
                <div class="desc-bottom">
                    <div class="top">
                        <em class="imgbadge-dlv">
                            <span>정기배송</span>
                        </em>
                        <div class="rating-simply">
                            <span class="rating-star"></span>
                            <span class="score">4.8</span>
                            <span class="total-num">5</span>
                        </div>
                    </div>

                    <p class="tit">
                        <a
                            href="javascript:void(0)"
                            class="text-elps2"
                            >맛있닭 NEW 더담은 닭가슴살 도시락 시즌2
                            다섯가지나물밥 &amp; 한입소스닭가슴살 짬뽕
                            275g</a
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

    $(".swiperDataContainer").eq(3).append(text);
}

const $recentSubUl = $(".recentSub");
recentSub.forEach((sub, i) => {
    let text = "";

    let filePath = "";

    console.log(sub.representFood);

    if(sub.representFood != null) filePath = sub.representFood.filePath + '/t_' + sub.representFood.fileUuid + '_' + sub.representFood.fileOrgName;

    text +=
        `
        <li
            class="bnr-item-slide swiper-slide swiper-slide-active"
            style="width: 256.25px; margin-right: 25px"
        >
            <div class="bnr-item">
                <div class="img">
                    <img src='${'/images/display/' + filePath}'/>
                </div>
                <em class="tit text-elps">${sub.subscriptionTitle}</em>
                <span class="desc"">${sub.welfareName}</span>
                <a href="/product/view?productCd=24931" class="btn-blank"></a>
            </div>
        </li>
    `

    $recentSubUl.append(text);
})
