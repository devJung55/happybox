package com.app.happybox.repository.user;

import com.app.happybox.entity.file.UserFile;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserFileRepository userFileRepository;

    @Test
    public void findByIdTest(){
        // given
        userRepository.findById(1L).map(user -> (user).toString()).ifPresent(log::info);

        // when

        // then
    }

    @Test
    public void userFilesaveTest() {
        UserFile userFile = new UserFile("2023/05/18", UUID.randomUUID().toString(), "프로필 사진", userRepository.findById(26L).get());
        userFileRepository.save(userFile);
    }
}