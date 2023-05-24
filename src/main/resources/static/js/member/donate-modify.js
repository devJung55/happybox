/* donate-modify.html */

const optionList = ["음식기부", "봉사활동", "무료배달", "노인복지"];

const insertData = {
    id: donationBoardDTO.id,
    boardTitle: donationBoardDTO.boardTtile,
    boardContent: donationBoardDTO.boardContent,
    donateType: donationBoardDTO.donateType,
    boardRegisterDate: donationBoardDTO.updatedDate,
    donateLocation: donationBoardDTO.donateLocation,
    donationBoardFiles: new Array()
}

const setList = $(".board-form table");
function showList(){
    let text='';
    let donateType = donationBoardDTO.donateType;
    switch (donateType) {
        case "FOOD":
            donateType = "음식기부";
            break;
        case "VOLUNTEER":
            donateType = "봉사활동";
            break;
        case "DELIVERY":
            donateType = "무료배달";
            break;
        case "ETC":
            donateType = "노인복지";
        default:
            donateType = "유형선택";

    }
    let file = donationBoardDTO.donationBoardFiles[0];
    let filePath = '/image/display?fileName=' + file.filePath + "/t_" + file.fileUuid + "_" + file.fileOrgName;

    text +=
        `
    <tr>
                                <th class="text-left">
                                    <div class="in-tb">
                                        기부 유형
                                        <em class="es"></em>
                                    </div>
                                </th>
                                <td class="text-left">
                                    <div class="input-group w-full">
                                        <div class="input-group-form">
                                            <div
                                                    class="ui-select select-box w150"
                                                    id="vCounselType"
                                                    data-value=""
                                            >
                                                <a
                                                        href="javascript:void(0)"
                                                        class="select-value placeholder"
                                                        onclick="openSelectVal()"
                                                ><span id="value">${donateType}</span></a
                                                >
                                                <div class="select-list disNone">
                                                    <ul></ul>
                                                </div>
                                            </div>
                                        </div>
                                        <input
                                                type="text"
                                                name="boardTitle"
                                                class="input-text h40"
                                                placeholder="제목을 입력해주세요"
                                                value="${donationBoardDTO.boardTitle}"
                                        />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th class="text-left">
                                    <div class="in-tb">무료 급식소 선택</div>
                                </th>
                                <td class="text-left">
                                    <div class="input-group w-full">
                                        <input
                                                type="text"
                                                name="donateLocation"
                                                class="input-text h40"
                                                placeholder="무료 급식소를 검색해 주세요."
                                                value="${donationBoardDTO.donateLocation}"
                                                readonly="true"
                                        />
                                        <span class="input-group-btn">
                                                    <button
                                                            type="button"
                                                            id="btn_orderProduct"
                                                            class="btn-form btn-ex-white w150 welfareSearchBtn"
                                                    >
                                                        <span>급식소 검색</span>
                                                    </button>
                                                </span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th class="text-left">
                                    <div class="in-tb">
                                        기부 내용
                                        <em class="es"></em>
                                    </div>
                                </th>
                                <td class="text-left">
                                    <div class="textarea-box-count h230">
                                                <textarea
                                                        id="vQuestionCont"
                                                        placeholder="기부에 대한 내용을 입력해주세요."
                                                        name="boardContent"
                                                >${donationBoardDTO.boardContent}</textarea>
                                        <div class="txt-count">
                                            <span id="counter">${donationBoardDTO.boardContent.length}</span> /
                                            <span class="total">1000</span>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <!-- 첨부파일 영역 -->
                            <tr class="attach-file-line">
                                <th class="text-left" scope="row"></th>
                                <td class="text-left">
                                    <div class="file-add-boxes">
                                        <div class="div-attach-thumb" id="US_RV_IMG_attachThumb">
                                            <button type="button" class="btn-attach-thumb" style="display: none;">
                                                <i class="ico-plus-xlg"></i><span class="blind">파일첨부</span>
                                            </button>
                                            <input class="input_file" name="imgFile" type="file" accept="image/*" style="display: none">
                                            <div class="attach-img" style="display: block;">
                                                <img
                                                src="${filePath}" 
                                                style="width: 68px; height: 68px">
                                                <button type="button" class="btn-x-xs2 btn_del">
                                                    <i class="ico-x-white"></i><span class="blind">삭제</span>
                                                </button>
                                            </div>
                                        </div>
                                        <p class="text-guide-md">
                                            - 최대 15MB 이하의 JPG, PNG, GIF, BMP 파일 첨부 가능합니다.
                                        </p>
                                    </div>
                                </td>
                            </tr>
    `
    setList.append(text);
}

showList();

