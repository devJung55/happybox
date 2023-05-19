let memberId = 1;

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