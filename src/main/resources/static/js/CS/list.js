const $noticeList = $(".notice-list");
const $useInfoList = $(".use-info-list");
const $faqList = $(".faq-list");

// 임시
// for (let i = 0; i < 10; i++) {
//     addNotice();
//     addInfo();
//     addFAQ();
// }

function addNotice() {
    let text = "";
    lists.forEach((list, i) => {
        text+= `
            <div class="board-wrap">
                <div class="text-center number">
                    <span>${list.id}</span>
                </div>
                <div class="text-left flex1">
                    <a href="javascript:void(0)" class="title-elps">
                        ${list.noticeTitle}
                    </a>
                </div>
                <div class="text-center">
                    <span>${list.createdDate}</span>
                </div>
            </div>
        `;
    })
    $noticeList.append(text);
}
addNotice();

function addInfo(info) {
    let text;

    text = `
    <div class="board-wrap use-info">
        <div class="text-left flex1">
            <a href="javascript:void(0)" class="title-elps">
                영양성분 전수조사 결과 입장문
            </a>
        </div>
        <div class="text-center arrow-wrap">
            <svg
                height="15px"
                viewBox="0 0 512 512"
                width="15px"
            >
                <polygon
                    points="396.6,160 416,180.7 256,352 96,180.7 115.3,160 256,310.5 "
                />
            </svg>
        </div>
    </div>
    <div class="answer-wrap disNone">
        <!-- 답변 영역 -->
        <div class="answer">
            <p class="txt">
                포인트 적립은 구매 확정 후 자동 적립 처리됩니다.
            </p>
        </div>
    </div>
    `;

    $useInfoList.append(text);
}

function addFAQ() {
    let text;

    text = `
    <div class="board-wrap use-info">
        <div class="text-left flex1">
            <a href="javascript:void(0)" class="title-elps">
                결제 후 적립으 어떻게 되나요?
            </a>
        </div>
        <div class="text-center arrow-wrap">
            <svg
                height="15px"
                viewBox="0 0 512 512"
                width="15px"
            >
                <polygon
                    points="396.6,160 416,180.7 256,352 96,180.7 115.3,160 256,310.5 "
                />
            </svg>
        </div>
    </div>
    <div class="answer-wrap disNone">
        <!-- 답변 영역 -->
        <div class="answer">
            <p class="txt">
                포인트 적립은 구매 확정 후 자동 적립 처리됩니다.
            </p>
        </div>
    </div>
    `

    $faqList.append(text);
}