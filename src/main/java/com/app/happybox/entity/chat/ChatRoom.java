package com.app.happybox.entity.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.*;

@Getter @Setter @ToString @NoArgsConstructor
@RedisHash(value = "chatRoom", timeToLive = -1L)
@JsonIgnoreProperties(ignoreUnknown = true) /* 선언되지 않은 필드 제외함 */
public class ChatRoom {

    @Id
    private Long id;

    @Indexed
    private String roomId;  // 채팅방 아이디
    @Indexed
    private Long welfareId;
    private String roomName;// 채팅방 이름
    private long userCount; // 채팅방 인원수

    private Map<String, String> users = new HashMap<>();

    @Builder
    public ChatRoom(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.userCount = 0;
    }

    public ChatRoom create(String roomName, Long welfareId){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomName = roomName;
        chatRoom.welfareId = welfareId;

        return chatRoom;
    }
}
