package com.app.happybox.repository.reply;

import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.reply.RecipeBoardReply;
import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.user.User;
import com.app.happybox.repository.board.RecipeBoardRepository;
import com.app.happybox.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
public class RecipeBoardReplyRepositoryTests {
    @Autowired private RecipeBoardReplyRepository recipeBoardReplyRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private RecipeBoardRepository recipeBoardRepository;

    @Test
    public void saveTest() {
        for (int i = 0; i < 5; i++) {
            RecipeBoardReply recipeBoardReply = new RecipeBoardReply(
                    "레시피 댓글_" + (i + 1),
                    memberRepository.findById(1L).get(),
                    recipeBoardRepository.findById(16L).get()
            );

            recipeBoardReplyRepository.save(recipeBoardReply);
        }
    }

    @Test
    public void findAllByMemberIdDescWithPagingTest() {
        recipeBoardReplyRepository.findAllByMemberIdDescWithPaging_QueryDSL(PageRequest.of(0, 3), 1L)
                .stream().map(Reply::getReplyContent).forEach(log::info);
    }
}