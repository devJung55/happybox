/* 라이더 리스트 뿌리기 */

const $list = $('.productCart-list');

let $page;

const URL = "/mypage/welfare/rider/list"

const doSearch = (page) => {

    let pageNum;

    if (page) {
        pageNum = $(page).data("page");
        $page = pageNum;
    }

    $doAjax("GET", URL, {page: $page}, (result) => {
        console.log(result);
        $itemCount.text(result.totalElements);

        // 결과 append 전 내용 비우기
        $itemWrap.empty();

        // 결과 append
        result.content.forEach((e) => showList(e));

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
        paging += `<a class="changePage" data-page="${startPage}" 
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


function showList(riderDTO) {

    let text = "";
    text = `
                    <li id="delivery-product-2541529900N" class="delivery-product-2541529900NY"><!-- // 상품 리스트 -->
                        <div class="prd-info-area productCart-info-area">
                            <div class="inner">
                                <div class="user__img">
                                    <img src="/img/mypage/user__profile.jpg">
                                </div>
                                <div class="user__info">
                                    <div class="user__name rider__name">
                                        <span>${riderDTO.riderName}</span>
                                        <span class="badge-sm-primary delivery__done">${riderDTO.deliveryStatus}</span>
                                    </div>
                                    <div class="rider__phoneNumber welfare__name">
                                        <span>${riderDTO.riderPhoneNumber}</span>
                                    </div>
                                </div>
                            </div>
                            <div class="column btn">
                                <button type="button" class="btn-x-sm deleteUserCart">
                                    <i class="ico-x-black"></i>
                                </button>
                            </div>  
                        </div>
                    </li> 
                `
    $list.append(text);
}