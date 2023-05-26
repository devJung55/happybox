/* Header.js */


/* ============================== 선언부  ============================ */

/* ===================== 복지마켓 / 유통마켓 효과 ======================*/
$(document).ready(function () {
    // 초기 상태에서 product-market에 글씨 굵게 설정
    if (window.location.href.includes('/market')) {
        $('#product-market').css('font-weight', '700');
    } else {
        $('#welfare-market').css('font-weight', '700');
    }

    // welfare-market을 클릭했을 때 글씨 굵게 설정하고 product-market 글씨 굵기 초기화
    $('#welfare-market').click(function () {
        $(this).css('font-weight', '700');
        $('#product-market').css('font-weight', '');
        $('#product-market').removeClass('selected');
        $(this).addClass('selected');
    });

    // product-market을 클릭했을 때 글씨 굵게 설정하고 welfare-market 글씨 굵기 초기화
    $('#product-market').click(function () {
        $(this).css('font-weight', '700');
        $('#welfare-market').css('font-weight', '');
        $('#welfare-market').removeClass('selected');
        $(this).addClass('selected');
    });

    // 마우스 오버시 글씨 굵게 설정
    $('#welfare-market, #product-market').mouseover(function () {
        if (!$(this).hasClass('selected')) {
            $(this).css('font-weight', '700');
        }
    });

    // 마우스 아웃시 버튼이 선택되어 있지 않은 경우에만 글씨 굵기 초기화
    $('#welfare-market, #product-market').mouseout(function () {
        if (!$(this).hasClass('selected')) {
            $(this).css('font-weight', '');
        }
    });
});


/* ===================== 마이 메뉴 효과 ======================*/

$(document).ready(function () {
    $('.my-menu li').on('mouseover', function () {
        $(this).css('transform', 'scale(1.05)');
    });
    $('.my-menu li').on('mouseout', function () {
        $(this).css('transform', 'scale(1.0)');
    });
});


/* ===================== 네비 효과 ======================*/

$(document).ready(function () {
    $('.gnb li').on('mouseover', function () {
        $(this).css('transform', 'scale(1.05)');
        $(this).find('span').css('font-weight', 'bold');
    });
    $('.gnb li').on('mouseout', function () {
        $(this).css('transform', 'scale(1)');
        $(this).find('span').css('font-weight', '');
    });
});

/* ========================= 유틸 효과============== ======================*/

$(document).ready(function () {
    $('.util li').on('mouseover', function () {
        $(this).css('transform', 'scale(1.03)');
        $(this).find('a').css('font-weight', 'bold');
    });
    $('.util li').on('mouseout', function () {
        $(this).css('transform', 'scale(1)');
        $(this).find('a').css('font-weight', '');
    });
});

/* ========================= fixed 용 =============================== */
$('document').ready(function () {
    $(window).scroll(function () {
        let position = $(window).scrollTop(); // 현재 스크롤바의 위치값을 반환합니다.

        if (position >= 120) {
            $('.gnb-wrap').addClass('fixed');
        } else {
            $('.gnb-wrap').removeClass('fixed');
        }
    });
});

/*============================== 장바구니 모달용 =============================== */
function showCartModal() {
    $(".productCart-modal").css("display", "flex");
}

$(window).on("click", function (event) {
    if (!$(event.target).is('.productCart-modal')) {
        $(".productCart-modal").css("display", "none");
    }
});
/* ============================ 복지관 검색 ====================================== */
$("form[name='subSearch']").on("submit", function (e) {
    e.preventDefault();
    let $searchVal = $(this).find(".input-search").val();
    location.href = `/welfare/list?search=${$searchVal}`;
});

/* ============================ 채팅방 ====================================== */

// 전체 채팅방 감싸는 div
const $chatWindow = $(".chat-window");
// 복지관 검색 및 채팅방 내역 조회공간
const $chatContent = $(".chat-content");
// 복지관 더보기
const $moreWelfares = $(".moreWelfares");
// 복지관 더보기 pageNumber
let pageNum = 1;
// 마지막 페이지인지
let isLast = false;
// 요청 URL
const WELFARE_REQ_URL = "/welfare/chat/list/search";
const CHAT_LIST_REQ_URL = "/chat/list";

// 복지관 이름으로 검색 form
const $searchWelfareForm = $(".chat-search-wrap");

