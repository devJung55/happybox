package com.app.happybox.service.board;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.board.ReviewBoardDTO;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.reply.ReplyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ReviewBoardService {
    // 상세보기
    public ReviewBoardDTO getDetail(Long id);

    //    작성하기
    public void write(ReviewBoardDTO reviewBoardDTO, Long userId);

    //    목록 페이징(최신순)
    public Slice<ReviewBoardDTO> getReviewBoards(Pageable pageable);

    //    목록 페이징(인기순)
    public Slice<ReviewBoardDTO> getPopularReviewBoards(Pageable pageable);

    //    마이페이지 나의후기 목록
    public Page<ReviewBoardDTO> findAllByMemberIdDescWithPaging_QueryDSL(Pageable pageable, Long memberId);


    default ReviewBoardDTO reviewBoardToDTO(ReviewBoard reviewBoard){
        return ReviewBoardDTO.builder()
                .id(reviewBoard.getId())
                .memberName(reviewBoard.getMember().getMemberName())
                .welfareName(reviewBoard.getSubscription().getWelfare().getWelfareName())
                .reviewBoardTitle(reviewBoard.getBoardTitle())
                .reviewBoardContent(reviewBoard.getBoardContent())
                .reviewRating(reviewBoard.getReviewRating())
                .reviewBoardRegisterDate(reviewBoard.getUpdatedDate().toLocalDate())
                .reviewLikeCount(reviewBoard.getReviewLikeCount().longValue())
                .reviewBoardReplyCount(reviewBoard.getReviewBoardReplyCount().longValue())
                .boardFiles(reviewBoard.getBoardFiles().stream().map(file -> boardFileToDTO(file)).collect(Collectors.toList()))
                .build();
    }

    default ReviewBoard toReviewBoardEntity(ReviewBoardDTO reviewBoardDTO){
        return ReviewBoard.builder()
                .boardTitle(reviewBoardDTO.getReviewBoardTitle())
                .boardContent(reviewBoardDTO.getReviewBoardContent())
                .reviewRating(reviewBoardDTO.getReviewRating())
//                .boardFiles(reviewBoardDTO.getBoardFiles().stream().map(file -> toBoardFileEntity(file)).collect(Collectors.toList()))
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

    default BoardFile toBoardFileEntity(BoardFileDTO boardFileDTO){
        return BoardFile.builder()
                .id(boardFileDTO.getId())
                .fileUuid(boardFileDTO.getFileUuid())
                .filePath(boardFileDTO.getFilePath())
                .fileOrgName(boardFileDTO.getFileOrgName())
                .fileRepresent(boardFileDTO.getFileRepresent())
                .build();
    }

    default ReviewBoardDTO myReviewBoardToDTO(ReviewBoard reviewBoard){ // 지영이
        return ReviewBoardDTO.builder()
                .id(reviewBoard.getId())
                .memberName(reviewBoard.getMember().getMemberName())
                .memberId(reviewBoard.getMember().getId())
                .reviewBoardTitle(reviewBoard.getBoardTitle())
                .reviewBoardContent(reviewBoard.getBoardContent())
                .reviewRating(reviewBoard.getReviewRating())
                .reviewBoardRegisterDate(reviewBoard.getUpdatedDate().toLocalDate())
                .boardFiles(reviewBoard.getBoardFiles().stream().map(file -> boardFileToDTO(file)).collect(Collectors.toList()))
                .build();
    }
}
