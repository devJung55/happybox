/* Welfare List */

$(function () {
    // 지역별 정렬 버튼
    $('.category-btn').on('click', function () {
        $('.category-btn').removeClass('selected');
        $(this).addClass('selected');
    });

    // 전체 버튼
    $('.category-btn:first-child').on('click', function () {
        $('.category-btn').removeClass('selected');
        $(this).addClass('selected');
    });
});

//  전체 버튼 Default
$(function () {
    $('.sorting-search-box .category-btn:first-child').addClass('selected');
});


/* ================================================================================================================================================ */

/* 인기 복지관 */
let $orderCount = $(".swiper-wrapper");
/* 이번달 최신 복지관 */
let $monthlySub = $(".welfare-list-ul");
let $thisMonthSub = $monthlySub.eq(0);
/* 저번 달 최신 복지관 */
let $likeCountSub = $monthlySub.eq(1);

orderCountSubs.forEach(sub => showList(sub, $orderCount));
thisMonthSubs.forEach(sub => showBoxList(sub, $thisMonthSub));
likeCountSubs.forEach(sub => showBoxList(sub, $likeCountSub));

/* 지역검색버튼 */
const $searchLocation = $(".sorting-search-box .inner button[type='button']");
/* 검색결과 */
const $welfareList = $('.welfare-list-wrap');
/* 검색바 */
const $searchForm = $("#searchForm");
const $input = $(".input-group input[type='text']");
const $inputReset = $(".btn-search");
/* 검색조건 */
let subSearch = {
    searchFirstAddress: null,
    searchText: null
}

$searchForm.on("submit", function (e) {
    e.preventDefault();

    let searchText = $input.val() == "" ? null : $input.val();

    subSearch.searchText = searchText;

    doSearch();
});

/* 검색바 초기화 */
$inputReset.on("click", function () {
    $input.val("");
    subSearch.searchText = null;
    doSearch();
});

/* 지역 검색 */
const LOCATIONS = {
    전체: null,
    강남구: "강남구",
    송파구: "송파구",
    서초구: "서초구",
    강동구: "강동구",
    광진구: "광진구",
    성동구: "성동구",
    동대문구: "동대문구",
    중랑구: "중랑구",
    노원구: "노원구",
    도봉구: "도봉구",
    강북구: "강북구",
    성북구: "성북구",
    종로구: "종로구",
    중구: "중구",
    용산구: "용산구",
    서대문구: "서대문구",
    마포구: "마포구",
    은평구: "은평구",
    강서구: "강서구",
    양천구: "양천구",
    구로구: "구로구",
    금천구: "금천구",
    영등포구: "영등포구",
    동작구: "동작구",
    관악구: "관악구",
}

function doSearch(page) {
    let pageNum;

    if (page) {
        pageNum = $(page).data("page");
        subSearch.page = pageNum;
    }

    console.log(subSearch);

    $doAjax("GET",
        "/welfare/list/search",
        subSearch,
        (result) => {
            // 이전 결과 비우기
            $welfareList.empty();

            result.content.forEach((sub) => showList(sub, $welfareList));
            // 페이징 버튼
            showPage(result);
        });
}

/* 페이지 로딩될 때 검색 */
/* 검색 queryString */
const searchString = new URLSearchParams(location.search).get("search");

if(searchString) {
    // 쿼리스트링 value 값을 담음
    subSearch.searchText = searchString;
    $input.val(searchString);
    doSearch();
}

/* 지역 검색버튼 */
$searchLocation.on("click", function () {
    let location = $(this).data("location");

    subSearch.searchFirstAddress = LOCATIONS[location];
    subSearch.page = 1;

    doSearch();
});



/* 결과 뿌리기 */
function showList(sub, $target) {

    let filePath = "/img/welfare/welfare_img_default.png";

    if (sub.representFood != null) filePath = '/image/display?fileName=' + sub.representFood.filePath + '/t_' + sub.representFood.fileUuid + '_' + sub.representFood.fileOrgName;

    let text = `
                <li class="swiper-slide swiper-slide-active">
                    <a href="/welfare/detail/${sub.id}">
                        <div class="brand-list-item">
                          <div class="welfare-img" style="cursor: pointer;">
                                  <img class="original-img"
                                      src="${filePath}"
                                  />
                          </div>
                          <div class="txt-area">
                              <strong class="welfare-name text-elps">${sub.welfareName}</strong>
                              <p class="welfare-info text-elps">${sub.subscriptionTitle}</p>
                              <span class="welfare-location text-elps"
                                  >${sub.address.firstAddress}</span
                              >
                          </div>
                      </div>
                    </a>
                </li>
                `;

    $target.append(text);
}

function showBoxList(sub, $target) {
    let text;

    let filePath = "/img/welfare/welfare_img_default.png";

    if (sub.representFood != null) filePath = '/image/display?fileName=' + sub.representFood.filePath + '/t_' + sub.representFood.fileUuid + '_' + sub.representFood.fileOrgName;

    text = `
        <li class="colum">
            <div class="welfare-in-box">
                <a href="/welfare/detail/${sub.id}" data-target="923">
                    <span class="welfare-img w156">
                        <img src="${filePath}">
                    </span>
                    <div class="txt-area">
                        <strong class="welfare-name text-elps">${sub.welfareName}</strong>
                        <span class="welfare-location text-elps">${sub.address.firstAddress}</span>
                    </div>
                </a>
            </div>
        </li>
    `;

    $target.append(text);
}

const PAGE_AMOUNT = 7;

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
    paging += `<div class="pagination" style="text-align: center; margin-top: 30px">`;

    if (hasPrev) {
        paging += `<a class="changePage" data-page="${startPage}" 
                        onclick="doSearch(this)"><span><</span></a>`;
    }

    for (let i = startPage + 1; i < endPage + 1; i++) {
        let page = i;
        if (pageNumber + 1 != page) {
            paging += `<a class="btn-page" data-page="${page}" onclick="doSearch(this)"><span>${page}</span></a>`;
        } else {
            paging += `<span class="btn-page" id="currentPage">${page}</span>`;
        }
    }
    if (hasNext) {
        paging += `<a class="changePage" data-page="${endPage + 1}" onclick="doSearch(this)"><span>></span></a>`
    }

    $('.page-bottom').html(paging);
}




