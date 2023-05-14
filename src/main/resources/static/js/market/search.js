const $itemWrap = $(".show-item-wrap");
const $itemCount = $("#item-count");
const SEARCH_URL = "/product/search";
const PAGE_AMOUNT = 7;

const searchProduct = {
    address: null,
    price: null,
    name: null,
    productCategory: null,
    productSearchOrder: null
};

const category = {
    VEGETABLES: "야채",
    FRUITS: "과일",
    SEAFOOD: "해산물",
    MEAT: "육류",
    DAIRY: "유제품",
    SPICES: "양념",
    OTHER: "기타"
}

const doSearch = (page) => {

    let pageNum;

    if(page) {
        pageNum = $(page).data("page");
        searchProduct.page = pageNum;
    }

    $doAjax("GET", SEARCH_URL, searchProduct, (result) => {
        console.log(result);
        $itemCount.text(result.totalElements);

        // 결과 append 전 내용 비우기
        $itemWrap.empty();

        // 결과 append
        result.content.forEach((e) => appendList(e));

        // 페이징 버튼 생성
        showPage(result);
    });
}

// 페이지 로딩 시 검색 요청
doSearch();

function showPage(result) {
    let pageable = result.pageable;
    let pageNumber = pageable.pageNumber;
    let count = Math.floor(pageNumber / PAGE_AMOUNT);
    let startPage = count * PAGE_AMOUNT;
    let endPage = startPage + PAGE_AMOUNT;

    endPage = endPage > result.totalPages ? result.totalPages : endPage;

    let hasPrev = startPage > 1;
    let hasNext = endPage < result.totalPages;

    // 페이징 HTML 태그를 추가하는 코드 작성
    let paging = "";
    paging += `<div class="paging" style="text-align: center">`;

    if (hasPrev) {
        paging += `<a class="changePage" data-page="${startPage - 1}" 
                        onclick="doSearch(this)"><span><</span></a>`;
    }
    for (let i = startPage + 1; i < endPage + 1; i++) {
        let page = i;
        if (pageNumber + 1 != page) {
            paging += `<a class="changePage" data-page="${page}" onclick="doSearch(this)"><span>${page}</span></a>`;
        } else {
            paging += `<span id="currentPage">${page}</span>`;
        }
    }
    if (hasNext) {
        paging += `<a class="changePage" data-page="${endPage + 1}" onclick="doSearch(this)"><span>></span></a>`
    }

    $('.paging-div').html(paging);
}

function appendList(product) {
    let text;

    let filePaths = product.productFileDTOS;
    let repFilePath = "/img/market/no_img_market.png";

    if(filePaths.length != 0) {
        repFilePath = filePaths[0].filePath + '/t_' + filePaths[0].fileUuid + '_' + filePaths[0].fileOrgName
    }

    text = `
        <div
            class="bnr-item-slide swiper-slide swiper-slide-active"
            style="width: 256.25px"
        >
            <a href="/product/detail/${product.id}">
                <div class="image-container">
                    <img src="${repFilePath}"/>
                </div>
            </a>
            <div class="product-info">
                <span class="distributer-name">${product.distributorName}</span>
                <h3 class="product-name">[${category[product.productCategory]}] ${product.productName}</h3>
                <div class="content-row">
                    <span class="sales-price">
                        ${product.productPrice}<span class="won">원</span>
                    </span>
                </div>
                <div class="review-count">
                    후기<span class="review-number">${product.productReplyCount}</span> 개
                </div>
            </div>
        </div>
    `

    $itemWrap.append(text);
}