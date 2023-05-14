package com.app.happybox.controller.chat;

import com.app.happybox.domain.ChatRoom;
import com.app.happybox.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @GetMapping("")
    public String goChat() { return "chat/chat"; }

    @PostMapping("/create")
    @ResponseBody
    public ChatRoom createRoom() {
        return chatService.createRoom();
    }

    @GetMapping("/room/find")
    @ResponseBody
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }

}
