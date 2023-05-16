package com.app.happybox.service.board;

import com.app.happybox.entity.board.DonationBoard;
import com.app.happybox.entity.board.DonationBoardDTO;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.BoardFileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public interface DonationBoardService {
    //    기부 게시글 목록 페이징 처리
    public Page<DonationBoardDTO> getList(Pageable pageable);

    //    기부 게시글 상세 보기
    public DonationBoardDTO getDetail(Long id);

    //    최신순 Top3 조회(메인)
    public List<DonationBoardDTO> findTop3OrderByDate_QueryDSL();

    //    관리자 기부글 목록
    public Page<DonationBoard> findAllWithPaging_QueryDSL(Pageable pageable);


    default DonationBoardDTO donationBoardToDTO(DonationBoard donationBoard){
        return DonationBoardDTO.builder()
                .id(donationBoard.getId())
                .welfareName(donationBoard.getWelfare().getWelfareName())
                .welfarePhone(donationBoard.getWelfare().getUserPhoneNumber())
                .welfarePoint(donationBoard.getWelfare().getWelfarePointTotal())
                .boardTitle(donationBoard.getBoardTitle())
                .boardContent(donationBoard.getBoardContent())
                .donationBoardFiles(donationBoard.getDonationBoardFiles().stream().map(file -> boardFileToDTO(file)).collect(Collectors.toList()))
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
}
