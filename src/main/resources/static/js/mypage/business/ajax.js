let myPageDistributorService = (function() {
    function inquiryListAjax(page) {
        $.ajax({
            url: "/mypage/distributor/inquiry-list",
            data: {"page": page},
            success: function(inquiries) {
                showInquiryList(inquiries);
                pagination(inquiries);
            }
        })
    }

    function productListAjax(page) {
        $.ajax({
            url: "/mypage/distributor/product-list",
            data: {"page": page},
            success: function(products) {
                showProductList(products)
                pagination(products);
            }
        })
    }

    function saleListAjax(page) {
        $.ajax({
            url: "/mypage/distributor/sale-list",
            data: {"page": page},
            success: function(sales) {
                showSaleList(sales)
                pagination(sales);
            }
        })
    }

    function saleListSearchDateAjax(page) {
        console.log(globalThis.searchDateDTO);
        $.ajax({
            url: "/mypage/distributor/sale-list",
            data: {"page": page, "searchDateDTO": globalThis.searchDateDTO},
            success: function(sales) {
                console.log(sales);
                showSaleList(sales)
                paginationSearchDate(sales);
            }
        })
    }

    return {
        inquiryListAjax: inquiryListAjax,
        productListAjax: productListAjax,
        saleListAjax: saleListAjax,
        saleListSearchDateAjax: saleListSearchDateAjax
    }
}());


/* 페이지 처리 */
globalThis.page = 1;
globalThis.searchDateDTO = {};

function setPage(page) {
    globalThis.page = page;
    let url = window.location.pathname;

    if(url == "/mypage/distributor/inquiry") {
        myPageDistributorService.inquiryListAjax(page);
    } else if(url == "/mypage/distributor/product") {
        myPageDistributorService.productListAjax(page);
    } else if(url == "/mypage/distributor/sale") {
        myPageDistributorService.saleListAjax(page);
    }
}

function setPageSearchDate(page) {
    globalThis.page = page;
    myPageDistributorService.saleListSearchDateAjax(page);
}

/* 페이지 버튼 append 할 곳 */
const $contentWrap = $('.pagination');

function pagination(get) {
    let page = get.pageable;
    /* 현재 페이지 */
    let Num = page.pageNumber;

    let count = Math.floor( Num / PAGE_AMOUNT);
    /* 시작 페이지 */
    let startPage = count * PAGE_AMOUNT;
    /* 끝 페이지 */
    let endPage = startPage + PAGE_AMOUNT;

    endPage = endPage > get.totalPages ? get.totalPages : endPage;

    let hasPrev = startPage > 1;
    let hasNext = endPage < page.totalPages;

    /* 페이지 버튼 추가하는 HTML 코드 작성부 */
    let paging =  "";

    if (page.length == 0) {
        paging = "";
    } else {
        paging = `
            <!-- // 페이징 -->
                <div class="pagination mt20" style="margin-top: 30px;">
                `
        if(hasPrev) {
            paging += ` <a class="btn-page prev" data-page="${startPage}" href="javascript:setPage(${startPage})"><span class="blind">&lt;</span></a> `
        }

        for (let i = startPage + 1; i < endPage + 1; i++) {
            let page = i
            if (Num + 1 != page) {
                paging += `<a class="change-page" data-page="${page}" href="javascript:setPage(${page})"><span>${page}</span></a> `
            } else {
                paging += ` <a class="current"><span>${page}</span></a>`
            }
        }

        if (hasNext) {
            paging +=`<a class="btn-page next" data-page="${endPage + 1}" href="javascript:setPage(${endPage + 1})"><span class="blind">&gt;</span></a>`
        }
        paging += `
            </div>
        `;
        $contentWrap.html(paging);
    }
}


function paginationSearchDate(get) {
    let page = get.pageable;
    /* 현재 페이지 */
    let Num = page.pageNumber;

    let count = Math.floor( Num / PAGE_AMOUNT);
    /* 시작 페이지 */
    let startPage = count * PAGE_AMOUNT;
    /* 끝 페이지 */
    let endPage = startPage + PAGE_AMOUNT;

    endPage = endPage > get.totalPages ? get.totalPages : endPage;

    let hasPrev = startPage > 1;
    let hasNext = endPage < page.totalPages;

    /* 페이지 버튼 추가하는 HTML 코드 작성부 */
    let paging =  "";

    if (page.length == 0) {
        paging = "";
    } else {
        paging = `
            <!-- // 페이징 -->
                <div class="pagination mt20" style="margin-top: 30px;">
                `
        if(hasPrev) {
            paging += ` <a class="btn-page prev" data-page="${startPage}" href="javascript:setPageSearchDate(${startPage})"><span class="blind">&lt;</span></a> `
        }

        for (let i = startPage + 1; i < endPage + 1; i++) {
            let page = i
            if (Num + 1 != page) {
                paging += `<a class="change-page" data-page="${page}" href="javascript:setPageSearchDate(${page})"><span>${page}</span></a> `
            } else {
                paging += ` <a class="current"><span>${page}</span></a>`
            }
        }

        if (hasNext) {
            paging +=`<a class="btn-page next" data-page="${endPage + 1}" href="javascript:setPageSearchDate(${endPage + 1})"><span class="blind">&gt;</span></a>`
        }
        paging += `
            </div>
        `;
        $contentWrap.html(paging);
    }
}


/*----------------------------------------------------------------- 기간별 조회*/


let dateSales;

function getSalesByPeriod(dateSales) {
    let now = new Date();                                                               // 현재 날짜
    let setDate = new Date(now.getTime() - (dateSales * 24 * 60 * 60 * 1000));    // 사용자가 설정한 날짜 구하기

    if(dateSales == 1) {
        $salesUl.empty();
        myPageDistributorService.saleListAjax();
    }

    globalThis.searchDateDTO = {
        "year": setDate.getFullYear(),
        "month": setDate.getMonth() + 1,
        "day": setDate.getDate()
    }
    $salesUl.empty();
    myPageDistributorService.saleListSearchDateAjax(globalThis.page);
}


$(".searchButton__sales").on("click", function() {
    $(".custom-radio").each((i, v) => {
        if($(v).hasClass("default")) {
            dateSales = $($(v).children()[1]).text();
            switch (val) {
                case '2주일':
                    dateSales = 14;
                    break;
                case '1개월':
                    dateSales = 30;
                    break;
                case '3개월':
                    dateSales = 90;
                    break;
                case '6개월':
                    dateSales = 180;
                    break;
                case '9개월':
                    dateSales = 270;
                    break;
                case '12개월':
                    dateSales = 365;
                    break;
                case '전체조회':
                    dateSales = 1;
                    break;
                default:
                    dateSales = null;
                    break;
            }
        }
    })
    getSalesByPeriod(dateSales);
});