/* donate-list.html */

const setList = $('.list-append-wrap ol');
let page = 1;
let previousItemCount = 0;

function showList(donationBoardDTOS){
    if (donationBoardDTOS.content.length === 0 && previousItemCount === 0) {
        $(window).off('scroll');
        return;
    }

    // 이전에 추가된 항목 수를 업데이트
    previousItemCount = donationBoardDTOS.content.length;

    let text = '';

    donationBoardDTOS.content.forEach((donationBoard, i) => {
        console.log(donationBoard);
        let boardFiles = donationBoard.donationBoardFiles;
        let filePath = '';
        filePath =  filePath = '/image/display?fileName=' + boardFiles[0].filePath + "/t_" + boardFiles[0].fileUuid + "_" + boardFiles[0].fileOrgName;
        let donateType = `${donationBoard.donateType}`;
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

        text +=
            `
    <li class="donate-li">
            <div class="danate-info-area">
                <div class="inner">
                    <div class="column img">
                        <a href="/user-board/donate-detail/${donationBoard.id}">
                            <img
                                src="${filePath}"
                                alt=""
                            />
                        </a>
                    </div>
                    <div class="column tit">
                        <span class="welfare-name">${donationBoard.welfareName}</span>
                        <p class="title">
                            <a href="/user-board/donate-detail/${donationBoard.id}" class="text-elps2"
                                >${donationBoard.boardTitle}</a
                            >
                        </p>
                        <p class="donate-location">${donationBoard.welfareName}</p>
                    </div>
                    <div class="column count text-left">
                        <span class="num donate-type">${donateType}</span>
                    </div>
                    <div class="column point">
                        <span class="donate-point">${donationBoard.welfarePoint}P</span>
                    </div>
                </div>
            </div>
        </li>
    `
    })
    setList.append(text);
}

// 최신순 클릭 이벤트
$('.last-pop-btn a:first-child').click(function(e) {
    e.preventDefault();

    // 최신순에 'on' 클래스 추가, 인기순에서 'on' 클래스 제거
    $(this).addClass('on');
    $(this).siblings().removeClass('on');

    // 초기화 후 첫 페이지 데이터 로드
    setList.empty();
    page = 1;
    loadDonateBoardList('/user-board/donate-list/recent', { page: page });
    $(window).scroll(function() {
        if($(window).scrollTop() + $(window).height() > $(document).height() * 0.7) {
            page++;
            loadDonateBoardList('/user-board/donate-list/recent', { page: page });
            // 이전에 추가된 항목 수를 업데이트
            previousItemCount = setList.children('.donate-li').length;
        }
    });
});

// 인기순 클릭 이벤트
$('.last-pop-btn a:last-child').click(function(e) {
    e.preventDefault();

    // 인기순에 'on' 클래스 추가, 최신순에서 'on' 클래스 제거
    $(this).addClass('on');
    $(this).siblings().removeClass('on');

    // 초기화 후 첫 페이지 데이터 로드
    setList.empty();
    page = 1;
    loadDonateBoardList('/user-board/donate-list/popular', { page: page });
    $(window).scroll(function() {
        if($(window).scrollTop() + $(window).height() > $(document).height() * 0.7) {
            page++;
            loadDonateBoardList('/user-board/donate-list/popular', { page: page });
            // 이전에 추가된 항목 수를 업데이트
            previousItemCount = setList.children('.donate-li').length;
        }
    });
});

// 리뷰 게시물 목록 로드 함수
function loadDonateBoardList(url, data) {
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        dataType: "json",
        success: function(response) {
            showList(response);
            console.log(response.content.length);
        }
    });
}


// 초기 페이지 로드
loadDonateBoardList('/user-board/donate-list/recent', { page: page });

// 스크롤 이벤트 핸들러
$(window).scroll(function() {
    if($(window).scrollTop() + $(window).height() > $(document).height() * 0.7) {
        page++;
        if ($('.last-pop-btn a:last-child').text() == "최신순") {
            loadDonateBoardList('/user-board/donate-list/recent', { page: page });
        } else if ($('.last-pop-btn a:last-child').text() == "기부순") {
            loadDonateBoardList('/user-board/donate-list/popular', { page: page });
        }

        // 이전에 추가된 항목 수를 업데이트
        previousItemCount = setList.children('.donate-li').length;
    }
});

/*  사진 마우스오버시 스케일 커지는 이벤트 */
$(function () {
    $('.column.img img')
        .on('mouseover', function () {
            var $img = $(this);
            setTimeout(function () {
                $img.css('transform', 'scale(1.07)');
                $img.css('transition', 'all 0.3s');
            }, 250); // set delay
        })
        .on('mouseout', function () {
            $(this).css('transform', 'scale(1)');
            $(this).css('transition', 'all 0.3s');
        });
});