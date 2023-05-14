const $itemWrap = $(".show-item-wrap");
const $itemCount = $("#item-count");
const SEARCH_URL = "/product/search";
const PAGE_AMOUNT = 5;

// private String address;
// private Integer price;
// private String name;
// private ProductCategory productCategory;
// private ProductSearchOrder productSearchOrder;

const searchProduct = {
    address: "",
    price: 0,
    name: "",
    productCategory: "",
    productSearchOrder: ""
};

const category = {
    VEGETABLES: "VEGETABLES",
    FRUITS: "FRUITS",
    SEAFOOD: "SEAFOOD",
    MEAT: "MEAT",
    DAIRY: "DAIRY",
    SPICES: "SPICES",
    OTHER: "OTHER"
}

const searchOrder = {
    DATE_DESC: "DATE_DESC",
    ORDER_COUNT_DESC: "ORDER_COUNT_DESC",
    PRICE_ASC: "PRICE_ASC",
    PRICE_DESC: "PRICE_DESC"
}

// 페이지 로딩 시 검색 요청
$doAjax("GET", SEARCH_URL, {/*page: 3*/}, (result) => {
    console.log(result);
    $itemCount.text(result.totalElements);
    result.content.forEach((e) => appendList(e));
    showPage(result);
});

function showPage(result) {
    let pageable = result.pageable;
    let pageNumber = pageable.pageNumber;
    let count = Math.floor(pageNumber / PAGE_AMOUNT);
    // 7 page / 5 -> floor(1.4) = 1 -> 1 * 5 + 1 -> startPage = 6
    let startPage = count * PAGE_AMOUNT;
    let endPage = startPage + PAGE_AMOUNT;

    endPage = endPage > result.totalPages ? result.totalPages : endPage;

    console.log("end page : " + endPage);

    let hasPrev = startPage > 1;
    // 170 page / 5 = 24 -> 171 /
    let hasNext = endPage < result.totalPages;

    // 페이징 HTML 태그를 추가하는 코드 작성
    let paging = "";
    paging += `<div class="paging" style="text-align: center">`;

    if (hasPrev) {
        paging += `<a class="changePage" data-page="${pageNumber}" 
                        onclick="preventDefault(this)"><span><</span></a>`;
    }
    for (let i = startPage + 1; i < endPage + 1; i++) {
        let page = i;
        if (pageNumber + 1 != page) {
            paging += `<a class="changePage" data-page="${page}" onclick="findPage(this)"><span>${page}</span></a>`;
        } else {
            paging += `<span id="currentPage">${page}</span>`;
        }
    }
    if (hasNext) {
        paging += `<a class="changePage" data-page="${endPage}" onclick="findPage(this)"><span>></span></a>`
    }

    // paging += `
    //                 </div>
    //                 <form action="/storages/search" method="get" name="pageForm">
    //                     <input type="hidden" name="page" value="${result.paginationDTO.pageDTO.criteria.page}">
    //                     <input type="hidden" name="amount" value="${result.paginationDTO.pageDTO.criteria.amount}">
    //                 </form>
    //             `

    $('.paging-div').html(paging); // 생성된 HTML 태그를 product 클래스를 가진 DOM에 추가합니다.
}

function appendList(product) {
    let text;

    text = `
        <div
            class="bnr-item-slide swiper-slide swiper-slide-active"
            style="width: 256.25px"
        >
            <a href="javascript:void(0)">
                <div class="image-container">
                    <img
                            src="https://img-cf.kurly.com/shop/data/goods/1656038418331l0.jpg"
                    />
                </div>
            </a>
            <div class="product-info">
                <span class="distributer-name">김정표</span>
                <h3 class="product-name">[쟈뎅] 시그니처 보틀 커피 1.1L 3종</h3>
                <div class="content-row">
                    <span class="sales-price">
                        2,900<span class="won">원</span>
                    </span>
                </div>
                <div class="review-count">
                    후기<span class="review-number">999+</span>
                </div>
            </div>
        </div>
    `

    $itemWrap.append(text);
}