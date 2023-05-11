package com.app.happybox.service.board;

import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.board.RecipeBoardDTO;
import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.board.ReviewBoardDTO;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.BoardFileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public interface RecipeBoardService {
    //    목록 페이징(최신순)
    public Slice<RecipeBoardDTO> getRecipeBoards(Pageable pageable);

    //    목록 페이징(인기순)
    public Slice<RecipeBoardDTO> getPopularRecipeBoards(Pageable pageable);

    //    마이페이지 목록
    public Page<RecipeBoardDTO> getListByMemberId(Pageable pageable, Long memberId);

    //    댓글 목록
    public List<RecipeBoard> findRecipeBoardReplyCountByMemberId_QueryDSL(Long memberId);

    //    레시피 추천순 Top5
    public List<RecipeBoard> findTop5ByLikeCountWithRepresentFileOrderByLikeCount_QueryDSL();

    //    마이페이지 게시물건수 조회
    public Long getCountByMemberId(Long memberId);

    //    관리자 레시피 게시글 목록
    public Page<RecipeBoardDTO> getList(Pageable pageable);

    default RecipeBoardDTO recipeBoardToDTO(RecipeBoard recipeBoard){
        return RecipeBoardDTO.builder()
                .id(recipeBoard.getId())
                .memberName(recipeBoard.getMember().getMemberName())
                .recipeBoardTitle(recipeBoard.getBoardTitle())
                .recipeBoardContent(recipeBoard.getBoardContent())
                .likeCount(recipeBoard.getRecipeLikeCount())
                .replyCount(recipeBoard.getRecipeBoardReplyCount())
                .boardFiles(recipeBoard.getBoardFiles().stream().map(file -> boardFileToDTO(file)).collect(Collectors.toList()))
                .build();
    }

    default BoardFileDTO boardFileToDTO(BoardFile boardFile){
        return BoardFileDTO.builder()
                .id(boardFile.getId())
                .fileUuid(boardFile.getFileUuid())
                .filePath(boardFile.getFilePath())
                .fileOrgName(boardFile.getFileOrgName())
                .fileRepresent(boardFile.getFileRepresent())
                .build();
    }

    default RecipeBoardDTO mypageRecipeBoardToDTO(RecipeBoard recipeBoard){ // 지영이
        return RecipeBoardDTO.builder()
                .id(recipeBoard.getId())
                .memberName(recipeBoard.getMember().getMemberName())
                .recipeBoardTitle(recipeBoard.getBoardTitle())
                .recipeBoardContent(recipeBoard.getBoardContent())
                .likeCount(recipeBoard.getRecipeLikeCount())
                .replyCount(recipeBoard.getRecipeBoardReplyCount())
                .boardFiles(recipeBoard.getBoardFiles().stream().map(file -> boardFileToDTO(file)).collect(Collectors.toList()))
                .build();
    }
}
