package com.app.happybox.controller.chat;

import com.app.happybox.domain.chat.ChatMessageDTO;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.service.chat.ChatMessageService;
import com.app.happybox.service.chat.ChatService;
import com.app.happybox.type.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    // 아래에서 사용되는 convertAndSend 를 사용하기 위해서 선언
    // convertAndSend 는 객체를 인자로 넘겨주면 자동으로 Message 객체로 변환 후 도착지로 전송한다.
    private final SimpMessageSendingOperations template;
    private final ChatService chatService;
    private final ChatMessageService chatMessageService;

    // MessageMapping 을 통해 websocket 으로 들어오는 메시지를 발신 처리합니다.
    // 이 때 클라이언트에서는 /pub/chat/message 로 요청을 하게 되고 이것을 controller 가 받아서 처리합니다.
    // 처리가 완료되면 /sub/chat/room/roomId 로 메시지가 전송됩니다.
    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatMessageDTO chat, SimpMessageHeaderAccessor headerAccessor, Authentication authentication) {

        Long userId = ((UserDetail) authentication.getPrincipal()).getId();
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

    //해당유저
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatMessageDTO chat, Authentication authentication) {

        log.info("chat : {}", chat);
        Long userId = ((UserDetail) authentication.getPrincipal()).getId();

        ChatMessageDTO savedChat = chatMessageService.save(chat, userId);
        savedChat.setSenderId(userId);

        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), savedChat);
    }

    //유저 퇴장 시에는 EventListener 를 통해서 유저 퇴장을 확인
    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {

        log.info("DisconnectEvent : {}", event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // stomp 세션에 있던 uuid 와 userDetail의 id를 통해 채팅방 유저 리스트와 room에서 해당 유저를 삭제
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
        String user = (String) headerAccessor.getSessionAttributes().get("user");

        log.info("headAccessor : {}", headerAccessor);

        // userDetail의 Id로 채팅방에서 유저 삭제
        String removedUserName = chatService.deleteUser(roomId, user);

        // 유저 삭제가 정상적으로 되었을 경우
        if (removedUserName != null) {
            log.info("User Disconnected : " + removedUserName);

            ChatMessageDTO chat = ChatMessageDTO.builder()
                    .type(MessageType.LEAVE)
                    .message(removedUserName + "님이 퇴장하였습니다.")
                    .build();

            ChatMessageDTO savedChat = chatMessageService.save(chat);

            template.convertAndSend("/sub/chat/room/" + roomId, savedChat);
        }
    }

    // 채팅에 참여한 유저 리스트 반환
    @GetMapping("/chat/userList")
    @ResponseBody
    public List<String> userList(String roomId) {

        return chatService.getUserList(roomId);
    }

    // 해당 방의 채팅내역 조회
    @GetMapping("/chat/history/{roomId}")
    @ResponseBody
    public List<ChatMessageDTO> chatHistory(@PathVariable String roomId, @AuthenticationPrincipal UserDetail userDetail,
                                            HttpServletRequest request) {
        if (userDetail == null) {
            return new ArrayList<>();
        }

        request.getSession().setAttribute("userId", userDetail.getId());

        List<ChatMessageDTO> messages = chatMessageService.findAllChatMessagesByRoomId(roomId);
        messages.forEach(message -> {
            if(message.getSenderId().equals(userDetail.getId())) {
                message.setMyMessage(true);
            }
        });

        return messages;
    }

//    // 채팅에 참여한 유저 닉네임 중복 확인
//    @GetMapping("/chat/duplicateName")
//    @ResponseBody
//    public String isDuplicateName(@RequestParam("roomId") String roomId,
//                                  @RequestParam("username") String username) {
//
//        String userName = chatService.isDuplicateName(roomId, username);
//        log.info("DuplicateName : {}", userName);
//
//        return userName;
//    }

}
