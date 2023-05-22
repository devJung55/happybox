package com.app.happybox.repository.chat;

import com.app.happybox.entity.chat.UserRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoomRepository extends CrudRepository<UserRoom, Long> {

    public List<UserRoom> findByUserId(Long userId);

    public List<UserRoom> findAll();
}
