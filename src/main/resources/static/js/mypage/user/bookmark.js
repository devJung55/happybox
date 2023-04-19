/* bookmark.html */

/* 좋아요 클릭 이벤트 */

const $likeButtons = $(".like__button");

$likeButtons.each((i, likeButton) => {
    $(likeButton).on("click", function() {
        if($(likeButton).attr("src") == "../../../static/img/mypage/heart.png") {
            $(likeButton).css("filter", "none");
            $(likeButton).attr("src", "../../../static/img/mypage/heart-pull.png");
        } else {
            $(likeButton).css("filter", "invert(100%) sepia(7%) saturate(722%) hue-rotate(198deg) brightness(118%)");
            $(likeButton).attr("src", "../../../static/img/mypage/heart.png");
        }
    });
});