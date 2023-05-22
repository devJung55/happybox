/* sales-list.html */

/* 기간 클릭 이벤트 */
const $radiosSales = $(".custom-radio");
let val = "";

$radiosSales.on("click", function() {
    $radiosSales.each((i, radio) => {
        if($(radio).hasClass("default")) {
            $(radio).removeClass("default");
            val = $($(this).children()[1]).text();
        }
    });

    if(!$(this).hasClass("default")) {
        $(this).addClass("default");
    }
});

/* 초기화 클릭 이벤트 */
const $resetSales = $(".reset");

$resetSales.on("click", function() {
    dateSales = 0;

    $radiosSales.each((i, radio) => {
        if($(radio).hasClass("default")) {
            $(radio).removeClass("default");
        }
    });
    $(".select__all").addClass("default");
});


/* 주문 회원정보 상세보기 모달창 */

const $modal = $(".layer-wrap");
const $userDetailButton = $(".user__name");
const $closeButton = $(".btn-x-md2");

$userDetailButton.on("click", function() {
    if($modal.css("display") == "none") {
        $modal.css("display", "block");
    }
});

$closeButton.on("click", function() {
    if($modal.css("display") == "block") {
        $modal.css("display", "none");
    }
});


/*----------------------------------------------------*/

const $salesUl = $(".sale__list");
myPageDistributorService.saleListAjax();

function showSaleList(sales) {
    let text = "";

    sales.content.forEach(product => {
        let img = "";
        let totalPrice = product.orderStock * product.productPrice;

        text += `
            <li id="delivery-product-2541529900N" class="delivery-product-2541529900NY">
                <div class="prd-info-area productCart-info-area">
                    <div class="inner">
                        <div class="column img">
                            <label for="check-product-25415N">
                `;

            for (let i = 0; i < 1; i++) {
                if(product.productFiles.length != 0) {
                    img = `<img src="/image/display?fileName=${product.productFiles[i].filePath}/${product.productFiles[i].fileUuid}_${product.productFiles[i].fileOrgName}">`;
                } else {
                    img = `<img src="https://us.123rf.com/450wm/mathier/mathier1905/mathier190500002/134557216-%EC%8D%B8%EB%84%A4%EC%9D%BC-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%97%86%EC%9D%8C-%ED%8F%AC%EB%9F%BC-%EB%B8%94%EB%A1%9C%EA%B7%B8-%EB%B0%8F-%EC%9B%B9%EC%82%AC%EC%9D%B4%ED%8A%B8%EC%9A%A9-%EC%9E%90%EB%A6%AC-%ED%91%9C%EC%8B%9C%EC%9E%90.jpg?ver=6">`;
                }
            }

        text += img;

        text += `
                            </label>
                        </div>
                        <div class="column tit">
                            <p class="tit" style="margin-top: 8px;">
                                <div class="user__profile__wrap">
                                    <a class="user__name" href="javascript:void(0)">${product.memberName}</a>
                                </div>
                                <a href="javascript:void(0)" class="productNm" style="display: block;">${product.productName}</a>
                            </p>
                        </div>
                        <div class="column price">
                            <div class="price-div">
                                <span class="order__count">${product.orderStock}</span>개 /
                                <span class="num productCart-price-25415 brand-cd-1042 partner-cd-299 productCart-price-25415" id="productCart-price-20230418000014810723">${totalPrice}</span>원
                            </div>
                        </div>
                    </div>
                </div>
            </li>
        `;
    });
    $salesUl.empty();
    $salesUl.append(text);
}



