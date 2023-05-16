package com.app.happybox.entity.file;

import com.app.happybox.entity.board.Board;
import com.app.happybox.entity.board.DonationBoardDTO;
import com.app.happybox.entity.board.RecipeBoardDTO;
import com.app.happybox.entity.board.ReviewBoardDTO;
import com.app.happybox.type.FileRepresent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class BoardFileDTO {
    private Long id;

    private String filePath;
    private String fileUuid;
    private String fileOrgName;

    private FileRepresent fileRepresent;

    private ReviewBoardDTO reviewBoardDTO;

    private RecipeBoardDTO recipeBoardDTO;

    private DonationBoardDTO donationBoardDTO;

    @Builder
    public BoardFileDTO(Long id, String filePath, String fileUuid, String fileOrgName, FileRepresent fileRepresent, ReviewBoardDTO reviewBoardDTO, RecipeBoardDTO recipeBoardDTO, DonationBoardDTO donationBoardDTO) {
        this.id = id;
        this.filePath = filePath;
        this.fileUuid = fileUuid;
        this.fileOrgName = fileOrgName;
        this.fileRepresent = fileRepresent;
        this.reviewBoardDTO = reviewBoardDTO;
        this.recipeBoardDTO = recipeBoardDTO;
        this.donationBoardDTO = donationBoardDTO;
    }

    public void setFileRepresent(FileRepresent fileRepresent) {
        this.fileRepresent = fileRepresent;
    }

    public void setReviewBoardDTO(ReviewBoardDTO reviewBoardDTO) {
        this.reviewBoardDTO = reviewBoardDTO;
    }

    public void setRecipeBoardDTO(RecipeBoardDTO recipeBoardDTO) {
        this.recipeBoardDTO = recipeBoardDTO;
    }

    public void setDonationBoardDTO(DonationBoardDTO donationBoardDTO) {
        this.donationBoardDTO = donationBoardDTO;
    }
}
