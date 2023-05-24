/* donate-detail.js */

const setList = $('.form-wrap');

function showList() {
    let date = donate.boardRegisterDate.split("T")[0];
    let donateType = `${donate.donateType}`;
    switch (donateType) {
        case "FOOD":
            donateType = "음식기부";
            break;
        case "VOLUNTEER":
            donateType = "봉사활동";
            break;
        case "DELIVERY":
            donateType = "무료배달";
            break;
        case "ETC":
            donateType = "노인복지";
        default:
            donateType = "음식기부";

    }
    console.log(donate);
    let text = '';
    text +=
        `
        <div class="notice-content">
                                    <div class="answer-wrap notice-title">
                                        <!-- 답변 영역 -->
                                        <div class="answer">
                                            <p class="txt">
                                                제목:
                                                <span class="donate-title">${donate.boardTitle}</span>
                                            </p>
                                        </div>
                                        <div class="text-center">
                                            <span>${date}</span>
                                        </div>
                                    </div>
                                    <div class="plus-info">
                                        <div class="plus-cell">
                                            기부 장소:
                                            <span class="soupkitchen">${donate.donateLocation}</span>
                                        </div>
                                        <div class="plus-cell">
                                            기부 유형:
                                            <span class="donate-type">${donateType}</span>
                                        </div>
                                    </div>
                                    <div class="plus-info">
                                        <div class="plus-cell">
                                            복지관 명:
                                            <span class="welfare-name">${donate.welfareName}</span>
                                        </div>
                                        <div class="plus-cell">
                                            연락처:
                                            <span class="welfare-phone">${donate.welfarePhone}</span>
                                        </div>
                                        `
    if ($welfareId == donate.welfareId){
        text +=
            `
                                        <a href='/user-board/donate-modify/${donate.id}' class="update-btn" style="
                                                width: 20px;
                                                height: 20px;
                                                position: absolute;
                                                right: 81px;
                                                margin-top: 8px;
                                            ">
                                          <svg class="write-button-icon" style="width: 100%;enable-background: new 0 0 1696.162 1696.143;" version="1.1" viewBox="0 0 1696.162 1696.143" width="1696.162px" xml:space="preserve" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                            <g id="pen">
                                              <path d="M1648.016,305.367L1390.795,48.149C1359.747,17.098,1318.466,0,1274.555,0c-43.907,0-85.188,17.098-116.236,48.148   L81.585,1124.866c-10.22,10.22-16.808,23.511-18.75,37.833L0.601,1621.186c-2.774,20.448,4.161,41.015,18.753,55.605   c12.473,12.473,29.313,19.352,46.714,19.352c2.952,0,5.923-0.197,8.891-0.601l458.488-62.231   c14.324-1.945,27.615-8.529,37.835-18.752L1648.016,537.844c31.049-31.048,48.146-72.33,48.146-116.237   C1696.162,377.696,1679.064,336.415,1648.016,305.367z M493.598,1505.366l-350.381,47.558l47.56-350.376L953.78,439.557   l302.818,302.819L493.598,1505.366z M1554.575,444.404l-204.536,204.533l-302.821-302.818l204.535-204.532   c8.22-8.218,17.814-9.446,22.802-9.446c4.988,0,14.582,1.228,22.803,9.446l257.221,257.218c8.217,8.217,9.443,17.812,9.443,22.799   S1562.795,436.186,1554.575,444.404z"></path>
                                            </g>
                                            <g id="Layer_1"></g>
                                          </svg>
                                        </a>
                                        <button type="button" class="delete-btn" onclick="deleteModal()" data-id="${donate.id}"
                                                style="
                                                        width: 20px;
                                                        height: 20px;
                                                        position: absolute;
                                                        right: 0;
                                                        margin: 7px 28px 0 0;
                                                        ">
                                          <svg viewBox="0 0 448 512" class="delete-button-icon" xmlns="http://www.w3.org/2000/svg">
                                            <path d="M432 80h-82.38l-34-56.75C306.1 8.827 291.4 0 274.6 0H173.4C156.6 0 141 8.827 132.4 23.25L98.38 80H16C7.125 80 0 87.13 0 96v16C0 120.9 7.125 128 16 128H32v320c0 35.35 28.65 64 64 64h256c35.35 0 64-28.65 64-64V128h16C440.9 128 448 120.9 448 112V96C448 87.13 440.9 80 432 80zM171.9 50.88C172.9 49.13 174.9 48 177 48h94c2.125 0 4.125 1.125 5.125 2.875L293.6 80H154.4L171.9 50.88zM352 464H96c-8.837 0-16-7.163-16-16V128h288v320C368 456.8 360.8 464 352 464zM224 416c8.844 0 16-7.156 16-16V192c0-8.844-7.156-16-16-16S208 183.2 208 192v208C208 408.8 215.2 416 224 416zM144 416C152.8 416 160 408.8 160 400V192c0-8.844-7.156-16-16-16S128 183.2 128 192v208C128 408.8 135.2 416 144 416zM304 416c8.844 0 16-7.156 16-16V192c0-8.844-7.156-16-16-16S288 183.2 288 192v208C288 408.8 295.2 416 304 416z"></path>
                                          </svg>
                                        </button>
                                        `
    }


        text +=
                                            `
                       
                       
                                    </div>
                                    <div class="inner-text">
                                        <div class="donate-content">
                                            ${donate.boardContent}
                                        </div>
                  `
    donate.donationBoardFiles.forEach((file, i) => {
        console.log(file);
        let filePath = '/image/display?fileName=' + file.filePath + "/t_" + file.fileUuid + "_" + file.fileOrgName;
        text +=
            `
                                        <div class="donate-img-area">
                                            <img
                                                alt="상세"
                                                src="${filePath}"
                                            />
                                        </div>
                   `
    })
    text +=
        `
                                    </div>
                                    
        `
    setList.append(text);
}

showList();

/* 게시글 삭제 */
function deleteModal(){
    $("#check-modal").css("display", "block");
}

// 닫기 버튼을 클릭했을 때
$(".close").on("click", function () {
    $(this).closest(".modal").css("display", "none");
});
const goDelete = `/user-board/donate-detail/delete/${donate.id}`;

/* 게시글 삭제 */
$("#modal-yesBtn").on("click", function () {
    var deleteBtn = $(this);

    $.ajax({
        url: goDelete,
        type: 'DELETE',
        contentType: "application/json; charset=utf-8",
        success: function() {
            location.href = "/user-board/donate-list";
        },
        error: function(error) {
            console.log(error);
        }
    });
});

const goUpdate = `/user-board/donate-modify/${donate.id}`;

/* 게시글 수정폼 */
$(".update-btn").on("click", function () {
    var updateBtn = $(this);

    $.ajax({
        url: goUpdate,
        type: 'GET',
        contentType: "application/json; charset=utf-8",
        success: function(result) {
        },
        error: function(error) {
        }
    });
});