const $noticeList = $(".notice-list");
const $useInfoList = $(".use-info-list");
const $faqList = $(".faq-list");

// 임시로 load() 사용
$(".frame-wrap").prepend(
    `
    <div class="frame-left">
        <div class="aside-menu-wrap">
            <nav class="aside-menu">
                <ul class="menu-list">
                    <li><a href="javascript:void(0)" class="menu">공지사항</a></li>
                    <li><a href="javascript:void(0)" class="menu">이용안내</a></li>
                    <li>
                        <a href="javascript:void(0)" class="menu">자주묻는 FAQ</a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" class="menu">1:1 문의하기</a>
                    </li>
                </ul>
            </nav>
            <!--// aside-menu -->
            <div class="aside-guide-box">
                <dl>
                    <dt>랭킹닭컴 고객센터</dt>
                    <dd class="phone-num">02-6405-8088</dd>
                    <dd>평일 <span class="text-num-md">09:00 ~ 18:00</span></dd>
                    <dd>주말, 공휴일 휴무</dd>
                </dl>
                <dl>
                    <dt>실시간 채팅 상담</dt>
                    <dd>평일 <span class="text-num-md">09:00 ~ 21:00</span></dd>
                    <dd>토요일 <span class="text-num-md">09:00 ~ 15:00</span></dd>
                    <dd>일요일 <span class="text-num-md">13:00 ~ 21:00</span></dd>
                </dl>
            </div>
            <!--// aside-guide-box -->
        </div>
        <!--// aside-menu-wrap -->
    </div>
    `
);

// 임시
for (let i = 0; i < 10; i++) {
    addNotice();
    addInfo();
    addFAQ();
}

function addNotice(notice) {
    let text;

    text = `
        <div class="board-wrap">
            <div class="text-center number">
                <span>152</span>
            </div>
            <div class="text-left flex1">
                <a href="javascript:void(0)" class="title-elps">
                    영양성분 전수조사 결과 입장문
                </a>
            </div>
            <div class="text-center">
                <span>2023.02.03</span>
            </div>
        </div>
    `;

    $noticeList.append(text);
}

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