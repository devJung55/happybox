/* 체크 박스 ============================= */
$("#allSelect").click(function() {
    if($("#allSelect").is(":checked")) $("input[name=check]").prop("checked", true);
    else $("input[name=check]").prop("checked", false);
});

$("input[name=check]").click(function() {
    var total = $("input[name=check]").length;
    var checked = $("input[name=check]:checked").length;

    if(total != checked) $("#allSelect").prop("checked", false);
    else $("#allSelect").prop("checked", true); 
});

const pageButton = $(".page-button");


/* 페이지 체크 ============================ */

var $prev = $($(".page-button")[0]);
	
$(".page-button").each((i, e) => {
       var $pageNumber = $(e);
       
       $pageNumber.on("click", function(e) {
          e.preventDefault();
          if($prev == $pageNumber){
              return;
          }
          
          $prev.removeClass("page-button-active");
          $pageNumber.addClass("page-button-active");
          
          $prev = $pageNumber;
       });
    }); 


/* 모달창 =================================== */


/* 상세보기 모달 */
const $showModal = $(".show-modal");

$showModal.on('click', function(e){
    $(".modal").show();
})

/* 상세 모달 닫기 */
const $modalCancel = $("#Capa_1");

$modalCancel.on("click", function(e) {
    $(".modal").hide();
});


/* 공지사항 작성용 모달 */
const $registNotice = $(".add-button");

$registNotice.on('click', function(e) {
    $(".add-modal").show();
})

const $cancelAdd = $("#Capa_2");

$cancelAdd.on("click", function(e) {
    $(".add-modal").hide();
});

/* 복지관 소개글 보기용 모달 */
const $showIntro = $(".show-intro");

$showIntro.on('click', function(e){
        $(".intro-modal").show();
})

const $cancelIntro = $("#Capa_3");

$cancelIntro.on('click', function(e) {
    $(".intro-modal").hide();
})

/* 상세보기 <- captain에 있던 것 */
// globalThis.memberId = "";

// $("table.table").on("click", ".content__detail__btn",  function (e) {
//     globalThis.memberId = $($(this).parent().parent().children()[1]).text();
//     adminMemberService.memberDetail(memberId);
//     $(".user-modal").show();
// });

/* 삭제 모달 */

const $showDelete = $(".delete-button");
const $cancelDelete = $(".cancel-delete");

$showDelete.on('click', function(e){
    $(".delete-modal").show();
})

$cancelDelete.on('click', function(e){
    $(".delete-modal").hide();
})


/* 이미지 삽입 ============================ */

/* input 태그 */
const $imgFile = $("input[name='imgFile']");

// 파일을 담을 배열 선언
let fileList = [];
// 받은 uuid 담을 배열
let Uuid = [];
// 받은 path를 담을 배열
let path = [];

/* 파일 넣기 */
/* 한 번에 멀티플로 넣는게 아니라서 각자 파일들을 찾음 */
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
            input.prop('disabled', false);
            fileList[i] = null;
            Uuid[i] = null;
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
            Uuid[i] = data.uuids;
            path[i] = data.paths;
            showThumb(attachBtn, data)
        }
    })
}

