const $selectVal = $(".select-value");
const $selectList = $(".select-list");
const $value = $("#value");

const optionList = ["주문/결제", "취소/환불", "사이트 문의", "이용방법 문의", "기타"];

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
            data-value="ORDER">
            <span>${optionList[0]}</span>
        </a>
    </li>
    <li>
        <a class="inquiryType"
            data-value="CANCEL">
            <span>${optionList[1]}</span>
        </a>
    </li>
    <li>
        <a class="inquiryType"
            data-value="SITE">
            <span>${optionList[2]}</span>
        </a>
    </li>
    <li>
        <a class="inquiryType"
            data-value="USE">
            <span>${optionList[3]}</span>
        </a>
    </li>
    <li>
        <a class="inquiryType"
            data-value="ETC">
            <span>${optionList[4]}</span>
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

/* 문의 유형 선택 */
$inquiryType.on("click", function (e) {
    let type = $(this).data("value")
    let val = $(this).children().text();
    $value.text(val);
    disNone($selectList);
    /* 타입 선택 */
    inputType(type)
});

/* 테이블 */
const $table = $('.table');
/* 타입 선택 후 선택 값 폼 안에 넣어주기 */
function inputType(type) {
    $table.find("input[name='inquiryType']").remove();

    let text = `<input type="hidden" name="inquiryType" value="${type}" th:field="*{inquiryType}"/>`;
    $table.append(text);
}

function addFile() {
    /* 만약 파일이 배열 안에 없다면 만들어진 input을 모두 없앰 */
    if (fileList == null) {
        $table.find(`input[name='inquiryFileDTOS[${i}].filePath']`).remove();
        $table.find(`input[name='inquiryFileDTOS[${i}].fileUuid']`).remove();
        $table.find(`input[name='inquiryFileDTOS[${i}].fileOrgName']`).remove();
    }
    fileList.forEach((file, i)=> {
        /* 파일이 추가 될 때마다 중복으로 값이 들어오니 이미 올라간 파일 내용들을 지워줌 */
        $table.find(`input[name='inquiryFileDTOS[${i}].filePath']`).remove();
        $table.find(`input[name='inquiryFileDTOS[${i}].fileUuid']`).remove();
        $table.find(`input[name='inquiryFileDTOS[${i}].fileOrgName']`).remove();

        let text = "";
        text += `
        <input type="hidden" name="inquiryFileDTOS[${i}].filePath" value="${path[i][0]}" th:field="*{inquiryFileDTOS[${i}].filePath}"/>
        <input type="hidden" name="inquiryFileDTOS[${i}].fileUuid" value="${Uuid[i][0]}" th:field="*{inquiryFileDTOS[${i}].fileUuid}"/>
        <input type="hidden" name="inquiryFileDTOS[${i}].fileOrgName" value="${file[0].name}" th:field="*{inquiryFileDTOS[${i}].fileOrgName}"/>
    `;
        $table.append(text);
    })
}


$('.btn-primary').on('click', function () {
    document.write.submit();
})


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

/* 파일 추가 버튼 누르면 작동하게 하기 */
$fileAttachBtn.on("click", function () {
    let $fileInput = $(this).next();
    $fileInput.click();
});

// const $imgFile = $("input[name='imgFile']");

// $imgFile.on("change", function () {
//     let $file = $(this)[0].files[0];
//     let formData = new FormData();
//     let attachBtn = $(this).prev();
//
//     /* 업로드용 Ajax 코드 들어갈 곳 */
//
//     // 임시 썸네일용, 추후 ajax 콜백으로
//     // imgTag생성하여 인자로 전달
//     let imgTag = `<img src='https://www.rankingdak.com/resources/pc/images/icon/ico_bullet.png'></img>`;
//     changeThumbnail(attachBtn, imgTag);
// });
//
// // 버튼 안의 html을 img로 바꾸기
// function changeThumbnail(attachBtn, imgTag) {
//     attachBtn.html(imgTag);
// }



/* 문의내용 글자수 세기 */
const $textArea = $("#vQuestionCont");
const $maxLength = $(".total");
const $counter = $("#counter");

const maxLength = 1500;

$maxLength.text(maxLength);

$textArea.on("input", function () {
    let text = $(this).val();
    let textLength = text.length;
    let check = textLength > maxLength;

    if (check) {
        let max = text;
        $(this).val(max.slice(0, maxLength));
        textLength = $(this).val().length;
    }

    $counter.text(textLength);
});
