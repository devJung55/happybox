const $selectList = $(".select-list");
const $selectVal = $(".select-value");
const $value = $("#value");
const $srchOption = $(".srchOpt");
const $srchType = $("input[name='srchType']");
const $keyword = $("input[name='keyword']");
const $srchButton = $("#noticeSrch");

const $noticeList = $(".notice-list");
/* 한 페이지에 보여질 페이지의 개수 */
const PAGE_AMOUNT = 10;

const url = "/cs/search";

/* 검색 조건 */
const noticeSearch = {
    /* 처음 아무것도 선택 안했을 때 */
    searchType: "전체",
    keyword: null
};

$srchType.val($srchOption.eq(0).data('value'));

$selectVal.on("click", () => disNone());

$srchOption.on('click', function() {
    /* 선택 옵션 */
    let val = $(this).data('value');
    $value.text(val);
    disNone();
    $srchType.val(val);
    noticeSearch.searchType = val;
})

$keyword.on('input', function() {
    /* 검색할 내용 */
    let searchKeyword = $(this).val();
    noticeSearch.keyword = searchKeyword;
}).on('keydown', function(e) {
    // input 타입 엔터 눌러도 form 안 넘어가게 막아주기
    if (e.which == 13) {
        e.preventDefault();
    }
});

/* 검색 */
const goSearch = (page) => {

    let pageNum;

    if (page) {
        pageNum = $(page).data("page");
        noticeSearch.page = pageNum;
    }
    $searchAjax("get", url, noticeSearch, (result) => {
        // 원래 있던 내용 비워주기
        $noticeList.empty();
        result.content.forEach((e) => addNotice(e))
        // 페이징
        noticePage(result)
    });
}

/* 페이지 로딩 시 첫 조회 */
goSearch();

function addNotice(list) {
    let text = "";
    const formattedDate = formatDate(new Date(list.createdDate));
    text+= `
            <div class="board-wrap">
                <div class="text-center number">
                    <span>${list.id}</span>
                </div>
                <div class="text-left flex1">
                    <a href="/cs/notice-detail/${list.id}" class="title-elps">
                        ${list.noticeTitle}
                    </a>
                </div>
                <div class="text-center">
                    <span>${formattedDate}</span>
                </div>
            </div>
        `;
    $noticeList.append(text);
}

const $pageWrap = $('.page-bottom');

function noticePage(get) {

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
            paging += ` <a class="btn-page prev" data-page="${startPage}" onclick="goSearch(this)"><span class="blind">&lt;</span></a> `
        }

        for (let i = startPage + 1; i < endPage + 1; i++) {
            let page = i
            if (Num + 1 != page) {
                paging += `<a class="change-page" data-page="${page}" onclick="goSearch(this)"><span>${page}</span></a> `
            } else {
                paging += ` <a class="current"><span>${page}</span></a>`
            }
        }

        if (hasNext) {
            paging +=`<a class="btn-page next" data-page="${endPage + 1}" onclick="goSearch(this)"><span class="blind">&gt;</span></a>`
        }
        paging += `
            </div>
            <!--// pagination -->
        `;
        $pageWrap.html(paging);
    }
}


/* 클릭을 누르면 ajax를 실행 */
$('#noticeSrch').on('click', function () {
    console.log(noticeSearch)
    goSearch();
})


function disNone() {
    let check = $selectList.hasClass("disNone");

    if (check) {
        $selectList.removeClass("disNone");
    } else $selectList.addClass("disNone");
}
