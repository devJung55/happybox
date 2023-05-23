const $more = $('.food-write-btn');
//
// $delete.on('click', function() {
//     $(this).closest('tr').next('.attach-file-line').remove(); // 첨부파일 영역 삭제
//     $(this).closest('tr').remove(); // 해당 행 삭제
//     foodCount--; // 음식 개수 1 감소
//     if (foodCount < 9) { // 음식 개수가 9보다 작으면 버튼 다시 표시
//       $more.css('display', 'block');
//     }
//   });
//
//   $(document).on('click', '.delete-btn', function() {
//     $(this).closest('tr').next('.attach-file-line').remove(); // 첨부파일 영역 삭제
//     $(this).closest('tr').remove(); // 해당 행 삭제
//     foodCount--; // 음식 개수 1 감소
//     if (foodCount < 9) { // 음식 개수가 9보다 작으면 버튼 다시 표시
//       $more.css('display', 'block');
//     }
//   });
//
//
//
const text =
    `
                <!-- 음식 등록 최대 9개 -->
                                            <tr>
                                                <th class="text-left" scope="row">
                                                    <div class="in-tb">
                                                        음식 이름 <em class="es"><span class="blind"></span></em>
                                                    </div>
                                                </th>
                                                <td class="text-left">
                                                    <div class="input-group w-full">
                                                        <input type="text" name="foodName" id="vQuestionTitle" title="" class="food-name" placeholder="음식 이름을 적어주세요" value=""/>
                                                    </div>
                                                </td>
                                            </tr>

                                            <!-- 첨부파일 영역 -->
                                            <tr class="attach-file-line">
                                                <th class="text-left"></th>
                                                <td class="text-left file-box-wrap">
                                                    <div class="file-add-boxes">
                                                        <div class="div-attach-thumb">
                                                            <label>
                                                                <button class="btn-attach-thumb" type="button" onclick="addFiles(this)">
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
                                                                </button>
                                                                <input
                                                                        class="inputFile"
                                                                        type="file"
                                                                        name="imgFile"
                                                                        style="display: none"
                                                                        accept="image/*"
                                                                />
                                                            </label>
                                                            <span class="x-image">x</span>
                                                        </div>
                                                    </div>
                                                    <p class="text-guide-md">
                                                        - 최대 15MB 이하의 JPG, PNG, GIF, BMP 파일 3장까지
                                                        첨부 가능합니다.
                                                    </p>
                                                </td>
                                            </tr>
                `;
//
var foodCount = 0; // 음식 개수 초기값 0으로 설정
$more.on('click', function () {
    if (foodCount < 9) { // 음식 개수가 9보다 작으면 append 실행
        $('.more-food').append(text);
        foodCount++; // 음식 개수 1 증가
    }
    if (foodCount == 8) {
        $more.css('display', 'none');
    }
});
//
// $delete.on('click', function() {
//     $(this).closest('tr').next('.attach-file-line').remove(); // 첨부파일 영역 삭제
//     $(this).closest('tr').remove(); // 해당 행 삭제
//     foodCount--; // 음식 개수 1 감소
//     if (foodCount < 9) { // 음식 개수가 9보다 작으면 버튼 다시 표시
//       $more.css('display', 'block');
//     }
//   });


/*=============================== 첨부파일 영역  =============================================*/

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
    console.log(path, uuid, file);
    $fileArea.append(text);
}


/*  ==================================== 파일 업로드  ==================================================  */
/* 이미지 삽입 ============================ */
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

/* input 태그 */

// 파일을 담을 배열 선언
let fileList = [];

let file = [];
// 받은 uuid 담을 배열
let uuid = [];
// 받은 path를 담을 배열
let path = [];


/* 파일 넣기 */
/* 한 번에 멀티플로 넣는게 아니라서 각자 파일들을 찾음 */
const $fileAttachBtn = $(".btn-attach-thumb");


/* 파일 추가 버튼 누르면 작동하게 하기 */
function addFiles(obj) {
    let i = $(".btn-attach-thumb").index(obj);
    console.log("i의 수는??")
    console.log(i);
    $(".inputFile").eq(i).click();
    show();
}

function show(){
    $(`input[name=imgFile]`).each((i, e) => {
        $(e).on('change', function () {
            console.log("change 이벤트 ??")
            console.log(i);
            let input = $(this);
            console.log(input);
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
            cancel.on('click', function (e) {
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
    });
}


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

