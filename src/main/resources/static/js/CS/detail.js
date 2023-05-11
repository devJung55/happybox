/* 상세 페이지 */

/* 공지사항 상세 */
const setDetail = $(".notice-content");

function addDetail() {
    let text = "";
    text = `
                 <div class="answer-wrap notice-title">
                    <!-- 답변 영역 -->
                    <div class="answer">
                        <p class="txt">
                            ${noticeDetail.noticeTitle}
                        </p>
                    </div>
                    <div class="text-center">
                        <span>${noticeDetail.createdDate}</span>
                    </div>
                </div>
                 <div class="inner-text">
                      ${noticeDetail.noticeContent}         
                </div>
                <div class="img-area">
        `
    for(let i = 0; i < noticeDetail.noticeFileDTOS.length; i++) {
        text += `
                    <img
                        alt="상세"
                        src="/image/display?fileName=${noticeDetail.noticeFileDTOS[i].filePath}/${noticeDetail.noticeFileDTOS[i].fileUuid}_${noticeDetail.noticeFileDTOS[i].fileOrgName}"
                    />
                `
    }
    text +=
        `
                </div>
                <div class="board-wrap prev-article">
                    <div class="text-center arti">
                        <span class="">이전글</span>
                    </div>
                    <div class="text-left flex1">
                        <a href="javascript:void(0)" class="title-elps">
                            영양성분 전수조사 결과 입장문
                        </a>
                    </div>
                    <div class="text-center">
                        <span>2023.02.03</span>
                    </div>
                </div>
                <div class="board-wrap next-article">
                    <div class="text-center arti">
                        <span class="">다음글</span>
                    </div>
                    <div class="text-left flex1">
                        <a href="javascript:void(0)" class="title-elps">
                            다음글 다음글 다음글
                        </a>
                    </div>
                    <div class="text-center">
                        <span>2023.02.05</span>
                    </div>
                </div>
        `;
    setDetail.append(text);
}
addDetail();

