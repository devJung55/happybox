const $append = $(".article__wrap");

myPageService.recipeBoardListAjax();

function showRecipeBoardList(recipeBoards) {
    console.log(recipeBoards)
    let text = "";
    let image = "";
    let profile = "";

    recipeBoards.content.forEach(recipeBoard => {
        if(recipeBoard.recipeBoardFiles.length != 0) {
            for (let i = 0; i < recipeBoard.recipeBoardFiles.length; i++) {
                if(i == 0) {
                    image = `<img class="board-image" src="/image/display?fileName=${recipeBoard.recipeBoardFiles[i].filePath}/${recipeBoard.recipeBoardFiles[i].fileUuid}_${recipeBoard.recipeBoardFiles[i].fileOrgName}">`;
                }
            }
        } else {
            image = `<img class="board-image" src="https://us.123rf.com/450wm/mathier/mathier1905/mathier190500002/134557216-%EC%8D%B8%EB%84%A4%EC%9D%BC-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%97%86%EC%9D%8C-%ED%8F%AC%EB%9F%BC-%EB%B8%94%EB%A1%9C%EA%B7%B8-%EB%B0%8F-%EC%9B%B9%EC%82%AC%EC%9D%B4%ED%8A%B8%EC%9A%A9-%EC%9E%90%EB%A6%AC-%ED%91%9C%EC%8B%9C%EC%9E%90.jpg?ver=6">`
        }

        const formattedDate = formatDate(new Date(recipeBoard.boardRegisterDate));
        text +=
            `
                <article class="board-item-wrap">
                    <div class="profile-area">
                        <a href="javascript:void(0)">
                            <div class="profile-wrap">
                                <div class="writer-image" style="width: 40px; height: 40px;">
            `;

        if(recipeBoard.userFileDTO != null) {
            profile = `<img src="/image/display?fileName=${recipeBoard.userFileDTO.filePath}/${recipeBoard.userFileDTO.fileUuid}_${recipeBoard.userFileDTO.fileOrgName}">`;
        } else {
            profile = `<img src="/img/mypage/nomalProfile.png" alt="">`;
        }

        text += profile;

        text += `
                                    
                                </div>
                                <div class="writer-info-wrap">
                                    <div class="writer-info">
                                        <div class="writer-name">${recipeBoard.memberDTO.memberName}</div>
                                    </div>
                                    <span class="write-date" style="width: 61px;">${formattedDate}</span>
                                </div>
                            </div>
                        </a>
                    </div>
                    <a href="/user-board/recipe-board-detail/${recipeBoard.id}">
                        <input type="hidden" value="${recipeBoard.id}">
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
