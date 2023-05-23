/* 상세 페이지 */

/* 공지사항 상세 */
const setDetail = $(".notice-content");

function addDetail() {
    const formattedDate = formatDate(new Date(noticeDetail.createdDate));
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
                        <span>${formattedDate}</span>
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
    setDetail.append(text);
}
addDetail();

