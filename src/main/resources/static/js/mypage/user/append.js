function showRecipeBoardList(recipeBoards) {
    const $append = $(".article__wrap");
    let text = "";

    recipeBoards.content.forEach(recipeBoard => {
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
                            <img src="https://image.wanted.co.kr/optimize?src=https%3A%2F%2Fstatic.wanted.co.kr%2FImageTargetTypeEnum.COMMUNITY%2F2023%2F4%2Fcc499f657b5f0ace6f13f6a3e1d0926e0aac8fa0afe4168b85d11dfa251dd359&amp;w=310&amp;q=90" class="board-image">
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
            $pagination.append(`<a href="javascript:void(0)" class="current"><span>${i}</span></a>`);
        } else {
            // 다른 페이지는 a 태그로 표시
            $pagination.append(`<a href="/mypage/member/recipe-board/${i}" class="current"><span>${i}</span></a>`);
        }
    }
    if (page < totalPages - 1) {
        $pagination.append(`<a href="javascript:void(0)" class="btn-page next"><span class="blind2">&gt;</span></a>`);
    }
}