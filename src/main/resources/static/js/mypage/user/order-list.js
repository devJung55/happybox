/* order-list.html */

/* 기간 클릭 이벤트 */
const $radios = $(".custom-radio");
let text = "";

$radios.on("click", function() {
    $radios.each((i, radio) => {
        if($(radio).hasClass("default")) {
            $(radio).removeClass("default");
            text = $($(this).children()[1]).text();
        }
    });
    
    if(!$(this).hasClass("default")) {
        $(this).addClass("default");
    }
});

/* 초기화 클릭 이벤트 */
const $reset = $(".reset");

$reset.on("click", function() {
    $radios.each((i, radio) => {
        if($(radio).hasClass("default")) {
            $(radio).removeClass("default");
        }
    });
    $(".select__all").addClass("default");
});


/*-----------------------------------------------------------------*/


myPageService.orderListAjax();

const $orderAppend = $(".productCart-list");
let page = 0;

function showOrderList(orderList) {
    let text ="";
    let total = "";
    let img = "";

    if(orderList.content.length == 0) {
        text = `
                <div class="no-data-type1">
                    <p class="message">기간 내에 주문내역이 없습니다.</p>
                </div>
            `;
        $orderAppend.append(text);
    }
    orderList.content.forEach(order => {
        const formattedDate = formatDate(new Date(order.createdDate));
        total = order.productPrice * order.orderStock;

        if(order.productFiles.length != 0) {
            for (let i = 0; i < order.productFiles.length; i++) {
                img = `
                    <img class="board-image" src="/image/display?fileName=${order.productFiles[0].filePath}/${order.productFiles[0].fileUuid}_${order.productFiles[0].fileOrgName}">
                `;
            }
        } else {
            img = "";
        }

        text += `
            <li id="delivery-product-2541529900N" class="delivery-product-2541529900NY">
                <div class="prd-info-area productCart-info-area">
                    <div class="inner">
                        <div class="column img">
                            <label for="check-product-25415N">
                                
                `;

        text += img;

        text += `
                            </label>
                        </div>
                        <div class="column tit">
                            <p class="tit">
                                <a href="javascript:void(0)" class="productNm">${order.productName}</a>
                                <div>
                                    <a href="javascript:void(0)" class="productNm">${formattedDate}</a>
                                </div>
                            </p>
                        </div>
                        <div class="column price">
                            <div class="price-div">
                                <span class="order__count">${order.orderStock}</span>개 /
                                <span class="num productCart-price-25415 brand-cd-1042 partner-cd-299 productCart-price-25415" id="productCart-price-20230418000014810723">${total}</span>원
                            </div>
                        </div>
                    </div>
                </div>
            </li>
                `;

    });
    $orderAppend.append(text);
    displayPaginationOrder(orderList.totalPages);
}

function displayPaginationOrder(totalPages) {
    const $pagination = $(".pagination");
    $pagination.empty();

    if (page > 0) {
        $pagination.append(`<a href="javascript:void(0)" class="btn-page prev"><span class="blind2">&lt;</span></a>`);
    }

    for (let i = 1; i <= totalPages; i++) {
        if (i === page + 1) {
            // 현재 페이지를 텍스트로 표시
            $pagination.append(`<a href="javascript:void(0)" id="prev" class="arrow current"><span>${i}</span></a>`);
        } else {
            // 다른 페이지는 a 태그로 표시
            $pagination.append(`<a href="#" class="current"><span>${i}</span></a>`);
        }
    }

    if (page < totalPages - 1) {
        $pagination.append(`<a href="javascript:void(0)" id="next" class="arrow btn-page next"><span class="blind2">&gt;</span></a>`);
    }
}

$(".pagination").on("click", "a", function(e) {
    e.preventDefault();
    const targetPage = $(this).text();
    page = parseInt(targetPage);
    $orderAppend.empty();
    myPageService.orderListAjax(page);
});


/*----------------------------------------------------------------- 기간별 조회*/


let date;

function getOrdersByPeriod(date) {
    let now = new Date();   // 현재 날짜
    let setDate = new Date(now.getTime() - (date * 24 * 60 * 60 * 1000));    // 사용자가 설정한 날짜 구하기

    console.log(date)
    if(date == 1) {
        $orderAppend.empty();
        myPageService.orderListAjax();
    }

    let searchDateDTO = {
        "year": setDate.getFullYear(),
        "month": setDate.getMonth() + 1,
        "day": setDate.getDate()
    }
    $orderAppend.empty();
    myPageService.orderListBySearchDate(searchDateDTO);
}

$(".searchButton").on("click", function() {
    $(".custom-radio").each((i, v) => {
        if($(v).hasClass("default")) {
            date = $($(v).children()[1]).text();
            console.log(date)
            switch (text) {
                case '2주일':
                    date = 14;
                    break;
                case '1개월':
                    date = 30;
                    break;
                case '3개월':
                    date = 90;
                    break;
                case '6개월':
                    date = 180;
                    break;
                case '9개월':
                    date = 270;
                    break;
                case '12개월':
                    date = 365;
                    break;
                case '전체조회':
                    date = 1;
                    break;
                default:
                    date = null;
                    break;
            }
        }
    })
    getOrdersByPeriod(date);
});
