let myPageService = (function() {
    function recipeBoardListAjax(page) {
        $.ajax({
            url: "/mypage/member/recipe-board/",
            data: {"page": page},
            success: function(recipeBoards) {
                showRecipeBoardList(recipeBoards);
            }
        })
    }

    function reviewBoardListAjax(page) {
        $.ajax({
            url: "/mypage/member/review-board",
            data: {"page": page},
            success: function(reviewBoards) {
                showReviewBoardList(reviewBoards);
            }
        })
    }

    function inquiryListAjax(page) {
        $.ajax({
            url: "/mypage/member/inquiry-list/",
            data: {"page": page},
            success: function(inquiries) {
                showInquiryList(inquiries);
            }
        })
    }

    function orderListAjax(page) {
        $.ajax({
            url: "/mypage/member/order-list",
            data: {"page": page},
            success: function(orderList) {
                showOrderList(orderList);
            }
        })
    }

    function orderListBySearchDate(searchDateDTO) {
        $.ajax({
            url: "/mypage/member/order-list",
            data: searchDateDTO,
            success: function(orderList) {
                showOrderList(orderList);
            }
        })
    }

    function recipeBoardBookmarkAjax(page) {
        $.ajax({
            url: "/mypage/member/recipe-bookmark-list",
            data: {"page": page},
            success: function (bookmarkList) {
                showRecipeBoardBookmarkList(bookmarkList);
            }
        })
    }

    function subscriptionBookmarkAjax(page) {
        $.ajax({
            url: "/mypage/member/subscrition-bookmark-list",
            data: {"page": page},
            success: function (bookmarkList) {
                showSubscriptionBookmarkList(bookmarkList);
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
            success: function() {
                location.reload();
            }
        })
    }

    function cancelBookmarkSubscription(id) {
        $.ajax({
            url: "/mypage/member/subscription-bookmark-cancel",
            data: {"id": id},
            success: function() {
                location.reload();
            }
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
    adminNoticeService.getNoticeList();
}

/* 페이지 버튼 append 할 곳 */
const $contentWrap = $('.pagination');

function pagination(data) {
    let pageable = data.pageable;

    /* 현재 페이지 */
    let pageNumber = pageable.pageNumber;

    let count = Math.floor(pageNumber / PAGE_AMOUNT);
    /* 시작 페이지 */
    let startPage = count * PAGE_AMOUNT;
    /* 끝 페이지 */
    let endPage = startPage + PAGE_AMOUNT;

    endPage = endPage > data.totalPages ? data.totalPages : endPage;

    let hasPrev = startPage > 1;
    let hasNext = endPage < data.totalPages;

    /* 페이지 버튼 추가하는 HTML 코드 작성부 */
    let text =  "";

    if (pageable == null) {
        text = ``;
    } else {
        text = `
                <!-- 페이지 버튼 -->
                <div class="page-button-box-layout">
                    <div class="page-button-box">
                        <!-- 페이지 번호 -->
                `
        if(hasPrev) {
            text += `
                        <!-- 페이지 개수 10개 이상 -->
                        <div class="">
                            <div class="page-button-margin">
                                <div>
                                    <a href="javascript:setPage(${startPage})" ><img src="https://cdn3.iconfinder.com/data/icons/google-material-design-icons/48/ic_keyboard_arrow_left_48px-128.png" class="left-button"></a>
                                </div>
                            </div>
                        </div>
                    `
        }
        for(let i = startPage + 1; i < endPage + 1; i++) {
            let page = i
            /* 현재 페이지가 내가 선택한 페이지 일 경우 */
            if (pageNumber + 1 == page) {
                text += `<div onclick="setPage(${i})" class="page-button page-button-active"> `
            } else {
                text += ` <div onclick="setPage(${i})" class="page-button"> `
            }
            text += `
                    <div class="page-button-margin">
                            <div>
                                <span>${i}</span>
                            </div>
                        </div>
                    </div>
                `
        }

        if (hasNext) {
            text += `
                        <div class="">
                            <div class="page-button-margin">
                                <div>
                                    <a href="javascript:setPage(${endPage + 1})"><img src="https://cdn3.iconfinder.com/data/icons/google-material-design-icons/48/ic_keyboard_arrow_right_48px-128.png" class="right-button"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 페이지 버튼 끝 -->
            `
        }
    }
    $contentWrap.append(text);
}