/* 라이더 등록 */


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


/* =======================   파일 추가  =================================   */
/* input 태그 */
const $imgFile = $("input[name='imgFile']");

const $fileArea = $('.attach-file-line');
function addFile() {
    /* 만약 파일이 배열 안에 없다면 만들어진 input을 모두 없앰 */
    let text = "";
    text += `
                <input type="hidden" name="filePath" value="${path}" />
                <input type="hidden" name="fileUuid" value="${uuid}" />
                <input type="hidden" name="fileOrgName" value="${file}" />
            `;
    console.log("========================================")
    console.log(path,uuid,file);
    $fileArea.append(text);
}


/*  ==================================== 파일 업로드  ==================================================  */
/* 이미지 삽입 ============================ */

/* input 태그 */

// 파일을 담을 배열 선언
let fileList = [];

let file = "";
// 받은 uuid 담을 배열
let uuid = "";
// 받은 path를 담을 배열
let path = "";


/* 파일 넣기 */
/* 한 번에 멀티플로 넣는게 아니라서 각자 파일들을 찾음 */
const $fileAttachBtn = $(".btn-attach-thumb");

/* 파일 추가 버튼 누르면 작동하게 하기 */
$fileAttachBtn.on("click", function () {
    let $fileInput = $(this).next();
    $fileInput.click();
});

$(`input[name=imgFile]`).each((i, e) => {
    $(e).on('change', function () {
        let input = $(this)
        let $label = $(this).closest('label');
        /* 이미지 지우는 x 버튼 */
        let cancel = $label.siblings();

        /* 이미지 넣을 곳 */
        let attachBtn = $(this).prev();

        /* + 모양이 담긴 i 태그 */
        let iTag = $(this).siblings().find("i");
        iTag.hide();
        // 파일 선택 막아주기
        input.prop('disabled', true);
        // x버튼도 보이게 해준다
        cancel.css('display', 'inline-block')

        // 파일 찾아오기
        const $files = $(`input[name=imgFile]`)[i].files;
        let formData = new FormData();

        if ($files.length > 0) {
            // 배열의 각 i번째에 해당 파일을 집어넣는다
            fileList[i] = $files
        }
        formData.append("file", fileList[i][0])

        // upload ajax 실행 및 썸네일까지 올리기
        getFilePath(formData, attachBtn);

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
            input.prop('disabled', false);
            fileList[i] = null;
            uuid[i] = null;
            path[i] = null;
        })
    })
})

//  썸네일 만드는 기능
function showThumb(attachBtn, data) {
    attachBtn.append(`<img src='/image/display?fileName=${data.paths}/t_${data.uuids}_${data.orgNames}'></img>`)
}

// 파일을 올리면 path 받아오는 ajax
function getFilePath(formData, attachBtn, i) {
    $.ajax({
        url: "/image/upload",
        type: "post",
        data: formData,
        contentType: false,
        processData: false,
        success: function (data) {
            console.log(data)
            uuid = data.uuids;
            path = data.paths;
            file = data.orgNames;
            showThumb(attachBtn, data)
            addFile();
        }
    })
}

$('.btn-primary').on('click', function () {
    $('#riderForm').submit();
})


/* = ============================== 휴대폰 번호 정규화 ================================================*/
const phoneRegex = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
const $phoneInput = $('#rider-phone');

$phoneInput.on('blur', function () {
   let value = $(this).val();
    phoneRegex.test(value);
    $(this).val(value.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`));
})