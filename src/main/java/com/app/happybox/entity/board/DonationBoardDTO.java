package com.app.happybox.entity.board;

import com.app.happybox.entity.file.BoardFileDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DonationBoardDTO {
    private Long id;

    //    연관된 Entity의 DTO(Welfare)
    private String welfareName;
    private String welfarePhone;
    private Integer welfarePoint;

    //    DonationBoard 기본 컬럼
    private String boardTitle;
    private String boardContent;

    /*-- 파일 리스트 --*/
    List<BoardFileDTO> boardFiles;

    @Builder
    public DonationBoardDTO(Long id, String welfareName, String welfarePhone, Integer welfarePoint, String boardTitle, String boardContent, List<BoardFileDTO> boardFiles) {
        this.id = id;
        this.welfareName = welfareName;
        this.welfarePhone = welfarePhone;
        this.welfarePoint = welfarePoint;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardFiles = boardFiles;
    }
}