/* 문의유형 등록 */
function addOptions(params) {
    let $selectOptions = $(".select-list ul");

    let options = `
    <li>
        <a class="inquiryType" onclick="openInquiry()"
            data-value="${optionList[0]}">
            <span>${optionList[0]}</span>
        </a>
    </li>
    <li>
        <a class="inquiryType onclick="openInquiry()"
            data-value="${optionList[1]}">
            <span>${optionList[1]}</span>
        </a>
    </li>
    <li>
        <a class="inquiryType onclick="openInquiry()"
            data-value="${optionList[2]}">
            <span>${optionList[2]}</span>
        </a>
    </li>
    <li>
        <a class="inquiryType onclick="openInquiry()"
            data-value="${optionList[3]}">
            <span>${optionList[3]}</span>
        </a>
    </li>
    `;

    $selectOptions.append(options);
}

addOptions();

const $selectVal = $(".select-value");
const $selectList = $(".select-list");
const $value = $("#value");
/* 문의유형 선택 */
const $inquiryType = $("a.inquiryType");
// $selectVal.on("click", () => disNone($selectList));
function openSelectVal(){
    console.log("들어옴");
    disNone($selectList);
}

// target에 display: none 부여 or 제거
function disNone(target) {
    let check = target.hasClass("disNone");

    if (check) {
        target.removeClass("disNone");
    } else target.addClass("disNone");
}

function openInquiry(){
    console.log("들어옴");
    let val = $inquiryType.data("value");
    $value.text(val);
    disNone($selectList);
}

// $inquiryType.on("click", function (e) {
//     let val = $(this).data("value");
//     $value.text(val);
//     disNone($selectList);
// });

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
const $imgFile = $("input[type='file']");
const $imgDiv = $('.attach-img');
const $fileT = $('.attach-img img');


$fileAttachBtn.on("click", function () {
    let $fileInput = $(this).next();
    $fileInput.click();
});

const fileAjax = (data, index) => {
    $.ajax({
        url: '/image/upload',
        data: data,
        method: 'post',
        processData: false,
        contentType: false,
        success: function (result) {
            if (result) {
                let file = new Object();

                file.filePath = result.paths[0];
                file.fileUuid = result.uuids[0];
                file.fileOrgName = result.orgNames[0];

                insertData.donationBoardFiles[index] = file;
            }
        }
    });
}

$imgFile.each((i, e) => {
    $(e).on('change', function (e) {
        console.log(this.files[0]);
        let file = this.files[0];
        let formData = new FormData();

        formData.append('file', file);

        fileAjax(formData, i);

        $imgDiv.eq(i).css('display', 'block');
        // 기존의 이미지 숨김 처리
        $('.btn-attach-thumb').eq(i).css('display', 'none');
        let reader = new FileReader();
        // 이벤트 타겟의 url을 불러와서
        reader.readAsDataURL(e.target.files[0]);
        // 올리기
        // onload - file이 로드된 후 발생하는 이벤트
        reader.onload = function (e) {
            // 이벤트가 발생된 타겟의 url을 출력해서 result에 담아줌
            let result = e.target.result;
            // result가 이미지라면 result에 담긴 이미지로 설정
            if (result.includes('image')) {
                $fileT.eq(i).attr('src', result)
                // 이미지가 아니라면 no_image.png를 이미지로 설정
            } else {
                $imgDiv.eq(i).css('display', 'none');
            }
        };
    });
});


$(document).ready(function () {
    $('.btn_del').on('click', function (e) {
        let index = $(".btn_del").index($(this));
        e.preventDefault();
        var $attachImg = $(this).parent('.attach-img');
        var $img = $attachImg.find('img');
        var $btnAttachThumb = $(this).closest('.div-attach-thumb').find('.btn-attach-thumb');

        // 누른 버튼의 index 추출
        console.log(index);
        // 그 index 로 insertData 안의 파일 삭제
        insertData.donationBoardFiles[index] = null;

        $img.attr('src', '');
        $attachImg.css('display', 'none');
        $btnAttachThumb.css('display', 'inline-block');


    });
});


$("form[name='form']").on("submit", function (e) {
    e.preventDefault();

    let boardTitle = $("input[name='boardTitle']").val();
    let boardContent = $("textarea[name='boardContent']").val();
    let donateType = $("#value").text();
    switch (donateType) {
        case "음식기부":
            donateType = "FOOD";
            break;
        case "봉사활동":
            donateType = "VOLUNTEER";
            break;
        case "무료배달":
            donateType = "DELIVERY";
            break;
        case "노인복지":
            donateType = "ETC";
        default:
            donateType = "FOOD";

    }
    let donateLocation = $("input[name='donateLocation']").val();

    insertData.boardTitle = boardTitle;
    insertData.boardContent = boardContent;
    insertData.donateType = donateType;
    insertData.donateLocation = donateLocation;

    insertData.donationBoardFiles = insertData.donationBoardFiles.filter(e => e !== undefined && e !== null);

    $.ajax({
        url: '/user-board/donate-modify',
        data: JSON.stringify(insertData),
        contentType: "application/json; charset=utf-8",
        method: 'post',
        success: function (result) {
            // redirect 경로
            location.href = result;
        }
    })
});

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