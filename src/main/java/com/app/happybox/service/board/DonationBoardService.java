package com.app.happybox.service.board;

import com.app.happybox.domain.user.WelfareDTO;
import com.app.happybox.entity.board.DonationBoard;
import com.app.happybox.entity.board.DonationBoardDTO;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.user.Welfare;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface DonationBoardService {
    //    기부 게시글 작성
    public void write(DonationBoardDTO donationBoardDTO, Long welfareId);

    //    기부 게시글 수정
    public void update(DonationBoardDTO donationBoardDTO, Long welfareId);

    //    기부 게시글 삭제
    public void delete(Long id, Long memberId);

    //    현재 시퀀스 가져오기
    public DonationBoard getCurrentSequence();

    //    기부 게시글 목록 페이징 처리(최신순)
    public Slice<DonationBoardDTO> getRecentList(Pageable pageable);

    //    기부 게시글 목록 페이징 처리(기부순)
    public Slice<DonationBoardDTO> getPopularList(Pageable pageable);

    //    기부 게시글 상세 보기
    public DonationBoardDTO getDetail(Long id);

    //    최신순 Top3 조회(메인)
    public List<DonationBoardDTO> findTop3OrderByDate_QueryDSL();

    //    관리자 기부글 목록
    public Page<DonationBoardDTO> adminGetList(Pageable pageable);


    default DonationBoardDTO donationBoardToDTO(DonationBoard donationBoard){
        return DonationBoardDTO.builder()
                .id(donationBoard.getId())
                .boardTitle(donationBoard.getBoardTitle())
                .boardContent(donationBoard.getBoardContent())
                .welfareName(donationBoard.getWelfare().getWelfareName())
                .welfarePhone(donationBoard.getWelfare().getUserPhoneNumber())
                .welfarePoint(donationBoard.getWelfare().getWelfarePointTotal())
                .welfareId(donationBoard.getWelfare().getUserId())
                .donateType(donationBoard.getDonateType())
                .boardRegisterDate(donationBoard.getUpdatedDate())
                .donateLocation(donationBoard.getDonateLocation())
                .donationBoardFiles(boardFileToDTO(donationBoard.getDonationBoardFiles()))
                .build();
    }

    default DonationBoardDTO adminDonationBoardToDTO(DonationBoard donationBoard){
        return DonationBoardDTO.builder()
                .id(donationBoard.getId())
                .boardTitle(donationBoard.getBoardTitle())
                .boardContent(donationBoard.getBoardContent())
                .donateType(donationBoard.getDonateType())
                .donateLocation(donationBoard.getDonateLocation())
                .welfareName(donationBoard.getWelfare().getWelfareName())
                .welfarePhone(donationBoard.getWelfare().getUserPhoneNumber())
                .welfarePoint(donationBoard.getWelfare().getWelfarePointTotal())
                .boardRegisterDate(donationBoard.getCreatedDate())
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

    default DonationBoard toDonationBoardEntity(DonationBoardDTO donationBoardDTO){
        return DonationBoard.builder()
                .id(donationBoardDTO.getId())
                .boardTitle(donationBoardDTO.getBoardTitle())
                .boardContent(donationBoardDTO.getBoardContent())
                .donateType(donationBoardDTO.getDonateType())
                .donateLocation(donationBoardDTO.getDonateLocation())
                .build();
    }

    default List<BoardFileDTO> boardFileToDTO(List<BoardFile> donationBoardFiles){
        List<BoardFileDTO> boardFileDTOS = new ArrayList<>();
        donationBoardFiles.forEach(
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

    default WelfareDTO toWelfareDTO(Welfare welfare) {
        return WelfareDTO.builder()
                .id(welfare.getId())
                .userId(welfare.getUserId())
                .userPhoneNumber(welfare.getUserPhoneNumber())
                .address(welfare.getAddress())
                .userRole(welfare.getUserRole())
                .welfareName(welfare.getWelfareName())
                .welfarePointTotal(welfare.getWelfarePointTotal())
                .build();
    }

    default Welfare toWelfareEntity(WelfareDTO welfareDTO){
        return Welfare.builder()
                .id(welfareDTO.getId())
                .userId(welfareDTO.getUserId())
                .userPhoneNumber(welfareDTO.getUserPhoneNumber())
                .address(welfareDTO.getAddress())
                .userRole(welfareDTO.getUserRole())
                .welfareName(welfareDTO.getWelfareName())
                .welfarePointTotal(welfareDTO.getWelfarePointTotal())
                .build();
    }
}
