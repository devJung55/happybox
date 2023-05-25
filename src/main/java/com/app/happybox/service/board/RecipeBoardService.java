package com.app.happybox.service.board;

import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.board.RecipeBoardDTO;
import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.board.ReviewBoardDTO;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.user.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface RecipeBoardService {
    // 상세보기
    public RecipeBoardDTO getDetail(Long id);

    // 작성하기
    public void write(RecipeBoardDTO recipeBoardDTO, Long memberId);

    // 수정
    public void update(RecipeBoardDTO recipeBoardDTO, Long memberId);

    // 삭제
    public void delete(Long id, Long memberId);

    //    현재 시퀀스 가져오기
    public RecipeBoard getCurrentSequence();

    //    목록 페이징(최신순)
    public Slice<RecipeBoardDTO> getRecipeBoards(Pageable pageable);

    //    목록 페이징(인기순)
    public Slice<RecipeBoardDTO> getPopularRecipeBoards(Pageable pageable);

    //    마이페이지 목록
    public Page<RecipeBoardDTO> getListByMemberId(Pageable pageable, Long memberId);

    //    댓글 목록
    public List<RecipeBoard> findRecipeBoardReplyCountByMemberId_QueryDSL(Long memberId);

    //    레시피 추천순 Top5
    public List<RecipeBoardDTO> findTop5ByLikeCountWithRepresentFileOrderByLikeCount_QueryDSL();

    //    마이페이지 게시물건수 조회
    public Long getCountByMemberId(Long memberId);

    //    관리자 레시피 게시글 목록
    public Page<RecipeBoardDTO> getList(Pageable pageable);

//    관리자 레시피 게시글 조회
    public Optional<RecipeBoardDTO> getRecipeBoardDetailById(Long recipeBoardId);

    default RecipeBoardDTO recipeBoardToDTO(RecipeBoard recipeBoard){
        return RecipeBoardDTO.builder()
                .id(recipeBoard.getId())
                .memberDTO(toMemberDTO(recipeBoard.getMember()))
                .boardTitle(recipeBoard.getBoardTitle())
                .boardContent(recipeBoard.getBoardContent())
                .boardRegisterDate(recipeBoard.getUpdatedDate().toLocalDate())
                .recipeLikeCount(recipeBoard.getRecipeLikeCount())
                .recipeReplyCount(recipeBoard.getRecipeBoardReplyCount())
                .recipeBoardFiles(boardFileToDTO(recipeBoard.getRecipeBoardFiles()))
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

    default RecipeBoardDTO mypageRecipeBoardToDTO(RecipeBoard recipeBoard){
        return RecipeBoardDTO.builder()
                .id(recipeBoard.getId())
                .memberDTO(toMemberDTO(recipeBoard.getMember()))
                .boardTitle(recipeBoard.getBoardTitle())
                .boardContent(recipeBoard.getBoardContent())
                .boardRegisterDate(recipeBoard.getUpdatedDate().toLocalDate())
                .recipeBoardFiles(recipeBoard.getRecipeBoardFiles().stream().map(file -> boardFileToDTO(file)).collect(Collectors.toList()))
                .build();
    }

    default RecipeBoard toRecipeBoardEntity(RecipeBoardDTO recipeBoardDTO){
        return RecipeBoard.builder()
                .id(recipeBoardDTO.getId())
                .boardTitle(recipeBoardDTO.getBoardTitle())
                .boardContent(recipeBoardDTO.getBoardContent())
                .build();
    }

    default List<BoardFileDTO> boardFileToDTO(List<BoardFile> recipeBoardFiles){
        List<BoardFileDTO> boardFileDTOS = new ArrayList<>();
        recipeBoardFiles.forEach(
                boardFile -> {
                    BoardFileDTO boardFileDTO = BoardFileDTO.builder()
                            .id(boardFile.getId())
                            .fileUuid(boardFile.getFileUuid())
                            .filePath(boardFile.getFilePath())
                            .fileOrgName(boardFile.getFileOrgName())
                            .fileRepresent(boardFile.getFileRepresent())
                            .build();
                    boardFileDTOS.add(boardFileDTO);
                }
        );
        return boardFileDTOS;
    }

    default BoardFile toBoardFileEntity(BoardFileDTO boardFileDTO){
        return BoardFile.builder()
                .fileUuid(boardFileDTO.getFileUuid())
                .filePath(boardFileDTO.getFilePath())
                .fileOrgName(boardFileDTO.getFileOrgName())
                .fileRepresent(boardFileDTO.getFileRepresent())
                .build();
    }

    default MemberDTO toMemberDTO(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .userPassword(member.getUserPassword())
                .userPhoneNumber(member.getUserPhoneNumber())
                .address(member.getAddress())
                .userEmail(member.getUserEmail())
                .userStatus(member.getUserStatus())
                .userRole(member.getUserRole())
                .memberName(member.getMemberName())
                .memberBirth(member.getMemberBirth())
                .memberDeliveryAddress(member.getMemberDeliveryAddress())
                .memberGender(member.getMemberGender())
                .build();
    }

    default Member toMemberEntity(MemberDTO memberDTO) {
        return Member.builder()
                .userId(memberDTO.getUserId())
                .userPassword(memberDTO.getUserPassword())
                .userPhoneNumber(memberDTO.getUserPhoneNumber())
                .userRole(memberDTO.getUserRole())
                .userRole(memberDTO.getUserRole())
                .memberName(memberDTO.getMemberName())
                .memberBirth(memberDTO.getMemberBirth())
                .memberDeliveryAddress(memberDTO.getMemberDeliveryAddress())
                .userEmail(memberDTO.getUserEmail())
                .memberGender(memberDTO.getMemberGender())
                .build();
    }

}