// 메시지 input
const $msgInput = $(".message-input");
// 채팅방
const $chatRoomContainer = $(".chat-room-container");

// 채팅방 불러오기 btn
const $chatShowBtn = $(".chat-show-btn");

// 채팅방 닫기 btn
const $chatClose = $(".chat-close button");

// 뒤로가기 btn
const $backBtn = $(".chat-room-container .back-btn");

// 채팅 message input
const $chatInput = $("input[name='send-msg']");

// 채팅 message form
const $chatMessageForm = $(".chat-message-wrap");

// 채팅 내역 ul
const $messageArea = $(".message-area");

// 입장 roomId
let enterRoomId;

function appendWelfares(welfare) {
    let text;
    text = `
                <li>
                    <a onclick="javascript:createChattingRoom(this)" data-welfareId="${welfare.id}" data-welfareName="${welfare.welfareName}">
                            <span class="welfare-title welfare-info">
                                ${welfare.welfareName}
                            </span>
                        <span class="welfare-address welfare-info">
                            ${welfare.address.firstAddress}
                        </span>
                    </a>
                </li>
            `;
    $(".list-contents-welfare").append(text);
}

function appendChatRooms(chatRoom) {
    let text;

    let roomName = chatRoom.roomName;
    text = `
        <li>
            <a onclick="showChatting(this)" data-roomId="${chatRoom.roomId}">
                    <span class="welfare-title welfare-info">
                        ${roomName}
                    </span>
                <span class="welfare-address welfare-info">
                        복지관 채팅방
                    </span>
            </a>
        </li>
    `;

    $(".list-contents-chatroom").append(text);
}

$chatShowBtn.on("click", function () {
    $(this).hide(500);
    $chatWindow.show(500);

    // 화면 데이터 보여줌
    showChatWindowData();
});

function showChatWindowData() {
    // pageNum 초기화
    pageNum = 1;

    // 복지관 목록
    chatAjax(WELFARE_REQ_URL, {page: pageNum}, (result) => {
        // 기존 검색결과 삭제
        $(".list-contents-welfare").empty();
        result.content.forEach(welfare => appendWelfares(welfare));
        // 마지막 여부 담기
        isLast = result.last;

        // 마지막이면 더보기 버튼 표시 안함
        if(isLast) $moreWelfares.css("display", "none");
    });

    // 회원의 채팅방 목록
    chatAjax(CHAT_LIST_REQ_URL, {}, (chatRooms) => {
        console.log(chatRooms);
        $(".list-contents-chatroom").empty();
        chatRooms.forEach(chatRoom => appendChatRooms(chatRoom));
    });
}

// 복지관 더불러오기
$moreWelfares.on("click", function () {
    // 페이지 증가
    pageNum++;

    if (isLast) {
        $(this).css("display", "none");
        return;
    }

    chatAjax(WELFARE_REQ_URL, {page: pageNum}, (result) => {
        console.log(result);
        result.content.forEach(welfare => appendWelfares(welfare));
        isLast = result.last;
    });
});

$searchWelfareForm.on("submit", function (e) {
    e.preventDefault();
    let inputValue = $(".chat-input").val();
    if (inputValue === "" || inputValue === null) {
        return;
    }

    // pageNum 초기화
    pageNum = 1;

    chatAjax(WELFARE_REQ_URL, {page: pageNum, welfareName: inputValue}, (result) => {
        // 기존 검색결과 삭제
        $(".list-contents-welfare").empty();
        result.content.forEach(welfare => appendWelfares(welfare));
        isLast = result.last;
    });
});

// 채팅 닫기
$chatClose.on("click", function () {
    $chatWindow.hide(500);
    $chatShowBtn.show(500);
});

// 뒤로가기
$backBtn.on("click", function () {
    $chatRoomContainer.hide(500);
    $msgInput.hide(500, () => $chatContent.slideDown());

    exitRoom();

    // 화면 데이터 보여줌
    showChatWindowData();
});

function chatAjax(url, data, callback) {
    $.ajax({
        type: "get",
        url: url,
        data: data,
        dataType: "json",
        success: function (response) {
            if (callback) {
                callback(response);
            }
        }
    });
}


/* ======================== 채팅 JS  ========================== */
// 채팅 stomp client
let stompClient = null;

