package com.app.happybox.repository.reply;

import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.board.RecipeBoardLike;
import com.app.happybox.entity.reply.RecipeBoardReply;
import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.user.Welfare;
import com.app.happybox.repository.board.RecipeBoardLikeRepository;
import com.app.happybox.repository.board.RecipeBoardRepository;
import com.app.happybox.repository.user.MemberRepository;
import com.app.happybox.repository.user.WelfareRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
public class RecipeBoardReplyRepositoryTests {
    @Autowired private RecipeBoardReplyRepository recipeBoardReplyRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private WelfareRepository welfareRepository;
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
        for(int i=0; i<5; i++){
            Optional<Welfare> welfare = welfareRepository.findById(2L);
            Optional<RecipeBoard> recipeBoard = recipeBoardRepository.findById(91L);
            Integer recipeBoardReplyCount = recipeBoard.get().getRecipeBoardReplyCount();
            RecipeBoardReply recipeBoardReply = new RecipeBoardReply("댓글테스트" + (i+1), welfare.get(), recipeBoard.get());
            recipeBoardReplyRepository.save(recipeBoardReply);
            recipeBoard.get().setRecipeBoardReplyCount(++recipeBoardReplyCount);
        }
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