package com.app.happybox.service.board;

import com.app.happybox.entity.board.*;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.Welfare;
import com.app.happybox.exception.BoardNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.board.BoardFileRepository;
import com.app.happybox.repository.board.DonationBoardRepository;
import com.app.happybox.repository.user.WelfareRepository;
import com.app.happybox.type.FileRepresent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("donationBoard")
@Slf4j
public class DonationBoardServiceImpl implements DonationBoardService {
    private final DonationBoardRepository donationBoardRepository;
    private final WelfareRepository welfareRepository;
    private final BoardFileRepository boardFileRepository;

    @Override
    public void write(DonationBoardDTO donationBoardDTO, Long welfareId) {
        List<BoardFileDTO> boardFileDTOS = donationBoardDTO.getDonationBoardFiles();
        // 아이디 조회 실패 시 Exception
        Welfare welfare = welfareRepository.findById(welfareId).orElseThrow(UserNotFoundException::new);

        // 게시판에 setMember
        DonationBoard donationBoard = toDonationBoardEntity(donationBoardDTO);
        donationBoard.setWelfare(welfare);

        donationBoardRepository.save(donationBoard);

        int count = 0;

        for (int i = 0; i < boardFileDTOS.size(); i++) {
            if(boardFileDTOS.get(i) == null) continue;

            if (count == 0) {
                boardFileDTOS.get(i).setFileRepresent(FileRepresent.REPRESENT);
                count++;
            } else {
                boardFileDTOS.get(i).setFileRepresent(FileRepresent.ORDINARY);
            }

            boardFileDTOS.get(i).setDonationBoardDTO(donationBoardToDTO(getCurrentSequence()));
            // 엔티티
            BoardFile boardFile = toBoardFileEntity(boardFileDTOS.get(i));

            boardFile.setDonationBoard(donationBoard);

            boardFileRepository.save(boardFile);
        }
    }


    @Override @Transactional
    public void update(DonationBoardDTO donationBoardDTO, Long welfareId) {
        List<BoardFileDTO> boardFileDTOS = donationBoardDTO.getDonationBoardFiles();

        DonationBoard donationBoard = donationBoardRepository.findById(donationBoardDTO.getId()).orElseThrow(BoardNotFoundException::new);

        donationBoard.setBoardTitle(donationBoardDTO.getBoardTitle());
        donationBoard.setBoardContent(donationBoardDTO.getBoardContent());
        donationBoard.setDonateType(donationBoardDTO.getDonateType());

        // 기존파일 삭제
        Long deleteCount = boardFileRepository.deleteByRecipeBoardId(donationBoardDTO.getId());

        log.info("============== {} ============", deleteCount);

//        reviewBoardDTO.getReviewBoardFiles().forEach(file -> {
//            log.info("============ 파일 반복문 ===========");
//            // 새로운 파일
//            BoardFile boardFile = toBoardFileEntity(file);
//
//
//            boardFile.setReviewBoard(reviewBoard);
//            boardFileRepository.save(boardFile);
//        });
        int count = 0;

        for (int i = 0; i < boardFileDTOS.size(); i++) {
            if(boardFileDTOS.get(i) == null) continue;

            if (count == 0) {
                boardFileDTOS.get(i).setFileRepresent(FileRepresent.REPRESENT);
                count++;
            } else {
                boardFileDTOS.get(i).setFileRepresent(FileRepresent.ORDINARY);
            }

            boardFileDTOS.get(i).setDonationBoardDTO(donationBoardToDTO(getCurrentSequence()));
            // 엔티티
            BoardFile boardFile = toBoardFileEntity(boardFileDTOS.get(i));

            boardFile.setDonationBoard(donationBoard);

            boardFileRepository.save(boardFile);
        }

    }

    @Override
    public void delete(Long id, Long welfareId) {
        DonationBoard donationBoard = donationBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        donationBoardRepository.delete(donationBoard);
    }

    @Override
    public DonationBoard getCurrentSequence() {
        return donationBoardRepository.getCurrentSequence_QueryDsl();
    }

    @Override
    public Page<DonationBoardDTO> getList(Pageable pageable) {
        Page<DonationBoard> donationBoards = donationBoardRepository.findAllByIdDescWithPaging_QueryDSL(PageRequest.of(0, 10));
        List<DonationBoardDTO> collect = donationBoards.get().map(board -> donationBoardToDTO(board)).collect(Collectors.toList());
        return new PageImpl<>(collect, pageable, donationBoards.getTotalElements());
    }

    @Override
    public DonationBoardDTO getDetail(Long id) {
        DonationBoard donationBoard = donationBoardRepository.findById(id).orElseThrow(() -> {
            throw new BoardNotFoundException();
        });
        return donationBoardToDTO(donationBoard);
    }

    @Override
    public List<DonationBoardDTO> findTop3OrderByDate_QueryDSL() {
        return donationBoardRepository.findTop3OrderByDate_QueryDSL().stream()
                .map(this::donationBoardToDTO).collect(Collectors.toList());
    }

    @Override
    public Page<DonationBoard> findAllWithPaging_QueryDSL(Pageable pageable) {
        return null;
    }
}
