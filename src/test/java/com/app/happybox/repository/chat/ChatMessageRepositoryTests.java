package com.app.happybox.repository.chat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
public class ChatMessageRepositoryTests {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Test
    public void findAllTest(){
        chatMessageRepository.findAll().forEach(chat -> log.info(chat.toString()));
    }

    @Test
    public void findAllByRoomIdTest(){
        chatMessageRepository.findAllByRoomId("5890cb76-90d8-42e1-9395-46258df8a207").forEach(chat -> log.info(chat.toString()));
    }
}
