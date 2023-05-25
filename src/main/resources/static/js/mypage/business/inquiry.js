/* inquiry.html */

/* 문의 내역 클릭 이벤트 */

function showInquiryDetail(btn) {
    let hidden = $(btn).data("hidden");
    let arrowImg = $(btn).find(".arrow-img");
    $(btn).next().css("display", `${hidden ? 'block' : 'none'}`);
    arrowImg.removeClass(`${hidden ? 'arrow-0deg' : 'arrow-180deg'}`);
    arrowImg.addClass(`${hidden ? 'arrow-180deg' : 'arrow-0deg'}`);

    $(btn).data("hidden", !hidden);
}

/*------------------------------------------------------------------------------*/


myPageDistributorService.inquiryListAjax();
const $inquiryAppend = $(".inquiry__list__append");

function showInquiryList(inquiries) {
    let text = "";

    inquiries.content.forEach(inquiry => {
        let str = "";
        let answer = "";
        let image = "";
        let answerImage = "";

        if(inquiry.inquiryAnswerDTO != null) {
            const formattedDate = formatDate(new Date(inquiry.inquiryAnswerDTO.createdDate));
            answer = `
                    <div class="answer" style="margin-top: 5px;">
                        <div class="info">
                            <span class="name">from. 행복상자</span>
                            <span class="date">${formattedDate}</span>
                        </div>
                        <p class="txt">
                            <span style="white-space:pre-line">${inquiry.inquiryAnswerDTO.inquiryAnswerContent}</span>
                        </p>
                        <div class="bottom" style="margin-bottom: 5px;">
                            <div class="added-file thumDtlQuestion">
                                <ul class="thum-list">
                    `;

            for (let i = 0; i < inquiry.inquiryAnswerDTO.inquiryAnswerFileDTOS.length; i++) {
                answerImage += `
                    <li style="width: 70px; height: 70px; margin-right: 7px;">
                        <a href="javascript:void(0)">
                            <img class="thumnail" src="/image/display?fileName=${inquiry.inquiryAnswerDTO.inquiryAnswerFileDTOS[i].filePath}/${inquiry.inquiryAnswerDTO.inquiryAnswerFileDTOS[i].fileUuid}_${inquiry.inquiryAnswerDTO.inquiryAnswerFileDTOS[i].fileOrgName}">
                        </a>
                    </li>
                `;
            }
            answer += answerImage;

            answer += `
                                </ul>
                            </div>
                        </div>
                    </div>
            `;

            str = "<span class=\"state-wait complete\">답변완료</span>";
        } else {
            answer = "";
            str = "<span class=\"state-wait\">답변대기</span>";
        }

        if(inquiry.inquiryFileDTOS.length != 0) {
            for (let i = 0; i < inquiry.inquiryFileDTOS.length; i++) {
                image += `
                    <li>
                        <a href="javascript:void(0)">
                            <img class="thumnail" src="/image/display?fileName=${inquiry.inquiryFileDTOS[i].filePath}/${inquiry.inquiryFileDTOS[i].fileUuid}_${inquiry.inquiryFileDTOS[i].fileOrgName}">
                        </a>
                    </li>
                `;
            }
        } else {
            image = "";
        }

        const formattedDate = formatDate(new Date(inquiry.createdDate));
        text += `
                    <input type="hidden" name="inquiryId" value="${inquiry.id}">
                    <li class="border-bottom">
                        <a href="javascript:void(0)" onclick="showInquiryDetail(this)" class="title-div ui-accordion-click">
                            <div class="subject">
                `;

        text += str;

        text += `
                                
                                <span class="classify inquiry__title">${inquiry.inquiryTitle}</span>
                            </div>
                            <div class="right">
                                <span class="date">${formattedDate}</span>
                                <img class="arrow-img arrow-0deg" src="/img/mypage/inquiry-arrow.png" width="18" height="18">
                            </div>
                
                        <div class="ui-accordion-view hide" style="display: none;">
                            <div class="question">
                                <div class="detail-div">
                                    <p class="txt">
                                        <span style="white-space:pre-wrap">${inquiry.inquiryContent}</span>
                                    </p>
                                    <div class="bottom" style="margin-bottom: 5px;">
                                        <div class="added-file thumDtlQuestion">
                                            <ul class="thum-list">
                `;

        text += image;

        text += `
                                            </ul>
                                        </div>
                                        <ul class="sep-list type3">
                                            <li><a href="javascript:void(0)" class="btn_delete"></a></li>
                                        </ul>
                                    </div>
                                </div>
                `;

        text += answer;

        text += `
                            </div>
                        </div>
                    </li>
                `;
    });
    $inquiryAppend.empty();
    $inquiryAppend.append(text);
}


