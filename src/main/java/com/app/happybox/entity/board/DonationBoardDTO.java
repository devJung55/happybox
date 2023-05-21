package com.app.happybox.entity.board;

import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.type.DonateType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder @ToString
public class DonationBoardDTO {
    private Long id;

    //    DonationBoard 기본 컬럼
    private String boardTitle;
    private String boardContent;

    private DonateType donateType;

    /* 기부할 급식소 */
    private String donateLocation;

    /* 기부한 복지관명 */
    private String welfareName;

    /* 기부한 날짜 */
    private LocalDateTime boardRegisterDate;

    /*-- 파일 리스트 --*/
    List<BoardFileDTO> donationBoardFiles;

    public DonationBoardDTO(){this.donationBoardFiles = new ArrayList<>();
    }

    @Builder
    public DonationBoardDTO(Long id, String boardTitle, String boardContent, DonateType donateType, String donateLocation, String welfareName, LocalDateTime boardRegisterDate, List<BoardFileDTO> donationBoardFiles) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.donateType = donateType;
        this.donateLocation = donateLocation;
        this.welfareName = welfareName;
        this.boardRegisterDate = boardRegisterDate;
        this.donationBoardFiles = donationBoardFiles;
    }
}