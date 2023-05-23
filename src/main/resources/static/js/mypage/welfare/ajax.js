let myPageWelfareService = (function() {
    function inquiryListAjax(page) {
        $.ajax({
            url: "/mypage/welfare/inquiry/list",
            data: {"page": page},
            success: function(inquiries) {
                showInquiryList(inquiries);
                pagination(inquiries);
            }
        })
    }

    function subscriberListAjax(page) {
        $.ajax({
            url: "/mypage/welfare/subscriber/list",
            data: {"page": page},
            success: function(subscribers) {
                console.log(subscribers)
                showSubscriberList(subscribers);
                pagination(subscribers);
            }
        })
    }

    function subscriberListSearchNameAjax(page, subscriberName) {
        $.ajax({
            url: "/mypage/welfare/subscriber/list/searchName",
            data: {"page": page, "subscriberName": subscriberName},
            success: function(subscribers) {
                console.log(subscribers)
                showSubscriberList(subscribers);
                pagination(subscribers);
            }
        })
    }

    return {
        inquiryListAjax: inquiryListAjax,
        subscriberListAjax: subscriberListAjax,
        subscriberListSearchNameAjax: subscriberListSearchNameAjax
    }
}());


/* 페이지 처리 */
globalThis.page = 1;

function setPage(page) {
    globalThis.page = page;
    let url = window.location.pathname;

    if(url == "/mypage/welfare/inquiry") {
        myPageWelfareService.inquiryListAjax(page);
    } else if(url == "/mypage/welfare/subscriber") {
        myPageWelfareService.subscriberListAjax(page);
    }
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

/* 구독자 이름 조회 */

globalThis.subscripberName = "";
const $searchName = $("input[name=srchProductNm]");

$(".ico-btn-search").on("click", function() {
    globalThis.subscripberName = $searchName.val();

    myPageWelfareService.subscriberListSearchNameAjax(page, globalThis.subscripberName);
});