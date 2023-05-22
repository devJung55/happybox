const $append = $(".article__wrap");

myPageService.recipeBoardListAjax();

function showRecipeBoardList(recipeBoards) {
    let text = "";
    let image = "";

    recipeBoards.content.forEach(recipeBoard => {
        if(recipeBoard.recipeBoardFiles.length != 0) {
            for (let i = 0; i < recipeBoard.recipeBoardFiles.length; i++) {
                if(i == 0) {
                    image = `
                        <img class="board-image" src="/image/display?fileName=${recipeBoard.recipeBoardFiles[i].filePath}/${recipeBoard.recipeBoardFiles[i].fileUuid}_${recipeBoard.recipeBoardFiles[i].fileOrgName}">
                    `;
                }
            }
        } else {
            image = "";
        }

        const formattedDate = formatDate(new Date(recipeBoard.boardRegisterDate));
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
                        <h3 class="board-title">${recipeBoard.boardTitle}</h3>
                        <p class="board-content">${recipeBoard.boardContent}</p>
                        <picture>
            `;

        text += image;

        text += `
            
                        </picture>
                    </a>
                </article>
            `;
    });
    $append.empty();
    $append.append(text);
}
