package com.app.happybox.controller.chat;

import com.app.happybox.domain.chat.ChatMessageDTO;
import com.app.happybox.entity.chat.ChatRoom;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatService chatService;

    @GetMapping("")
    public String goChatRoom() {
        return "chat/chat";
    }

    // 채팅 리스트 확인
    @GetMapping("/list")
    @ResponseBody
    public List<ChatRoom> ChatRoomList(@AuthenticationPrincipal UserDetail userDetail) {

        log.info("===================" + userDetail.toString());
        if (userDetail == null) {
            return new ArrayList<>();
        }

        List<ChatRoom> chatRooms = chatService.findAllRoomByUserId(userDetail.getId());
        log.info("Show All Chat_Room List : {}", chatRooms);

        return chatRooms;
    }

    // 채팅방 생성
    @PostMapping("/createroom/{welfareId}")
    @ResponseBody
    public ChatRoom createRoom(@PathVariable Long welfareId, @AuthenticationPrincipal UserDetail userDetail) {

        if (userDetail == null) {
            throw new UserNotFoundException("로그인되지 않음.");
        }

        ChatRoom userChatRoom = chatService.findRoomByUserIdAndWelfareIdReturnIfPresent(userDetail.getId(), welfareId);

        log.info("============== USER_CHAT_ROOM " + userChatRoom);

        if (userChatRoom == null) {
            ChatRoom chatRoom = chatService.createChatRoom(userDetail.getUserId(), welfareId, userDetail.getId());
            log.info("Create ChatRoom : {}", chatRoom);
            return chatRoom;
        }

        return userChatRoom;
    }

    // 채팅방 입장
    @PostMapping("/joinroom")
    @ResponseBody
    public void joinRoom(@RequestBody ChatMessageDTO chat, @AuthenticationPrincipal UserDetail userDetail) {

        log.info("==================== joinRoom 들어옴 ==================");

        if (userDetail == null) {
            return;
        }

        ChatRoom userChatRoom = chatService.findRoomByUserIdAndWelfareIdReturnIfPresent(userDetail.getId(), chat.getWelfareId());

        // 채팅 유저와 복지관 id 저장
        if (userChatRoom == null) {
            log.info("=================== userChatRoom is null ============");
            chatService.addUser(chat.getRoomId(), userDetail.getId());
            chatService.addUser(chat.getRoomId(), chat.getWelfareId());
            log.info("roomId : {}", chat.getRoomId());
        }
    }
}
