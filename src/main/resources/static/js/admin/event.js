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
const $showDetail = $(".show-detail");

$showDetail.on('click', function(e){
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

$imgFile.on('change', function(e){
    let input = $(this)
    let $label = $(this).closest('label');
    /* 이미지 지우는 x 버튼 */
    let cancel = $label.siblings();
    let reader = new FileReader();
    /* 이미지 넣을 곳 */
    let attachBtn = $(this).prev();
    /* + 모양이 담긴 i 태그 */
    let iTag = $(this).siblings().find("i");
    console.log($(this).siblings())

    reader.readAsDataURL(e.target.files[0]);
    reader.onload = function(e) {
        let result = e.target.result;
        if(result.includes('image')) {
            iTag.hide();
            attachBtn.append(`<img src='${result}'></img>`);
            cancel.css('display', 'inline-block')

            // 파일 선택을 막음
            input.prop('disabled', true);
        }
    }

    cancel.on('click', function(e){
        let img = attachBtn.find("img")
        e.preventDefault();
        selectedFile = null;
        $imgFile.val("");
        img.remove()
        cancel.css('display', 'none');
        iTag.show();

        // 파일 선택 가능하도록 변경
        input.prop('disabled', false);
    })
})

