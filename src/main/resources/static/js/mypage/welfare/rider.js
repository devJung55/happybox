/* 라이더 리스트 뿌리기 */
function $doAjax(type, url, data, callback) {
    $.ajax({
        type: type,
        url: url,
        data: data,
        dataType: "json",
        success: function (response) {
            if (callback) {
                callback(response);
            }
        }
    });
}


const $list = $('.productCart-list');
const PAGE_AMOUNT = 7;
let $page;

const URL = "/mypage/welfare/getList"

const doSearch = (page) => {

    let pageNum;

    if (page) {
        pageNum = $(page).data("page");
        $page = pageNum;
    }

    $doAjax("GET", URL, {page: $page}, (result) => {
        console.log(result);
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
    let filePath = riderDTO.filePath;
    let fileUuid = riderDTO.fileUuid;
    let fileOrgName = riderDTO.fileOrgName;
    let repFilePath = "/img/market/no_img_market.png";
    let status = riderDTO.deliveryStatus;
    if(status == "COMPLETED"){
        status = "배달 완료";
    }else if(status == "INCOMPLETED"){
        status = "배달 대기";
    }else {
        status = "배달중";
    }

    if(filePath != null) {
        repFilePath = "/image/display?fileName=" + filePath + '/t_' + fileUuid + '_' + fileOrgName;
    }
    text = `
                    <li id="delivery-product-2541529900N" class="delivery-product-2541529900NY"><!-- // 상품 리스트 -->
                        <div class="prd-info-area productCart-info-area">
                            <div class="inner">
                                <div class="user__img">
                                    <img src="${repFilePath}">
                                </div>
                                <div class="user__info">
                                    <div class="user__name rider__name">
                                        <span>${riderDTO.riderName}</span>
                                        <span class="badge-sm-primary delivery__done">${status}</span>
                                    </div>
                                    <div class="rider__phoneNumber welfare__name">
                                        <span>${riderDTO.riderPhoneNumber}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li> 
                `
    $list.append(text);
}