/* 기부 하기 등록 */

const $selectVal = $(".select-value");
const $selectList = $(".select-list");
const $value = $("#value");

const optionList = ["음식 기부", "봉사활동", "무료 배달", "노인복지"];

const fileAddSVG = `
    <i class="ico-plus-xlg">
        <svg
            height="26px"
            viewBox="0 0 512 512"
            width="26px"
        >
            <g>
                <path
                    d="M384,265H264v119h-17V265H128v-17h119V128h17v120h120V265z"
                />
            </g>
        </svg>
    </i>
`;

/* 문의유형 등록 */
function addOptions(params) {
    let $selectOptions = $(".select-list ul");

    let options = `
    <li>
        <a class="inquiryType"
            data-value="${optionList[0]}">
            <span>${optionList[0]}</span>
        </a>
    </li>
    <li>
        <a class="inquiryType"
            data-value="${optionList[1]}">
            <span>${optionList[1]}</span>
        </a>
    </li>
    <li>
        <a class="inquiryType"
            data-value="${optionList[2]}">
            <span>${optionList[2]}</span>
        </a>
    </li>
    <li>
        <a class="inquiryType"
            data-value="${optionList[3]}">
            <span>${optionList[3]}</span>
        </a>
    </li>
    `;

    $selectOptions.append(options);
}

addOptions();

/* 문의유형 선택 */
const $inquiryType = $("a.inquiryType");
$selectVal.on("click", () => disNone($selectList));

// target에 display: none 부여 or 제거
function disNone(target) {
    let check = target.hasClass("disNone");

    if (check) {
        target.removeClass("disNone");
    } else target.addClass("disNone");
}

$inquiryType.on("click", function (e) {
    let val = $(this).data("value");
    $value.text(val);
    disNone($selectList);
});

/* 검색모달 */
const $welfareSearchBtn = $(".welfareSearchBtn");
const $searchModalWrap = $(".search-modal-wrap");

// 모달열기
$welfareSearchBtn.on("click", () => disNone($searchModalWrap));

// 닫기
$searchModalWrap.find(".modalClose").on("click", () => disNone($searchModalWrap));

// 복지관 선택
$searchModalWrap.find(".welfareSelect").on("click", () => disNone($searchModalWrap));

/* 파일 등록 */
const $fileAttachBtn = $(".btn-attach-thumb");
const $imgFile = $("input[name='imgFile']");

$fileAttachBtn.on("click", function () {
    let $fileInput = $(this).next();
    $fileInput.click();
});

$imgFile.on("change", function () {
    let $file = $(this)[0].files[0];
    let formData = new FormData();
    let attachBtn = $(this).prev();

    /* 업로드용 Ajax 코드 들어갈 곳 */

    // 임시 썸네일용, 추후 ajax 콜백으로
    // imgTag생성하여 인자로 전달
    let imgTag = `<img src='https://file.rankingdak.com/image/RANK/PRODUCT/RANKING/20220430/IMG1651rjH248931259.jpg'></img>`;
    changeThumbnail(attachBtn, imgTag);
});

// 버튼 안의 html을 img로 바꾸기
function changeThumbnail(attachBtn, imgTag) {
    attachBtn.html(imgTag);
}


/* 내용 글자수 카운트 이벤트 */

const $counter = $('#counter');
const $textarea = $('#vQuestionCont');

$textarea.on('keyup', function () {
    let content = $(this).val();

    // 글자수 세기
    if (content.length < 1000) {
        $counter.text(content.length);
    }

    // 글자수 제한
    if (content.length > 1000) {
        // 1000자 부터는 타이핑 되지 않도록
        $(this).val($(this).val().substring(0, 1000));
        // 1000자 넘으면 알림창 뜨도록
        alert('글자수는 1000자까지 입력 가능합니다.');
    }
});