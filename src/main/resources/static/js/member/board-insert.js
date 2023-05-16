/* recipe-board-insert.html */

$(function() {
    $(".rating-point img").each(function(index) {
      $(this).on("click", function() {
        $(".rating-point img").attr("src", "/img/mypage/rating.png");
        $(this).prevAll().addBack().attr("src", "/img/mypage/rating-pull.png");

          var pullCount = $(".rating-point img[src='/img/mypage/rating-pull.png']").length;
          console.log("rating-pull.png 개수: " + pullCount);
      });
    });
  });

function send() {

}

/* 파일 등록 */
const $fileAttachBtn = $(".btn-attach-thumb");
const $imgFile = $("input[type='file']");
const $imgDiv = $('.attach-img');
const $fileT = $('.attach-img img');


$fileAttachBtn.on("click", function () {
    let $fileInput = $(this).next();
    $fileInput.click();
});

const fileAjax = (data, callback) => {
    $.ajax({
        url: '/image/upload',
        data: data,
        method: 'post',
        processData: false,
        contentType: false,
        success: function (result) {
            if(result){
                callback(result);
            }
        }
    })
}

$imgFile.each((i, e) => {
    $(e).on('change', function (e) {
        console.log(this.files[0]);
        let file = this.files[0];
        let formData = new FormData();

        formData.append('file', file);

        fileAjax(formData, (result) => {
            console.log(result);
        });

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
                $imgDiv.eq(i).css('dispaly', 'none');
            }
        };
    });
});

// FileList.prototype.forEach = Array.prototype.forEach;
//
// const $imgDiv = $('.attach-img');
// let files = [];
// if(fileDTOS != null && fileDTOS != undefined){
//     fileDTOS.forEach((file, i) => {
//         files.push(file);
//     });
// }
//
// $("input[type=file]").on("change", function () {
//     const $files = $("input[type=file]")[0].files;
//     let formData = new FormData();
//
//     $($files).each((i, file) => {
//         files.push(file);
//     })
//
//     files.forEach((file, e) => {
//         formData.append("file", file);
//     })
//
//
//     $.ajax({
//         url: "/image/upload",
//         type: "post",
//         data: formData,
//         contentType: false,
//         processData: false,
//         success: function (uuids) {
//             globalThis.uuids = uuids;
//             console.log(uuids);
//             $files.forEach((file, i) => {
//                 if (file.type.startsWith("image")) {
//                     let text = `
//                         <img
//                                     src="/image/display?fileName=${toStringByFormatting(new Date())}/t_${uuids[i]}_${file.name}"
//                                     style="width: 68px; height: 68px"
//                                   /><button
//                                     type="button"
//                                     class="btn-x-xs2 btn_del"
//                                   >
//                                     <i class="ico-x-white"></i><span class="blind">삭제</span>
//                                   </button>
//                 `;
//                     $imgDiv.append(text);
//                 }
//             });
//         }
//     });
// });
//
//
// /*****************************************************/
// function leftPad(value) {
//     if (value >= 10) {
//         return value;
//     }
//
//     return `0${value}`;
// }
//
// function toStringByFormatting(source, delimiter = '/') {
//     const year = source.getFullYear();
//     const month = leftPad(source.getMonth() + 1);
//     const day = leftPad(source.getDate());
//
//     return [year, month, day].join(delimiter);
// }


