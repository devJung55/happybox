package com.app.happybox.repository.board;

import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.board.RecipeBoardDTO;
import com.app.happybox.entity.type.Gender;
import com.app.happybox.entity.user.Address;
import com.app.happybox.entity.user.Member;
import com.app.happybox.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
public class RecipeBoardRepositoryTests {
    @Autowired private RecipeBoardRepository recipeBoardRepository;
    @Autowired private MemberRepository memberRepository;

    @Test
    public void saveTest() {
        for (int i = 0; i < 5; i++) {
            RecipeBoard recipeBoard = new RecipeBoard("레시피 게시물_제목" + (i + 1), "레시피 게시물_내용" + (i + 1));
            memberRepository.findById(1L).ifPresent(member -> recipeBoard.setMember(member));

            recipeBoardRepository.save(recipeBoard);
        }
    }

    @Test
    public void findRecipeBoardListByMemberIdWithPagingTest() {
        recipeBoardRepository.findRecipeBoardListByMemberIdWithPaging_QueryDSL(
                PageRequest.of(0, 5), 1L
        ).stream().map(RecipeBoardDTO::toString).forEach(log::info);
    }

    @Test
    public void findTop5ByLikeCountWithDetailOrderByLikeCount_QueryDSL_Test() {
        // given
        List<RecipeBoard> recipeBoards = recipeBoardRepository.findTop5ByLikeCountWithRepresentFileOrderByLikeCount_QueryDSL();

        // when

        // then
        recipeBoards.stream().map(RecipeBoard::toString).forEach(log::info);
    }
}