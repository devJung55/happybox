package com.app.happybox.entity.chat;

import com.app.happybox.type.MessageType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Getter @Setter @ToString @NoArgsConstructor
@RedisHash(value = "chat", timeToLive = 86400L)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessage {

    /* timeToLive = -1 : 영구저장 | 1초단위 25200L : 1주일 저장 */

    @Id
    private Long id;

    // 메시지 타입 :  입장 채팅
    // 메시지 타입에 따라서 동작하는 구조가 달라진다.
    // 입장과 퇴장 ENTER 과 LEAVE 의 경우 입장/퇴장 이벤트 처리가 실행되고,
    // TALK 는 말 그대로 해당 채팅방을 sub 하고 있는 모든 클라이언트에게 전달됩니다.
    private MessageType type; //메시지 타입
    @Indexed
    private String roomId;// 방 번호
    private String sender;//채팅을 보낸 사람
    private String message;// 메세지
    private LocalDateTime time; // 채팅 발송 시간

    @Builder
    public ChatMessage(MessageType type, String roomId, String sender, String message, LocalDateTime time) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
        this.time = time;
    }
}
