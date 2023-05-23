const $table = $(".info-table table");
let currentPage = 1;
const PAGE_AMOUNT = 5;

function doSearch(btn) {

    if(btn) {
        currentPage = $(btn).data("page");
    }

    $.ajax({
        type: "GET",
        url: `/admin/distributor/products/list/${$distributor.id}`,
        data: {page : currentPage},
        dataType: "json",
        success: function (products) {
            $table.empty();

            appendTableHeader();
            products.content.forEach(product => appendList(product));

            showPage(products);
        }
    });
}

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
        paging += `<a class="page-button" data-page="${startPage}" 
                        onclick="doSearch(this)"><span><</span></a>`;
    }

    for (let i = startPage + 1; i < endPage + 1; i++) {
        let page = i;
        if (pageNumber + 1 != page) {
            paging += `<a class="page-button" data-page="${page}" onclick="doSearch(this)"><span>${page}</span></a>`;
        } else {
            paging += `<span class="page-button-active page-button">${page}</span>`;
        }
    }
    if (hasNext) {
        paging += `<a class="right-button" data-page="${endPage + 1}" onclick="doSearch(this)"><span>></span></a>`
    }

    $(".page-button-box-layout").html(paging);
}

function appendList(product) {
    let text;

    text = `
            <tr class="product__tr" data-productid="${product.id}" onclick="adminService.productDetail(this)">
                <td>
                    <input type="checkbox" name="check">
                </td>
                <td class="show-modal">${product.id}</td>
                <td class="show-modal">${product.productName}</td>
                <td class="show-modal">${product.productPrice}</td>
                <td class="show-modal">${product.productStock}</td>
            </tr>
    `;

    $table.append(text);
}

function appendTableHeader() {
    let text = `
        <thead>
            <tr>
                <th>
                    <input type="checkbox" id="allSelect">
                </th>
                <th>No</th>
                <th>메뉴</th>
                <th>가격</th>
                <th>수량</th>
            </tr>
        </thead>
    `;

    $table.append(text);
}

/* 페이지 이동시 바로 실행 */
doSearch();