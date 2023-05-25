package com.app.happybox.service.chat;

import com.app.happybox.entity.chat.ChatRoom;
import com.app.happybox.entity.chat.ChatMessage;
import com.app.happybox.entity.chat.UserRoom;
import com.app.happybox.entity.user.User;
import com.app.happybox.entity.user.Welfare;
import com.app.happybox.exception.ChatRoomNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.repository.chat.ChatRoomRepository;
import com.app.happybox.repository.chat.UserRoomRepository;
import com.app.happybox.repository.user.UserRepository;
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
    private final UserRoomRepository userRoomRepository;
    private final UserRepository userRepository;


    // 전체 채팅방 조회
    public List<ChatRoom> findAllRoom() {
        //채팅방 생성 순서를 최근순으로 반환
        List<ChatRoom> chatRooms = new ArrayList<>();
        chatRoomRepository.findAll().forEach(chatRooms::add);

        Collections.reverse(chatRooms);

        return chatRooms;
    }

    // 회원의 채팅방 조회
    @Transactional
    public List<ChatRoom> findAllRoomByUserId(Long userId) {
        List<ChatRoom> chatRooms = new ArrayList<>();
        log.info(userRoomRepository.findAll().toString());
        userRoomRepository.findByUserId(userId).forEach(userRoom -> {
            String roomId = userRoom.getRoomId();
            ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow(ChatRoomNotFoundException::new);

            chatRooms.add(chatRoom);
        });

        return chatRooms;
    }

    // 이미 채팅하는 복지관이라면 그 채팅방 return
    @Transactional
    public ChatRoom findRoomByUserIdAndWelfareIdReturnIfPresent(Long userId, Long welfareId) {
        List<ChatRoom> chatRooms = new ArrayList<>();

        log.info("findByUserId : {}", userRoomRepository.findByUserId(userId));

        userRoomRepository.findByUserId(userId).forEach(userRoom -> {
            ChatRoom foundChatRoom = chatRoomRepository.findByRoomId(userRoom.getRoomId()).orElseThrow(ChatRoomNotFoundException::new);

            log.info("found chat Room : {}", foundChatRoom);

            if(foundChatRoom.getWelfareId().equals(welfareId)) chatRooms.add(foundChatRoom);
        });

        return chatRooms.size() > 0 ? chatRooms.get(0) : null;
    }

    // roomId 기준으로 채팅방 찾기
    @Transactional
    public ChatRoom findByRoomId(String roomId) {
        return chatRoomRepository.findByRoomId(roomId).orElseThrow(ChatRoomNotFoundException::new);
    }

    // roomName 으로 채팅방 만들기
    @Transactional
    public ChatRoom createChatRoom(String userIdentification, Long welfareId, Long userId) {
        Welfare welfare = (Welfare)userRepository.findById(welfareId).orElseThrow(UserNotFoundException::new);
        String roomName = "[ " + userIdentification + " ]" + " 님 과 " + welfare.getWelfareName() + "님 의 채팅방";

        //채팅방 이름으로 채팅 방 생성후
        ChatRoom chatRoom = new ChatRoom().create(roomName, welfareId);
        //map에 채팅방 아이디와 만들어진 채팅룸을 저장
        chatRoomRepository.save(chatRoom);

//        유저의 채팅방 내역인 userRoom 저장
//        일반회원 userRoom
        UserRoom userRoom = UserRoom.builder().userId(userId).roomId(chatRoom.getRoomId()).build();
//        복지관 저장 userRoom
        UserRoom welfareUserRoom = UserRoom.builder().userId(welfareId).roomId(chatRoom.getRoomId()).build();
        userRoomRepository.saveAll(new ArrayList<>(Arrays.asList(userRoom, welfareUserRoom)));

        return chatRoom;
    }

    //채팅방 유저 리스트에 유저추가
    @Transactional
    public String addUser(String roomId, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow(ChatRoomNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        String joinedUserName = chatRoom.getUsers().put(user.getUserId(), user.getUserId());

        log.info("=================== USER_ID " + userId);

        // 유저 채팅방 목록에 추가
        userRoomRepository.save(UserRoom.builder().roomId(roomId).userId(userId).build());

        // 채팅 인원수 + 1
        long userCount = chatRoom.getUserCount() + 1;
        chatRoom.setUserCount(userCount);

        return joinedUserName;
    }

    // 채팅방 유저 리스트 삭제
    @Transactional
    public String deleteUser(String roomId, String user) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow(ChatRoomNotFoundException::new);
        String removedUserName = chatRoom.getUsers().remove(user);

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
