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

myPageService.recipeBoardBookmarkAjax();
myPageService.subscriptionBookmarkAjax();

function showRecipeBoardBookmarkList(bookmarkList) {
    let text = "";
    let img = "";

    bookmarkList.content.forEach(board => {

        console.log(board)
        const formattedDate = formatDate(new Date(board.createdDate));
        text += `
            <li class="ext-li colum">
                <input type="hidden" class="id" value="${board.id}">
                <div class="prd-item type-sm2">
                    <figure class="img w180">
                        <a href="/user-board/recipe-board-detail/${board.recipeBoardId}">
                `;

        if(board.boardFiles.length != 0) {
            for (let i = 0; i < board.boardFiles.length; i++) {
                if(i == 0) {
                    img = `<img class="lozad" src="/image/display?fileName=${board.boardFiles[i].filePath}/${board.boardFiles[i].fileUuid}_${board.boardFiles[i].fileOrgName}">`
                }
            }
        } else {
            img = `<img class="lozad" src="https://us.123rf.com/450wm/mathier/mathier1905/mathier190500002/134557216-%EC%8D%B8%EB%84%A4%EC%9D%BC-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%97%86%EC%9D%8C-%ED%8F%AC%EB%9F%BC-%EB%B8%94%EB%A1%9C%EA%B7%B8-%EB%B0%8F-%EC%9B%B9%EC%82%AC%EC%9D%B4%ED%8A%B8%EC%9A%A9-%EC%9E%90%EB%A6%AC-%ED%91%9C%EC%8B%9C%EC%9E%90.jpg?ver=6">`
        }

        text += img;

        text += `
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
    });
    $recipeBookmarkAppend.empty();
    $recipeBookmarkAppend.append(text);
}


function showSubscriptionBookmarkList(welfareList) {
    let text = "";
    let img = "";

    welfareList.content.forEach(board => {

        text += `
            <li class="ext-li colum">
                <input type="hidden" class="id" value="${board.id}">
                <input type="hidden" value="${board.subscriptionId}">
                <div class="prd-item type-sm2">
                    <figure class="img w180">
                        <a href="/welfare/detail/${board.subscriptionId}">
                `;
                    if(board.userFileDTO != null) {
                        img = `<img class='lozad' src="/image/display?fileName=${board.userFileDTO.filePath}/${board.userFileDTO.fileUuid}_${board.userFileDTO.fileOrgName}">`;
                    } else {
                        img = `<img class="lozad" src="https://us.123rf.com/450wm/mathier/mathier1905/mathier190500002/134557216-%EC%8D%B8%EB%84%A4%EC%9D%BC-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%97%86%EC%9D%8C-%ED%8F%AC%EB%9F%BC-%EB%B8%94%EB%A1%9C%EA%B7%B8-%EB%B0%8F-%EC%9B%B9%EC%82%AC%EC%9D%B4%ED%8A%B8%EC%9A%A9-%EC%9E%90%EB%A6%AC-%ED%91%9C%EC%8B%9C%EC%9E%90.jpg?ver=6">`;
                    }

        text += img;

        text += `
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
    $welfareBookmarkAppend.empty();
    $welfareBookmarkAppend.append(text);
}

/* 복지관, 레시피 게시물 찜 삭제 */
$("#wishList").on("click", ".like__wrap", function() {
    let id = $($(this).parent().siblings()[0]).val();

    if(window.location.pathname == "/mypage/member/subscription-bookmark") {
        myPageService.cancelBookmarkSubscription(id);
    } else if(window.location.pathname == "/mypage/member/recipe-bookmark") {
        myPageService.cancelBookmarkRecipeBoard(id);
    }
});