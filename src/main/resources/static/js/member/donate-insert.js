/* donate-insert.html */

const $selectVal = $(".select-value");
const $selectList = $(".select-list");
const $value = $("#value");

const optionList = ["음식기부", "봉사활동", "무료배달", "노인복지"];

const insertData = {
    boardTitle:"",
    boardContent: "",
    donateType: "",
    donateLocation: "",
    donationBoardFiles: new Array(3)
}

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
            if(result){
                let file = new Object();

                file.filePath = result.paths[0];
                file.fileUuid = result.uuids[0];
                file.fileOrgName = result.orgNames[0];

                insertData.donationBoardFiles[index] = file;
                console.log(insertData);
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

$(document).ready(function() {
    $('.btn_del').on('click', function(e) {
        e.preventDefault();
        var $attachImg = $(this).parent('.attach-img');
        var $img = $attachImg.find('img');
        var $btnAttachThumb = $(this).closest('.div-attach-thumb').find('.btn-attach-thumb');

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
            break;

    }
    let donateLocation = $("input[name='donateLocation']").val();

    insertData.boardTitle = boardTitle;
    insertData.boardContent = boardContent;
    insertData.donateType = donateType;
    insertData.donateLocation = donateLocation;

    insertData.donationBoardFiles = insertData.donationBoardFiles.filter(e => e !== undefined && e !== null);

    console.log(insertData.donationBoardFiles.length);
    if (
        boardTitle == "" ||
        boardContent == "" ||
        donateType == "유형선택" ||
        donateLocation == "" ||
        insertData.donationBoardFiles.length == 0
    ) {
        alertModal("모든 정보를 입력해주세요.");
        return false; // submit 막기
    }

    $.ajax({
        url: '/user-board/donate-insert',
        data: JSON.stringify(insertData),
        contentType: "application/json; charset=utf-8",
        method: 'post',
        success: function (result) {
            // redirect 경로
            location.href = "/user-board/donate-list";
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

/* 모달창 */

function alertModal(errorMsg) {
    $("div#content-wrap").html(errorMsg)
    $("div.modal").css("display", "flex").hide().fadeIn(500);
    setTimeout(function () {
        $("div.modal").fadeOut();
    }, 2000);
}