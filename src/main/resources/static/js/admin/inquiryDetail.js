/* detail */

const answerData = {
    inquiryAnswerContent : null,
    inquiryAnswerFileDTOS: new Array(3)
}

/* 페이지 로딩 시 */
// 이미지
$(".inquire-img img").each((i, e) => {
    let filePath = "/img/admin/admin_no_img.png";
    let file = $inquiry.inquiryFileDTOS[i];

    // i 번 째 파일이 존재하면
    if (file) {
        filePath = `/image/display?fileName=${file.filePath}/t_${file.fileUuid}_${file.fileOrgName}`;
    }

    $(e).attr("src", filePath);
});

// 문의 제목
$("input[name='inquiryTitle']").val($inquiry.inquiryTitle);
// 문의 내용
$("textarea[name='inquiryContent']").val($inquiry.inquiryContent);
// 작성자
$("input[name='userId']").val($inquiry.userId);
// 작성일
$("input[name='createdDate']").val($inquiry.createdDate.toString().split("T")[0]);

// 파일
$(`input[name='answerFile']`).each((i, e) => {
    $(e).on('change', function () {
        let $input = $(this);
        let $label = $(this).closest('label');
        /* 이미지 지우는 x 버튼 */
        let cancel = $label.siblings();

        /* 이미지 넣을 곳 */
        let attachBtn = $(this).prev();

        /* + 모양이 담긴 i 태그 */
        let iTag = $(this).siblings().find("i");
        iTag.hide();
        // 파일 선택 막아주기
        $input.prop('disabled', true);
        // x버튼도 보이게 해준다
        cancel.css('display', 'inline-block')

        // 파일 찾아오기
        const file = $input[0].files[0];
        let formData = new FormData();

        formData.append("file", file);

        // upload ajax 실행 및 썸네일까지 올리기
        getFilePath(formData, attachBtn, i);

        // x 버튼을 눌렀다면
        cancel.on('click', function(e) {
            // 썸네일 이미지
            let img = attachBtn.find("img")
            e.preventDefault();
            // 선택한 파일이 없게 만들기
            // selectedFile = null;
            $imgFile.eq(i).val("");
            img.remove()
            // x 버튼 없애기
            cancel.css('display', 'none');
            iTag.show();
            // 파일 선택 가능하도록 변경
            $input.prop('disabled', false);
            answerData.inquiryAnswerFileDTOS[i] = null;
        });
    })
});

function getFilePath(formData, attachBtn, i) {
    $.ajax({
        url: "/image/upload",
        type: "post",
        data: formData,
        contentType: false,
        processData: false,
        success: function (data) {
            let file = new Object();
            file.fileUuid = data.uuids[0];
            file.filePath = data.paths[0];
            file.fileOrgName = data.orgNames[0];

            answerData.inquiryAnswerFileDTOS[i] = file;

            showThumb(attachBtn, file);
        }
    })
}

function showThumb(attachBtn, file) {
    attachBtn.append(`<img src='/image/display?fileName=${file.filePath}/t_${file.fileUuid}_${file.fileOrgName}'></img>`)
}

// 답변 달기
$(".regist-button").on("click", function () {
    let content = $("textarea[name='inquiryAnswerContent']").val();

    if (content === "") return;

    answerData.inquiryAnswerFileDTOS = answerData.inquiryAnswerFileDTOS.filter(e => e !== undefined && e !== null);
    answerData.inquiryAnswerContent = content;
    console.log(answerData);
    $.ajax({
        type: "POST",
        url: `/admin/inquiry/answer/save/${$inquiry.id}`,
        data: JSON.stringify(answerData),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (response) {
            location.href = "/admin/admin-inquiryList";
        }
    });
});