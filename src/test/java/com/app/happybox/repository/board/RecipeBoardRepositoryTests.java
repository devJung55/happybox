package com.app.happybox.repository.board;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
public class RecipeBoardRepositoryTests {
    @Autowired
    private RecipeBoardRepository recipeBoardRepository;

    @Test
    public void saveTest() {

    }

    @Test
    public void findRecipeBoardListByMemberIdWithPagingTest() {

    }
}
