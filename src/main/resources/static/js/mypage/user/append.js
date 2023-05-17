const $append = $(".article__wrap");
let page = 0;

myPageService.recipeBoardListAjax();

function showRecipeBoardList(recipeBoards) {
    let text = "";
    let image = "";

    recipeBoards.content.forEach(recipeBoard => {
        if(recipeBoard.boardFiles.length != 0) {
            for (let i = 0; i < recipeBoard.boardFiles.length; i++) {
                if(i == 0) {
                    image = `
                        <img class="board-image" src="/image/display?fileName=${recipeBoard.boardFiles[i].filePath}/${recipeBoard.boardFiles[i].fileUuid}_${recipeBoard.boardFiles[i].fileOrgName}">
                    `;
                }
            }
        } else {
            image = "";
        }
        const formattedDate = formatDate(new Date(recipeBoard.recipeBoardRegisterDate));
        text +=
            `
                <article class="board-item-wrap">
                    <div class="profile-area">
                        <a href="javascript:void(0)">
                            <div class="profile-wrap">
                                <div class="writer-image">
                                    <img src="https://static.wanted.co.kr/oneid-user/profile_default.png" alt="">
                                </div>
                                <div class="writer-info-wrap">
                                    <div class="writer-info">
                                        <div class="writer-name">${recipeBoard.memberName}</div>
                                    </div>
                                    <span class="write-date" style="width: 61px;">${formattedDate}</span>
                                </div>
                            </div>
                        </a>
                    </div>
                    <a href="javascript:void(0)">
                        <h3 class="board-title">${recipeBoard.recipeBoardTitle}</h3>
                        <p class="board-content">${recipeBoard.recipeBoardContent}</p>
                        <picture>
            `;

        text += image;

        text += `
            
                        </picture>
                    </a>
                </article>
            `;
    });
    $append.append(text);
    displayPagination(recipeBoards.totalPages);
}

function displayPagination(totalPages) {
    const $pagination = $(".pagination");
    $pagination.empty();

    if (page > 0) {
        $pagination.append(`<a href="javascript:void(0)" class="btn-page prev"><span class="blind2">&lt;</span></a>`);
    }

    for (let i = 1; i <= totalPages; i++) {
        if (i === page + 1) {
            // 현재 페이지를 텍스트로 표시
            $pagination.append(`<a href="javascript:void(0)" id="prev" class="arrow current"><span>${i}</span></a>`);
        } else {
            // 다른 페이지는 a 태그로 표시
            $pagination.append(`<a href="#" class="current"><span>${i}</span></a>`);
        }
    }

    if (page < totalPages - 1) {
        $pagination.append(`<a href="javascript:void(0)" id="next" class="arrow btn-page next"><span class="blind2">&gt;</span></a>`);
    }
}

$(".pagination").on("click", "a", function(e) {
    e.preventDefault();
    const targetPage = $(this).text();
    page = parseInt(targetPage);
    $append.empty();
    myPageService.recipeBoardListAjax(page);
});