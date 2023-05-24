package com.app.happybox.entity.board;

import com.app.happybox.domain.user.WelfareDTO;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.type.DonateType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
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
    private String welfarePhone;
    private Integer welfarePoint;
    private String welfareId;

    /* 기부한 날짜 */
    private LocalDateTime boardRegisterDate;

    /* 작성한 복지관 */
    private WelfareDTO welfareDTO;

    /*-- 파일 리스트 --*/
    private List<BoardFileDTO> donationBoardFiles = new ArrayList<>();

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public DonationBoardDTO(){this.donationBoardFiles = new ArrayList<>();
    }

    @Builder
    public DonationBoardDTO(Long id, String boardTitle, String boardContent, DonateType donateType, String donateLocation, String welfareName, String welfarePhone, Integer welfarePoint, String welfareId, LocalDateTime boardRegisterDate, WelfareDTO welfareDTO, List<BoardFileDTO> donationBoardFiles, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.donateType = donateType;
        this.donateLocation = donateLocation;
        this.welfareName = welfareName;
        this.welfarePhone = welfarePhone;
        this.welfarePoint = welfarePoint;
        this.welfareId = welfareId;
        this.boardRegisterDate = boardRegisterDate;
        this.welfareDTO = welfareDTO;
        this.donationBoardFiles = donationBoardFiles;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public void setWelfareDTO(WelfareDTO welfareDTO) {
        this.welfareDTO = welfareDTO;
    }
}