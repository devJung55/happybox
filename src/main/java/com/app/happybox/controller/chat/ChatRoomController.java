package com.app.happybox.controller.chat;

import com.app.happybox.entity.chat.ChatRoom;
import com.app.happybox.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    // ChatRepository Bean 가져오기
    private final ChatService chatService;

    @GetMapping("")
    public String goChatRoom() {
        return "chat/chat";
    }

    // 채팅 리스트 확인
    @GetMapping("/list")
    @ResponseBody
    public List<ChatRoom> ChatRoomList() {

        List<ChatRoom> chatRooms = chatService.findAllRoom();
        log.info("Show All Chat_Room List : {}", chatRooms);

        return chatRooms;
    }

    // 채팅방 생성 (리스트로 리다이렉트)
    @PostMapping("/createroom")
    @ResponseBody
    public ChatRoom createRoom(@RequestBody String roomName) {

        ChatRoom chatRoom = chatService.createChatRoom(roomName);
        log.info("Create ChatRoom : {}", chatRoom);

        return chatRoom;
    }

    // 채팅방 입장 화면
    // 파라미터로 넘어오는 roomId를 확인 후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/chat/joinroom")
    public String joinRoom(String roomId, Model model) {

        log.info("roomId : {}", roomId);
        model.addAttribute("room", chatService.findByRoomId(roomId));

        return "chatroom";
    }
}
