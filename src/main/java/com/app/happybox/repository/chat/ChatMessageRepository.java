package com.app.happybox.repository.chat;

import com.app.happybox.entity.chat.ChatMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {
    public List<ChatMessage> findAllByRoomId(String roomId);
}
