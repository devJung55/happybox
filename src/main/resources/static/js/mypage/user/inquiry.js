/* inquiry.html */

/* 문의 내역 클릭 이벤트 */

const $arrows = $(".arrow-0deg");
const $inquiryDetail = $(".ui-accordion-view");

$arrows.each((i, arrow) => {
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
    inquiries.content.forEach(inquiry => {
        const formattedDate = formatDate(new Date(inquiry.createdDate));
        text += `
                    <li class="border-bottom">
                        <a href="javascript:void(0)" class="title-div ui-accordion-click">
                            <div class="subject">
                                <span class="state-wait">답변대기</span>
                                <span class="state-wait complete">답변완료</span>
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
                                    <div class="bottom">
                                        <div class="added-file thumDtlQuestion">
                                            <ul class="thum-list">
                                                <li>
                                                    <a href="javascript:void(0)">
                                                        <img class="thumnail" src="https://file.rankingdak.com/image/RANK/REVIEW/US_RV_IMG1/20230415/IMG1681Teg570545730.png">
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                        <ul class="sep-list type3">
                                            <li><a href="javascript:void(0)" class="btn_delete">삭제</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
                `;
    });
    $inquiryAppend.append(text);
    displayPagination(inquiries.totalPages);
}


function displayPagination(totalPages) {
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



