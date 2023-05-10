package com.app.happybox.repository.board;

import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.type.FileRepresent;
import com.app.happybox.type.Gender;
import com.app.happybox.repository.user.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional @Rollback(false)
@Slf4j
public class RecipeBoardRepositoryTests {
    @Autowired private RecipeBoardRepository recipeBoardRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private BoardFileRepository boardFileRepository;

    @Test
    public void saveTest() {
//        for (int i = 0; i < 5; i++) {
//            RecipeBoard recipeBoard = new RecipeBoard("레시피 게시물_제목" + (i + 1), "레시피 게시물_내용" + (i + 1));
//            memberRepository.findById(1L).ifPresent(member -> recipeBoard.setMember(member));
//
//            recipeBoardRepository.save(recipeBoard);
//        }
        for(int i=0; i<5; i++){
            RecipeBoard recipeBoard = new RecipeBoard("테스트 제목" + (i+1), "테스트 내용" + (i+1));
            memberRepository.findById(1L).ifPresent(member -> recipeBoard.setMember(member));
            BoardFile boardFile = new BoardFile("2023/04/05", UUID.randomUUID().toString(), "레시피" + (i + 1) + ".png", FileRepresent.REPRESENT);
            BoardFile boardFile2 = new BoardFile("2023/04/05", UUID.randomUUID().toString(), "레시피" + (i + 1) + ".png", FileRepresent.ORDINARY);
            boardFile.setBoard(recipeBoard);
            boardFile2.setBoard(recipeBoard);

            boardFileRepository.save(boardFile);
            boardFileRepository.save(boardFile2);
            recipeBoardRepository.save(recipeBoard);
        }

    }

    // 레시피 게시글 목록 최신순 조회
    @Test
    public void findAllByIdDescWithPagingTest(){
        recipeBoardRepository.findAllByIdDescWithPaging_QueryDSL(
                PageRequest.of(0, 3)
        ).stream().map(RecipeBoard::toString).forEach(log::info);
    }

    // 레시피 게시글 목록 인기순 조회
    @Test
    public void findAllByLikeCountDescWithPagingTest(){
        recipeBoardRepository.findAllByLikeCountDescWithPaging_QueryDSL(
                PageRequest.of(0, 3)
        ).stream().map(RecipeBoard::toString).forEach(log::info);
    }

    //레시피 게시글 상세보기
    @Test
    public void findByIdTest(){
        recipeBoardRepository.findById(91L).map(RecipeBoard::toString).ifPresent(log::info);
    }

    //레시피 게시글 수정
    @Test
    public void updateTest(){
        recipeBoardRepository.findById(91L).ifPresent(recipeBoard -> {
            recipeBoard.setBoardTitle("수정된 레시피 제목1");
            recipeBoard.setBoardContent("수정된 레시피 내용1");
        });
    }

    @Test
    public void findRecipeBoardListByMemberIdWithPagingTest() {
        recipeBoardRepository.findRecipeBoardListByMemberIdWithPaging_QueryDSL(
                PageRequest.of(0, 1), 1L
        ).stream().map(RecipeBoard::toString).forEach(log::info);
    }

    @Test
    public void findTop5ByLikeCountWithDetailOrderByLikeCount_QueryDSL_Test() {
        // given
        List<RecipeBoard> recipeBoards = recipeBoardRepository.findTop5ByLikeCountWithRepresentFileOrderByLikeCount_QueryDSL();

        // when

        // then
        recipeBoards.stream().map(RecipeBoard::toString).forEach(log::info);
    }

    @Test
    public void findRecipeBoardCountByMemberId_QueryDSL_Test() {
        log.info("recipeBoardCount : " + recipeBoardRepository.findRecipeBoardCountByIdMemberId_QueryDSL(1L));
    }

    @Test
    public void findAllWithPaging_QueryDSL_Test() {
        recipeBoardRepository.findRecipeBoardListDescWithPaging_QueryDSL(PageRequest.of(0, 5))
                .stream().map(RecipeBoard::toString).forEach(log::info);
    }
}