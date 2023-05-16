let memberId = 1;

let myPageService = (function() {
    function recipeBoardListAjax(page) {
        $.ajax({
            url: "/mypage/member/recipe-board/",
            data: {"page": page, "memberId": memberId},
            success: function(recipeBoards) {
                showRecipeBoardList(recipeBoards);
            }
        })
    }

    function inquiryListAjax(page) {
        $.ajax({
            url: "/mypage/member/inquiry-list/",
            data: {"page": page, "memberId": memberId},
            success: function(inquiries) {
                console.log("success");
                showInquiryList(inquiries);
            }
        })
    }
    return {recipeBoardListAjax: recipeBoardListAjax, inquiryListAjax: inquiryListAjax}
}());

myPageService.recipeBoardListAjax();
myPageService.inquiryListAjax();