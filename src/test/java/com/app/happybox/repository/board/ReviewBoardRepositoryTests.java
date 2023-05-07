package com.app.happybox.repository.board;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.repository.user.MemberRepository;
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
public class ReviewBoardRepositoryTests {
    @Autowired
    private ReviewBoardRepository reviewBoardRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void saveTest(){
        ReviewBoard reviewBoard = new ReviewBoard(
                "테스트 제목1",
                "테스트 내용1",
                5,
                1
        );
        memberRepository.findById(2L).ifPresent(member -> reviewBoard.setMember(member));

    }
}
