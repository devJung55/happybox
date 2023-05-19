package com.app.happybox.entity.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter @ToString @NoArgsConstructor
@RedisHash(value = "userRoom", timeToLive = -1L)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRoom {

    @Id
    private Long id;

    @Indexed
    private Long userId;

    private Set<String> roomIds = new HashSet<>();

    @Builder
    public UserRoom(Long userId) {
        this.userId = userId;
    }
}