function createChattingRoom(aTag) {
    let welfareName = $(aTag).data("welfarename");
    let welfareId = $(aTag).data("welfareid");

    $chatContent.slideUp(() => {
        // slideUp 후 채팅내역 보여줌
        $chatRoomContainer.show(500, () => $msgInput.show(500));
    });

    $.ajax({
        type: "POST",
        url: `/chat/createroom/${welfareId}`,
        data: null,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (chatRoom) {
            connect(chatRoom.roomId, welfareId);
        }
    });
}

function showChatting(aTag) {
    let roomId = $(aTag).data("roomid");

    $chatContent.slideUp(() => {
        // slideUp 후 채팅내역 보여줌
        $chatRoomContainer.show(500, () => $msgInput.show(500));
    });

    // 연결
    connect(roomId);
    console.log("$SESSION_USER_ID : " + $SESSION_USER_ID);

    // 입장 roomId 에 값 저장
    enterRoomId = roomId;
    console.log("enterRoomId : " + roomId);
}

function connect(roomId, welfareId) {

    // 새로운 연결일 때 (welfareId 가 들어올떄)
    if (welfareId) {
        let data = {
            roomId: roomId,
            welfareId: welfareId
        }

        console.log("connect 들어옴");

        $.ajax({
            type: "POST",
            url: "/chat/joinroom",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function () {
            }
        });
    }

    // 연결하고자하는 Socket 의 endPoint
    let socket = new SockJS('/ws-stomp');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => onConnected(roomId), onError);
}

function onConnected(roomId) {

    // sub 할 url => /sub/chat/room/roomId 로 구독한다
    stompClient.subscribe('/sub/chat/room/' + roomId, onMessageReceived);

    // 서버에 username 을 가진 유저가 들어왔다는 것을 알림
    // /pub/chat/enterUser 로 메시지를 보냄
    stompClient.send('/pub/chat/enterUser/',
        {},
        JSON.stringify({
            "roomId": roomId,
            type: 'ENTER',
        })
    );

    // 메시지 표시 공간 비우기
    $messageArea.empty();

    chatAjax(`/chat/history/${roomId}`, {}, (result) =>
        result.forEach(message => prependChatMessage(message))
    );
}

// 메시지를 받을 때도 마찬가지로 JSON 타입으로 받으며,
// 넘어온 JSON 형식의 메시지를 parse 해서 사용한다.
function onMessageReceived(payload) {
    console.log("payload 들어오냐? :"+payload);

    let message = JSON.parse(payload.body);

    // 나의 메시지인 경우 return
    /* 진짜 구린 방법이라 추후 더 나은 방법이 있나 생각해볼 것 */
    if($SESSION_USER_ID === message.senderId) {
        return;
    }

    appendInputChatMessage(message);
}

// 채팅 form submit 시 message 발송
$chatMessageForm.on("submit", (e) => sendMessage(e));

function sendMessage(event) {
    event.preventDefault();
    let messageContent = $chatInput.val();

    if (messageContent === "" || messageContent === null) return;

    if (messageContent && stompClient) {
        let chatMessage = {
            "roomId": enterRoomId,
            message: messageContent,
            type: 'TALK'
        };

        stompClient.send("/pub/chat/sendMessage", {}, JSON.stringify(chatMessage));
    }

    // 값 초기화
    $chatInput.val("");
    appendInputChatMessage({message : messageContent});
}

// 채팅 메시지 표시
function prependChatMessage(message) {
    let isMyMessage = message.myMessage;

    if(isMyMessage == null) {
        isMyMessage = false;
    }

    let text = `
        <li>
            <div class="message ${isMyMessage ? 'msg-float-right' : 'msg-float-left'}">
                <span>${message.message}</span>
            </div>
        </li>
    `;

    $messageArea.prepend(text);
}

function appendInputChatMessage(message) {

    let isMyMessage = message.myMessage;

    if(isMyMessage == null) {
        isMyMessage = true;
    }

    let text = `
        <li>
            <div class="message ${isMyMessage ? 'msg-float-right' : 'msg-float-left'}">
                <span>${message.message}</span>
            </div>
        </li>
    `;

    $messageArea.append(text);
}


// 채팅방 나가기
function exitRoom() {
    // 구독 해제(채팅방 나가기)
    stompClient.unsubscribe(enterRoomId);
    // roomId 초기화
    roomId = null;
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}