package com.app.happybox.service.chat;

import com.app.happybox.entity.chat.ChatRoom;
import com.app.happybox.entity.chat.ChatMessage;
import com.app.happybox.exception.ChatRoomNotFoundException;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.repository.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;


    // 전체 채팅방 조회
    public List<ChatRoom> findAllRoom() {
        //채팅방 생성 순서를 최근순으로 반환
        List<ChatRoom> chatRooms = new ArrayList<>();
        chatRoomRepository.findAll().forEach(chatRooms::add);

        Collections.reverse(chatRooms);

        return chatRooms;
    }

    // roomId 기준으로 채팅방 찾기
    @Transactional
    public ChatRoom findByRoomId(String roomId) {
        return chatRoomRepository.findByRoomId(roomId).orElseThrow(ChatRoomNotFoundException::new);
    }

    // roomName 으로 채팅방 만들기
    @Transactional
    public ChatRoom createChatRoom(String roomName) {
        //채팅방 이름으로 채팅 방 생성후
        ChatRoom chatRoom = new ChatRoom().create(roomName);
        //map에 채팅방 아이디와 만들어진 채팅룸을 저장
        chatRoomRepository.save(chatRoom);

        return chatRoom;
    }

    //채팅방 유저 리스트에 유저추가
    @Transactional
    public String addUser(String roomId, UserDetail userDetail) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow(ChatRoomNotFoundException::new);
        String joinedUserName = chatRoom.getUsers().put(userDetail.getId(), userDetail.getUserId());

        // 채팅 인원수 + 1
        long userCount = chatRoom.getUserCount() + 1;
        chatRoom.setUserCount(userCount);

        return joinedUserName;
    }

    // 채팅방 유저 리스트 삭제
    @Transactional
    public String deleteUser(String roomId, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow(ChatRoomNotFoundException::new);
        String removedUserName = chatRoom.getUsers().remove(userId);

        // 채팅방 유저 -1
        if (removedUserName != null) {
            long userCount = chatRoom.getUserCount() - 1;
            chatRoom.setUserCount(userCount);
        }

        return removedUserName;
    }

    //채팅방 전체 userList 조회
    @Transactional
    public List<String> getUserList(String roomId) {
        List<String> list = new ArrayList<>();

        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow(ChatRoomNotFoundException::new);

        chatRoom.getUsers().forEach((id, userName) -> list.add(userName));

        return list;
    }
}
