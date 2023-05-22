/* bookmark.html */

/* 좋아요 클릭 이벤트 */

$(".recipeBoard__bookmark__append").on("click", ".like__button", function() {
    $(this).on("click", function() {
        if($(this).attr("src") == "/img/mypage/heart.png") {
            $(this).css("filter", "none");
            $(this).attr("src", "/img/mypage/heart-pull.png");
        } else {
            $(this).css("filter", "invert(100%) sepia(7%) saturate(722%) hue-rotate(198deg) brightness(118%)");
            $(this).attr("src", "/img/mypage/heart.png");
        }
    });
})

$(".welfare__bookmark__append").on("click", ".like__button", function() {
    $(this).on("click", function() {
        if($(this).attr("src") == "/img/mypage/heart.png") {
            $(this).css("filter", "none");
            $(this).attr("src", "/img/mypage/heart-pull.png");
        } else {
            $(this).css("filter", "invert(100%) sepia(7%) saturate(722%) hue-rotate(198deg) brightness(118%)");
            $(this).attr("src", "/img/mypage/heart.png");
        }
    });
})

/*---------------------------------------------------------------------------*/

const $pageMove = $(".bookmark");
const $empty = $("#wishList");

$pageMove.on("click", function() {
    if($(this).hasClass("welfare__bookmark")) {
        location.href = "/mypage/member/subscription-bookmark";
    } else {
        location.href = "/mypage/member/recipe-bookmark";
    }
})

/*---------------------------------------------------------------------------*/
const $recipeBookmarkAppend = $(".recipeBoard__bookmark__append");
const $welfareBookmarkAppend = $(".welfare__bookmark__append");
let page = 0;

myPageService.recipeBoardBookmarkAjax();
myPageService.subscriptionBookmarkAjax();

function showRecipeBoardBookmarkList(bookmarkList) {
    let text = "";

    bookmarkList.content.forEach(board => {
        const formattedDate = formatDate(new Date(board.createdDate));
        text += `
            <li class="ext-li colum">
                <input type="hidden" class="id" value="${board.id}">
                <div class="prd-item type-sm2">
                    <figure class="img w180">
                        <a href="javascript:void(0)">
                            <img class="lozad" src="https://file.rankingdak.com/image/RANK/PRODUCT/PRD001/20230216/IMG1676iqe535748247_330_330.jpg">
                        </a>
                    </figure>
                    <div class="desc-bottom">
                        <div class="brand-name recipe__title">${board.recipeBoardTitle}</div>
                        <p class="origin user__id"><span>${board.memberName}</span></p>
                        <p class="origin register__date"><span>${formattedDate}</span></p>
                    </div>
                    <div class="like__wrap">
                        <img class="like__button" src="/img/mypage/heart-pull.png" width="20" height="20">
                    </div>
                </div>
            </li>
        `;
        // text += displayPaginationRecipeBookmark(bookmarkList.totalPages);
    });
    $recipeBookmarkAppend.append(text);
    showPage(bookmarkList);
}


function showSubscriptionBookmarkList(welfareList) {
    let text = "";

    welfareList.content.forEach(board => {
        text += `
            <li class="ext-li colum">
                <input type="hidden" class="id" value="${board.id}">
                <div class="prd-item type-sm2">
                    <figure class="img w180">
                        <a href="javascript:void(0)">
                            <img class="lozad" src="https://file.rankingdak.com/image/RANK/PRODUCT/PRD001/20230216/IMG1676iqe535748247_330_330.jpg">
                        </a>
                    </figure>
                    <div class="desc-bottom">
                        <div class="brand-name">${board.welfareName}</div>
                        <p class="origin"><span>${board.subscriptionPrice}</span>원</p>
                    </div>
                    <div class="like__wrap">
                        <img class="like__button" src="/img/mypage/heart-pull.png" width="20" height="20">
                    </div>
                </div>
            </li>
        `;
    });
    $welfareBookmarkAppend.append(text);
    showPage(welfareList);
}


$(".paging-div").on("click", "a", function(e) {
    e.preventDefault();
    const targetPage = $(this).text();
    page = parseInt(targetPage);

    if(window.location.pathname == "/mypage/member/subscription-bookmark") {
        $welfareBookmarkAppend.empty();
        myPageService.subscriptionBookmarkAjax(page);
    } else if(window.location.pathname == "/mypage/member/recipe-bookmark") {
        $recipeBookmarkAppend.empty();
        myPageService.recipeBoardBookmarkAjax(page);
    }
});


/*복지관, 레시피 게시물 찜 삭제*/
$("#wishList").on("click", ".like__wrap", function() {
    let id = $($(this).parent().siblings()[0]).val();

    if(window.location.pathname == "/mypage/member/subscription-bookmark") {
        myPageService.cancelBookmarkSubscription(id);
    } else if(window.location.pathname == "/mypage/member/recipe-bookmark") {
        myPageService.cancelBookmarkRecipeBoard(id);
    }
});