$(".food-area").hide();

/* 파일 등록 */
const foodCalendarDTO = {
    foodCalendarTitle:"",
    foodCalendarDescription: "",
    startDate: "",
    endDate: "",
    foodList: new Array(6)
}

const foodList = {
    foodName:"",
    filePath:"",
    fileUuid:"",
    fileOrgName:"",
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

                foodCalendarDTO.foodList[index] = file;
                console.log(foodCalendarDTO);
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

    let foodCalendarTitle = $("input[name='foodCalendarTitle']").val();
    console.log(foodCalendarTitle);
    let foodCalendarDescription = $("textarea[name='foodCalendarDescription']").val();
    console.log(foodCalendarDescription);
    let startDate = $("input[name='startDate']").val();
    console.log(startDate);
    let endDate = $("input[name='endDate']").val();
    console.log(endDate);
    let foodName = $("input[name='foodName']");
    console.log(foodName);

    foodCalendarDTO.foodCalendarTitle = foodCalendarTitle;
    foodCalendarDTO.foodCalendarDescription = foodCalendarDescription;
    foodCalendarDTO.startDate = startDate;
    foodCalendarDTO.endDate = endDate;
    foodCalendarDTO.foodList.forEach((food,index) => {
        food.foodName = foodName.eq(index).val();
    });
    foodCalendarDTO.foodList = foodCalendarDTO.foodList.filter(e => e !== undefined && e !== null);
    console.log(foodCalendarDTO);


    $.ajax({
        url: '/mypage/welfare/calendar/write',
        data: JSON.stringify(foodCalendarDTO),
        contentType: "application/json; charset=utf-8",
        method: 'post',
        success: function (result) {
            // redirect 경로
            location.href = "/mypage/welfare/subscriber";
        }
    })
});