package com.app.happybox.service.chat;

import com.app.happybox.domain.chat.ChatMessageDTO;
import com.app.happybox.entity.chat.ChatMessage;
import com.app.happybox.repository.chat.ChatMessageRepository;
import edu.emory.mathcs.backport.java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public List<ChatMessageDTO> findAllChatMessagesByRoomId(String roomId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findAllByRoomId(roomId);

        // 날짜순 정렬
        Comparator<ChatMessage> comparator = Comparator.comparing(ChatMessage::getTime).reversed();
        Collections.sort(chatMessages, comparator);

        return chatMessages.stream().map(this::chatMessageToDTO).collect(Collectors.toList());
    }

    @Transactional
    public ChatMessageDTO save(ChatMessageDTO chatMessageDTO, Long sender) {
        ChatMessage chatMessage = chatMessageToEntity(chatMessageDTO);
        // senderId 저장
        chatMessage.setSenderId(sender);
        // 현재 시간으로 저장
        chatMessage.setTime(LocalDateTime.now());
        chatMessageRepository.save(chatMessage);
        return chatMessageDTO;
    }

    @Transactional
    public ChatMessageDTO save(ChatMessageDTO chatMessageDTO) {
        ChatMessage chatMessage = chatMessageToEntity(chatMessageDTO);
        // 현재 시간으로 저장
        chatMessage.setTime(LocalDateTime.now());
        chatMessageRepository.save(chatMessage);
        return chatMessageDTO;
    }

    private ChatMessageDTO chatMessageToDTO(ChatMessage chatMessage) {
        return ChatMessageDTO.builder()
                .message(chatMessage.getMessage())
                .roomId(chatMessage.getRoomId())
                .senderId(chatMessage.getSenderId())
                .time(chatMessage.getTime().toString())
                .type(chatMessage.getType())
                .build();
    }

    private ChatMessage chatMessageToEntity(ChatMessageDTO chatMessageDTO) {
        return ChatMessage.builder()
                .message(chatMessageDTO.getMessage())
                .roomId(chatMessageDTO.getRoomId())
                .senderId(chatMessageDTO.getSenderId())
                .type(chatMessageDTO.getType())
                .build();
    }
}
