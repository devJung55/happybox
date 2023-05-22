package com.app.happybox.repository.chat;

import com.app.happybox.entity.chat.ChatRoom;
import com.app.happybox.entity.chat.UserRoom;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class ChatRoomRepositoryTests {
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserRoomRepository userRoomRepository;

    @Test
    public void saveTest() {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomName("testRoom")
                .roomId("5f176086-0456-400f-bd91-e896809dc1f0")
                .build();

        chatRoomRepository.save(chatRoom);
    }

    @Test
    public void findAllTest() {
        Iterable<ChatRoom> rooms = chatRoomRepository.findAll();

        for (ChatRoom room : rooms) {
            userRoomRepository.findByUserId(1L).ifPresentOrElse(userRoom -> {
                userRoom.getRoomIds().add(room.getRoomId());
                log.info(userRoom.toString());
            }, () -> {
                UserRoom userRoom = UserRoom.builder().userId(1L).build();
                userRoom.getRoomIds().add(room.getRoomId());
                userRoomRepository.save(userRoom);

                log.info(userRoom.toString());
            });
            log.info(room.toString());
        }

    }
    @Test
    public void findByRoomId() {
        chatRoomRepository.findByRoomId("5f176086-0456-400f-bd91-e896809dc1f0").map(ChatRoom::toString).ifPresent(log::info);
    }

    @Test
    public void findUserRoomByUserIdTest() {
        userRoomRepository.findByUserId(1L).map(UserRoom::toString).ifPresent(log::info);
    }
}