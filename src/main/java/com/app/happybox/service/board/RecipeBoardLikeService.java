package com.app.happybox.service.board;

import com.app.happybox.domain.RecipeBoardLikeDTO;
import com.app.happybox.entity.board.RecipeBoardLike;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.BoardFileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

public interface RecipeBoardLikeService {
    //    마이페이지 찜 목록
    public Page<RecipeBoardLikeDTO> getListByMemberId(Pageable pageable, Long memberId);

    default RecipeBoardLikeDTO recipeBoardLikeToDTO(RecipeBoardLike recipeBoardLike) {
        return RecipeBoardLikeDTO.builder()
                .id(recipeBoardLike.getId())
                .memberName(recipeBoardLike.getMember().getMemberName())
                .recipeBoardId(recipeBoardLike.getRecipeBoard().getId())
                .recipeBoardTitle(recipeBoardLike.getRecipeBoard().getBoardTitle())
                .createdDate(recipeBoardLike.getRecipeBoard().getCreatedDate())
                .boardFiles(recipeBoardLike.getRecipeBoard().getRecipeBoardFiles().stream().map(file -> boardFileToDTO(file)).collect(Collectors.toList()))
                .build();
    }

    default BoardFileDTO boardFileToDTO(BoardFile boardFile) {
        return BoardFileDTO.builder()
                .id(boardFile.getId())
                .fileUuid(boardFile.getFileUuid())
                .filePath(boardFile.getFilePath())
                .fileOrgName(boardFile.getFileOrgName())
                .fileRepresent(boardFile.getFileRepresent())
                .build();
    }
}
