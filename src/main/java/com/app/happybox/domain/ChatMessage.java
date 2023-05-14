package com.app.happybox.domain;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class ChatMessage {
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
}
