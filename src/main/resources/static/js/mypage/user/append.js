const $append = $(".article__wrap");
const $inquiryAppend = $(".inquiry__list__append");
let memberId = 1;
let page = 0;

function showRecipeBoardList(recipeBoards) {
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

function showInquiryList(inquiries) {
    let text = "";
    inquiries.content.forEach(inquiry => {
        const formattedDate = formatDate(new Date(inquiry.createdDate));
        text += `
                    <li class="border-bottom">
                        <a href="javascript:void(0)" class="title-div ui-accordion-click">
                            <div class="subject">
                                <span class="state-wait">답변대기</span>
                                <span class="state-wait complete">답변완료</span>
                                <span class="classify inquiry__title">${inquiry.inquiryTitle}</span>
                            </div>
                            <div class="right">
                                <span class="date">${formattedDate}</span>
                                <img class="arrow-0deg" src="/img/mypage/inquiry-arrow.png" width="18" height="18">
                            </div>
                
                        <div class="ui-accordion-view hide" style="display: none;">
                            <div class="question">
                                <div class="detail-div">
                                    <p class="txt">
                                        <span style="white-space:pre-wrap">${inquiry.inquiryContent}</span>
                                    </p>
                                    <div class="bottom">
                                        <div class="added-file thumDtlQuestion">
                                            <ul class="thum-list">
                                                <li>
                                                    <a href="javascript:void(0)">
                                                        <img class="thumnail" src="https://file.rankingdak.com/image/RANK/REVIEW/US_RV_IMG1/20230415/IMG1681Teg570545730.png">
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                        <ul class="sep-list type3">
                                            <li><a href="javascript:void(0)" class="btn_delete">삭제</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
                `;
    });
    $inquiryAppend.append(text);
    displayPagination(inquiries.totalPages);
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