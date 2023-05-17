package com.app.happybox.service.reply;

import com.app.happybox.entity.reply.ReplyDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
public class ReplyServiceTests {
    @Autowired
    private ReviewBoardReplyService reviewBoardReplyService;

}
