

/* 목록 불러오기 */

const $NoticeListWrap = $('.notice-list-wrap');
function showNoticeList(noticeLists) {
    let text = "";
    noticeLists.forEach((noticeList, i) => {
        const formattedDate = formatDate(new Date(noticeList.createdDate));
        text += `
            <tr class="list-content">
                <td>
                    <label class="check-label">
                        <input type="checkbox" name="check">
                    </label>
                </td>
                <td id="notice-id" class="show-modal">${noticeList.id}</td>
                <td class="show-modal">${noticeList.noticeTitle}</td>
                <td class="show-modal">${noticeList.noticeContent}</td>
                <td class="show-modal">관리자</td>
                <td class="show-modal">${formattedDate}</td>
            </tr>
        `;
    })
    $NoticeListWrap.append(text);
}

/* 페이지 설정 */
globalThis.page = 1;

/* 페이지 버튼 만들기 */
const $contentWrap = $('.paging-wrap');

function pagination(pageDTO) {
    let text =  "";

    if (pageDTO.total == 0) {
        text = ``;
    } else {
        text = `
                <!-- 페이지 버튼 -->
                <div class="page-button-box-layout">
                    <div class="page-button-box">
                        <!-- 페이지 번호 -->
                `
        if(pageDTO.prev) {
            text += `
                        <!-- 페이지 개수 10개 이상 -->
                        <div class="">
                            <div class="page-button-margin">
                                <div>
                                    <a href="javascript:setPage(${pageDTO.startPage - 1})" ><img src="https://cdn3.iconfinder.com/data/icons/google-material-design-icons/48/ic_keyboard_arrow_left_48px-128.png" class="left-button"></a>
                                </div>
                            </div>
                        </div>
                    `
        }
        for(let i = pageDTO.startPage; i <= pageDTO.endPage; i++) {
            if (globalThis.page == i) {
                /* 페이지가 내가 지금 데이터를 요청한 페이지인지 검사 */
                text += `<div onclick="setPage(${i})" class="page-button page-button-active"> `
            } else {
                text += ` <div onclick="setPage(${i})" class="page-button"> `
            }
            text += `
                    <div class="page-button-margin">
                            <div>
                                <span>${i}</span>
                            </div>
                        </div>
                    </div>
                `
        }

        if (pageDTO.next) {
            text += `
                        <div class="">
                            <div class="page-button-margin">
                                <div>
                                    <a href="javascript:setPage(${pageDTO.endPage + 1})"><img src="https://cdn3.iconfinder.com/data/icons/google-material-design-icons/48/ic_keyboard_arrow_right_48px-128.png" class="right-button"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 페이지 버튼 끝 -->
            `
        }
    }
    $contentWrap.append(text);
}

/* 데이터 요청할 페이지 */
function setPage(page) {
    globalThis.page = page;
    adminNoticeService.getNoticeList();
}

/* ajax 모듈 */

let adminNoticeService = (function() {

    /* 목록 불러오기 */
    function getNoticeList() {
        $.ajax({
            url: `/admin/admin-noticeList/${globalThis.page}`,
            success: function(data) {
                $NoticeListWrap.empty();
                showNoticeList(data.noticeLists);
                $contentWrap.empty();
                pagination(data.pageDTO)
            }
        })
    }

    /* 상세보기 */
    function noticeDetail(id) {
        $.ajax({
            url: "/admin/notice-detail",
            data: {"id" : id},
            success: function(noticeDTO) {
                showNoticeDetail(noticeDTO);
            },
            error: function(a, b, c) {
                console.log(a);
                console.log(b);
                console.log(c);
            }
        })
    }

    /* 수정 */

    /* 삭제 */
    function noticeDelete(id) {
        $.ajax({
            url: "/admin/notice-delete",
            type: "delete",
            data: {"id": id},
            success: function() {
                if ($NoticeListWrap.children() == null) {
                    globalThis.page = globalThis.page - 1;
                    adminNoticeService.getNoticeList();
                    return;
                }
                adminNoticeService.getNoticeList();
            }
        })
    }

    return {getNoticeList: getNoticeList, noticeDetail: noticeDetail, noticeDelete: noticeDelete}

})();

/* =========================== 상세보기 모달 =============================== */

$("table.table").on('click','.show-modal' , function(e){
    let id = $($(this).parent().children()[1]).text();
    console.log(id)
    $(".modal").show();
    adminNoticeService.noticeDetail(id);
});

/* =========================== 공지사항 삭제 =============================== */

const $deleteButton = $('#confirm-delete');
let checkBoxArr = [];

$deleteButton.on('click', function () {
    var $checkBoxs = $('.list-content input[type="checkbox"]');

    $checkBoxs.each((i, v) => {
        if (v.checked) {
            checkBoxArr.push($('#notice-id').eq(i).text())
        }
    });

    for (let i = 0; i < checkBoxArr.length; i++) {
        adminNoticeService.noticeDelete(checkBoxArr[i])
    }
});


/* ========================= 상세 모달 채우기 =================================== */

function showNoticeDetail(noticeDTO) {
    const formattedDate = formatDate(new Date(noticeDTO.updatedDate));
    let text = "";
    const $detailWrap = $('.content-detail');

    text = `
        <div class="content-img-wrapper">
        `
    if (noticeDTO.noticeFileDTOS != null) {
        for(let i = 0; i < noticeDTO.noticeFileDTOS.length; i++) {
            text += `
                <label>
                    <div class="content-img list-img img__width">
                        <img
                            src="/image/display?fileName=${noticeDTO.noticeFileDTOS[i].filePath}/${noticeDTO.noticeFileDTOS[i].fileUuid}_${noticeDTO.noticeFileDTOS[i].fileOrgName}"
                        />
                    </div>
                    <input type="file" name="file" accept="image/*" style="display: none" />
                </label>
            `
        }
    }
    text +=
        `
        </div>
        <ul class="content-list-wrap">
            <li class="content-list">
                <span>제목</span>
                <div class="content-input">
                    <input type="text" value="${noticeDTO.noticeTitle}" />
                </div>
            </li>
            <li class="content-list">
                <span>날짜</span>
                <div class="content-input">
                    <input type="text" value="${formattedDate}" />
                </div>
            </li>
            <li class="content-list txt-align">
                <span>내용</span>
                <div class="content-input">
                    <textarea class="normal-textarea" cols="30" rows="7" name="" >${noticeDTO.noticeContent}</textarea>
                </div>
            </li>
        </ul>
        <div class="button-box">
            <button type="button" class="update-button">수정하기</button>
        </div>
    `;
    $detailWrap.empty();
    $detailWrap.append(text);
}


// 맨 처음 화면에 데이터 뿌려주기
adminNoticeService.getNoticeList();