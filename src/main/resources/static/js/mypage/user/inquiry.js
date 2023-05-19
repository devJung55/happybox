/* inquiry.html */

/* 문의 내역 클릭 이벤트 */

const $arrows = $(".arrow-0deg");
const $inquiryDetail = $(".ui-accordion-view");

$(".inquiry__list__append").on("click", ".arrow-0deg", function() {
    $(this).each((i, arrow) => {
        $(arrow).on("click", function() {
            if($(arrow).hasClass("arrow-0deg")) {
                $(arrow).closest("a").next().css("display", "block");
                $(arrow).removeClass("arrow-0deg");
                $(arrow).addClass("arrow-180deg");
            } else {
                $(arrow).closest("a").next().css("display", "none");
                $(arrow).removeClass("arrow-180deg");
                $(arrow).addClass("arrow-0deg");
            }
        })
    });
});

/* 이미지 모달 이벤트 */

// const $inquiryTitle = $(".inquiry__title");
//
//
// $("#frm").on("click", ".thumnail", function() {
//     const $imgModal = $(".layer-wrap");
//     const $closeButton = $(".btn-x-md2");
//     let src = "";
//
//     if($imageModalAppend.css("display") == "none") {
//         console.log($imageModalAppend)
//         $imageModalAppend.css("display", "block");
//         src = $(this).attr("src");
//         showImageModal(src);
//     }
//
//     $closeButton.on("click", function() {
//         if($imgModal.css("display") == "block") {
//             $imgModal.css("display", "none");
//         }
//     });
// });



/*------------------------------------------------------------------------------*/

myPageService.inquiryListAjax();
const $inquiryAppend = $(".inquiry__list__append");
// const $imageModalAppend = $("#thumDtlPop");
let page = 0;

// function showImageModal(src) {
//     let text = "";
//
//     text = `
//         <div class="layer-wrap" id="popup-sample01" style="display:none">
//             <div class="layer-pop img-view-popup">
//                 <div class="layer-inner">
//                     <div class="layer-content">
//                         <div class="img-auto-slide">
//                             <div class="swiper-container swiper-container-initialized swiper-container-horizontal swiper-container-autoheight">
//                                 <ul class="swiper-wrapper" style="height: 126px; transform: translate3d(0px, 0px, 0px);">
//                                     <li class="swiper-slide swiper-slide-active" style="width: 510px; margin-right: 1px;">
//                                         <img src="${src}">
//                                     </li>
//                                 </ul>
//                             </div>
//                         </div>
//                     </div>
//                     <button type="button" class="btn-x-md2 ui-close-pop">
//                         <img class="close__modal" src="/img/mypage/close-button.webp" width="30">
//                     </button>
//                 </div>
//             </div>
//         </div>
//     `;
//     $imageModalAppend.append(text);
// }

function showInquiryList(inquiries) {
    let text = "";
    let str = "";
    let answer = "";
    let image = "";
    let answerImage = "";

    inquiries.content.forEach(inquiry => {
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
                        <a href="javascript:void(0)" class="title-div ui-accordion-click">
                            <div class="subject">
                `;

        text += str;

        text += `
                                
                                <span class="classify inquiry__title">${inquiry.inquiryTitle}</span>
                            </div>
                            <div class="right">
                                <span class="date">${formattedDate}</span>
                                <img class="arrow-0deg" src="/img/mypage/inquiry-arrow.png" width="18" height="18">
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
                                            <li><a href="javascript:void(0)" class="btn_delete">삭제</a></li>
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
    $inquiryAppend.append(text);
    showPage(inquiries);
}

$(".paging-div").on("click", "a", function(e) {
    e.preventDefault();
    const targetPage = $(this).text();
    page = parseInt(targetPage);
    $inquiryAppend.empty();
    myPageService.inquiryListAjax(page);
});



