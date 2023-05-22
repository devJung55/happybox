//package com.app.happybox.repository.board;
//
//import com.app.happybox.entity.board.RecipeBoard;
//import com.app.happybox.entity.board.RecipeBoardLike;
//import com.app.happybox.entity.board.ReviewBoard;
//import com.app.happybox.entity.board.ReviewBoardLike;
//import com.app.happybox.entity.user.Member;
//import com.app.happybox.repository.user.MemberRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.fail;
//
//@SpringBootTest
//@Transactional
//@Rollback(false)
//@Slf4j
//public class RecipeBoardLikeRepositoryTests {
//    @Autowired
//    private RecipeBoardLikeRepository recipeBoardLikeRepository;
//    @Autowired
//    private RecipeBoardRepository recipeBoardRepository;
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Test
//    public void checkMemberLikesReviewBoardTest(){
//        Optional<Member> member = memberRepository.findById(1L);
//        Optional<RecipeBoard> recipeBoard = recipeBoardRepository.findById(91L);
//
//        if(!member.isPresent() || !recipeBoard.isPresent()) fail("멤버 혹은 리뷰 보드가 존재하지 않음");
//
//        checkMemberAlreadyLikes(member.get(), recipeBoard.get());
//    }
//
//    @Test
//    public void saveTest(){
//        Optional<Member> member = memberRepository.findById(1L);
//        Optional<RecipeBoard> recipeBoard = recipeBoardRepository.findById(91L);
//
//        if(!member.isPresent() || !recipeBoard.isPresent()) fail("멤버 혹은 리뷰 보드가 존재하지 않음");
//
//        if(!checkMemberAlreadyLikes(member.get(), recipeBoard.get())){
//            Integer likeCount = recipeBoard.get().getRecipeLikeCount();
//
//            RecipeBoardLike recipeBoardLike = new RecipeBoardLike(member.get(), recipeBoard.get());
//            recipeBoardLikeRepository.save(recipeBoardLike);
//
//            recipeBoard.get().setRecipeLikeCount(++likeCount);
//        }
//    }
//
//    @Test
//    private boolean checkMemberAlreadyLikes(){
//        Long memberId = 61L;
//        Long recipeBoardId = 124L;
//        return recipeBoardLikeRepository.checkMemberLikesRecipeBoard_QueryDSL(memberId, recipeBoardId);
//    }
//}
