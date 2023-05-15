globalThis.page = 1;

let myPageService = (function() {
    function recipeBoardListAjax() {
        $.ajax({
            url: `/mypage/member/recipe-board/${page}`,
            success: function(recipeBoards) {
                showRecipeBoardList(recipeBoards);
            }
        })
    }
    return {recipeBoardListAjax: recipeBoardListAjax}
}());

myPageService.recipeBoardListAjax();