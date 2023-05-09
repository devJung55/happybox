package com.app.happybox.repository.reply;

import com.app.happybox.entity.board.Board;
import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.board.RecipeBoardLike;
import com.app.happybox.entity.reply.RecipeBoardReply;
import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.user.User;
import com.app.happybox.repository.board.RecipeBoardLikeRepository;
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
    @Autowired private RecipeBoardLikeRepository recipeBoardLikeRepository;

    @Test
    public void saveTest() {
//        for (int i = 0; i < 5; i++) {
//            RecipeBoardReply recipeBoardReply = new RecipeBoardReply(
//                    "레시피 댓글_" + (i + 1),
//                    memberRepository.findById(1L).get(),
//                    recipeBoardRepository.findById(16L).get()
//            );
//
//            recipeBoardReplyRepository.save(recipeBoardReply);
//        }

//        for (int i = 0; i < 5; i++) {
//            RecipeBoardLike recipeBoardLike = new RecipeBoardLike(recipeBoardRepository.findById(60L).get(), memberRepository.findById(1L).get());
//            recipeBoardLikeRepository.save(recipeBoardLike);
//        }
    }

    @Test
    public void findAllByMemberIdDescWithPagingTest() {
        recipeBoardReplyRepository.findAllByMemberIdDescWithPaging_QueryDSL(PageRequest.of(0, 3), 1L)
                .stream().map(Reply::getReplyContent).forEach(log::info);

//        recipeBoardRepository.findRecipeBoardReplyCountByMemberId_QueryDSL(1L)
//                .stream().map(RecipeBoard::getRecipeBoardReplies).forEach(v -> log.info("replyCount : " + v.size()));
    }

    @Test
    public void findBookmarkListWithMemberIdWithPagingTest() {
        recipeBoardLikeRepository.findBookmarkListWithMemberIdWithPaging_QueryDSL(PageRequest.of(0, 3), 1L)
                .stream().map(RecipeBoardLike::getRecipeBoard).forEach(v -> log.info(v.getBoardContent()));
    }
}