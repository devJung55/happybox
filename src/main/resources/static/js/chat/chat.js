const $createChat = $(".create_chat");
const $sendBtn = $(".msg_submit_button");
const $inputMsg = $(".input_msg");

// 웹소켓 생성
const socket = new WebSocket("ws://localhost:10000/ws/chat");

socket.onmessage = async function (e) {
    let result;
    try {
        if (e !== null && e !== undefined) {
            result = await JSON.parse(e.data);
            console.log(result);
        }
    } catch (err) {
        console.log(err);
    }
};

$createChat.on("click", function () {
    $.ajax({
        type: "POST",
        url: "/chat/create",
        data: {},
        dataType: "json",
        success: function (result) {
            let data = {
                type: "ENTER",
                roomId: result.roomId,
                sender: "사용자1",
            }

            socket.send(JSON.stringify(data));
        }
    });
});

$sendBtn.on("click", function () {

})