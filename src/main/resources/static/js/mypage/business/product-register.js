/* 파일 등록 */
const insertData = {
    productName:"",
    productCategory: "",
    productInfo: "",
    productPrice: "",
    productStock: "",
    productFileDTOS: new Array(3)
}


const $fileAttachBtn = $(".btn-attach-thumb");
const $imgFile = $("input[name='imgFile']");
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

    let productName = $("input[name='productName']").val();
    let productCategory = $("#foodCategory option").val();
    let productInfo = $("textarea[name='productInfo']").val();
    let productPrice = $("input[name='productPrice']").val();
    let productStock = $("input[name='productStock']").val();

    insertData.productName = productName;
    insertData.productCategory = productCategory;
    insertData.productInfo = productInfo;
    insertData.productPrice = productPrice;
    insertData.productStock = productStock;

    insertData.productFileDTOS = insertData.productFileDTOS.filter(e => e !== undefined && e !== null);

    $.ajax({
        url: '/distributor/register',
        data: JSON.stringify(insertData),
        contentType: "application/json; charset=utf-8",
        method: 'post',
        success: function (result) {
            // redirect 경로
            location.href = "/distributor/product-list";
        }
    })
});