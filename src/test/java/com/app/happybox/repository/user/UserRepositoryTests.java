package com.app.happybox.repository.user;

import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByIdTest(){
        // given
        userRepository.findById(1L).map(user -> (user).toString()).ifPresent(log::info);

        // when

        // then
    }
}