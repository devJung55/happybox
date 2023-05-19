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
                console.log(orderList)
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

    return {
        recipeBoardListAjax: recipeBoardListAjax,
        inquiryListAjax: inquiryListAjax,
        orderListAjax: orderListAjax,
        recipeBoardBookmarkAjax: recipeBoardBookmarkAjax,
        subscriptionBookmarkAjax: subscriptionBookmarkAjax,
        orderListBySearchDate: orderListBySearchDate
    }
}());