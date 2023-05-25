
// 페이지 버튼 개수
const PAGE_AMOUNT = 7;

let myPageService = (function() {
    function recipeBoardListAjax(page) {
        $.ajax({
            url: "/mypage/member/recipe-board/",
            data: {"page": page},
            success: function(recipeBoards) {
                showRecipeBoardList(recipeBoards);
                pagination(recipeBoards);
            }
        })
    }

    function reviewBoardListAjax(page) {
        $.ajax({
            url: "/mypage/member/review-board",
            data: {"page": page},
            success: function(reviewBoards) {
                showReviewBoardList(reviewBoards);
                pagination(reviewBoards);
            }
        })
    }

    function inquiryListAjax(page) {
        $.ajax({
            url: "/mypage/member/inquiry-list/",
            data: {"page": page},
            success: function(inquiries) {
                showInquiryList(inquiries);
                pagination(inquiries);
            }
        })
    }

    function orderListAjax(page) {
        $.ajax({
            url: "/mypage/member/order-list",
            data: {"page": page},
            success: function(orderList) {
                showOrderList(orderList);
                pagination(orderList);
            }
        })
    }

    function orderListBySearchDate(searchDateDTO) {
        $.ajax({
            url: "/mypage/member/order-list",
            data: searchDateDTO,
            success: function(orderList) {
                showOrderList(orderList);
                pagination(orderList);
            }
        })
    }

    function recipeBoardBookmarkAjax(page) {
        $.ajax({
            url: "/mypage/member/recipe-bookmark-list",
            data: {"page": page},
            success: function (bookmarkList) {
                showRecipeBoardBookmarkList(bookmarkList);
                pagination(bookmarkList);
            }
        })
    }

    function subscriptionBookmarkAjax(page) {
        $.ajax({
            url: "/mypage/member/subscrition-bookmark-list",
            data: {"page": page},
            success: function (bookmarkList) {
                showSubscriptionBookmarkList(bookmarkList);
                pagination(bookmarkList);
            }
        })
    }

    function cancelSubscribe(id) {
        $.ajax({
            url: "/mypage/member/subscribe-cancel",
            data: {"id": id},
        })
    }

    function cancelBookmarkRecipeBoard(id) {
        $.ajax({
            url: "/mypage/member/recipe-bookmark-cancel",
            data: {"id": id},
        })
    }

    function cancelBookmarkSubscription(id) {
        $.ajax({
            url: "/mypage/member/subscription-bookmark-cancel",
            data: {"id": id},
        })
    }

    return {
        recipeBoardListAjax: recipeBoardListAjax,
        reviewBoardListAjax: reviewBoardListAjax,
        inquiryListAjax: inquiryListAjax,
        orderListAjax: orderListAjax,
        recipeBoardBookmarkAjax: recipeBoardBookmarkAjax,
        subscriptionBookmarkAjax: subscriptionBookmarkAjax,
        orderListBySearchDate: orderListBySearchDate,
        cancelSubscribe: cancelSubscribe,
        cancelBookmarkRecipeBoard: cancelBookmarkRecipeBoard,
        cancelBookmarkSubscription: cancelBookmarkSubscription
    }
}());


/* 페이지 처리 */

globalThis.page = 1;

function setPage(page) {
    globalThis.page = page;
    let url = window.location.pathname;

    if(url == "/mypage/member/inquiry") {
        myPageService.inquiryListAjax(page);
    } else if(url == "/mypage/member/review") {
        myPageService.reviewBoardListAjax(page);
    } else if(url == "/mypage/member/board") {
        myPageService.recipeBoardListAjax(page);
    } else if(url == "/mypage/member/order") {
        myPageService.orderListAjax(page);
    } else if(url == "/mypage/member/recipe-bookmark") {
        myPageService.recipeBoardBookmarkAjax(page);
    } else if(url == "/mypage/member/subscription-bookmark") {
        myPageService.subscriptionBookmarkAjax(page);
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