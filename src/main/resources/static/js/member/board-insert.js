
const insertData = {
    boardTitle:"",
    boardContent: "",
    welfareName: "",
    reviewBoardFiles: new Array(3)
}

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

                insertData.reviewBoardFiles[index] = file;
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
                $imgDiv.eq(i).css('dispaly', 'none');
            }
        };
    });
});

$(document).ready(function() {
    $('#image').click(function() {
        $('.input_file').click();
    });

    $('.input_file').change(function() {
        var file = this.files[0];
        // 파일 처리 로직을 수행합니다.
    });
});

$("form[name='form']").on("submit", function (e) {
    e.preventDefault();

    let boardTitle = $("input[name='boardTitle']").val();
    let welfareName = $("input[name='welfareName']").val();
    let boardContent = $("textarea[name='boardContent']").val();

    insertData.boardTitle = boardTitle;
    insertData.welfareName = welfareName;
    insertData.boardContent = boardContent;

    $.ajax({
        url: '/user-board/review-board-insert',
        data: JSON.stringify(insertData),
        contentType: "application/json; charset=utf-8",
        method: 'post',
        success: function (result) {
            // redirect 경로
            location.href = "/user-board/review-board-list";
        }
    })
});
