/* donate-detail.js */

const setList = $('.form-wrap');

function showList(){
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
    let text ='';
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

