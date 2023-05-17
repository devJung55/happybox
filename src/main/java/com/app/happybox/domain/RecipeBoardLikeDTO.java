package com.app.happybox.domain;

import com.app.happybox.entity.file.BoardFileDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter @ToString @Builder
public class RecipeBoardLikeDTO {
    private Long id;
    private Long recipeBoardId; // PK
    private String memberName;
    private String recipeBoardTitle;
    private LocalDateTime createdDate;
    private List<BoardFileDTO> boardFiles;
}
