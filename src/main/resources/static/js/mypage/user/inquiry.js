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

const $imgModal = $(".layer-wrap");
const $imgFiles = $(".added-file");
const $closeButton = $(".btn-x-md2");
const $inquiryTitle = $(".inquiry__title");

$imgFiles.on("click", function() {
    if($imgModal.css("display") == "none") {
        $imgModal.css("display", "block");
    }
});

$closeButton.on("click", function() {
    if($imgModal.css("display") == "block") {
        $imgModal.css("display", "none");
    }
});


/*------------------------------------------------------------------------------*/


const $inquiryAppend = $(".inquiry__list__append");
let page = 0;

function showInquiryList(inquiries) {
    let text = "";
    let str = "";
    let answer = "";
    let image = "";

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
    displayPaginationInquiry(inquiries.totalPages);
}


function displayPaginationInquiry(totalPages) {
    const $pagination = $(".pagination");
    $pagination.empty();

    if (page > 0) {
        $pagination.append(`<a href="javascript:void(0)" class="btn-page prev"><span class="blind2">&lt;</span></a>`);
    }

    for (let i = 1; i <= totalPages; i++) {
        if (i === page + 1) {
            // 현재 페이지를 텍스트로 표시
            $pagination.append(`<a href="javascript:void(0)" id="prev" class="arrow current"><span>${i}</span></a>`);
        } else {
            // 다른 페이지는 a 태그로 표시
            $pagination.append(`<a href="#" class="current"><span>${i}</span></a>`);
        }
    }

    if (page < totalPages - 1) {
        $pagination.append(`<a href="javascript:void(0)" id="next" class="arrow btn-page next"><span class="blind2">&gt;</span></a>`);
    }
}

$(".pagination").on("click", "a", function(e) {
    e.preventDefault();
    const targetPage = $(this).text();
    page = parseInt(targetPage);
    $inquiryAppend.empty();
    myPageService.inquiryListAjax(page);
});



