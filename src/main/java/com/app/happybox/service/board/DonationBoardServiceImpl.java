package com.app.happybox.service.board;

import com.app.happybox.entity.board.*;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.BoardFileDTO;
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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    @Transactional(rollbackFor = Exception.class)
    public void write(DonationBoardDTO donationBoardDTO, Long welfareId) {
        List<BoardFileDTO> boardFileDTOS = donationBoardDTO.getDonationBoardFiles();
        log.info(boardFileDTOS.toString());
        // 아이디 조회 실패 시 Exception
        Welfare welfare = welfareRepository.findById(welfareId).orElseThrow(UserNotFoundException::new);
        Integer point = 1000;

        // 게시판에 setMember
        DonationBoard donationBoard = toDonationBoardEntity(donationBoardDTO);
        donationBoard.setWelfare(welfare);

        donationBoardRepository.save(donationBoard);
        welfare.setWelfarePointTotal(welfare.getWelfarePointTotal() + point);

        int index = 0;

        // boardFile donationBoard set 후 영속화
        for (int i = 0; i < boardFileDTOS.size(); i++) {
            BoardFile boardFile = toBoardFileEntity(boardFileDTOS.get(i));
            if(index < 1) boardFile.setFileRepresent(FileRepresent.REPRESENT);

            boardFile.setDonationBoard(donationBoard);
            boardFileRepository.save(boardFile);

            index++;
        }
    }


    @Override @Transactional(rollbackFor = Exception.class)
    public void update(DonationBoardDTO donationBoardDTO, Long welfareId) {
        List<BoardFileDTO> boardFileDTOS = donationBoardDTO.getDonationBoardFiles();

        DonationBoard donationBoard = donationBoardRepository.findById(donationBoardDTO.getId()).orElseThrow(BoardNotFoundException::new);

        donationBoard.setBoardTitle(donationBoardDTO.getBoardTitle());
        donationBoard.setBoardContent(donationBoardDTO.getBoardContent());
        donationBoard.setDonateType(donationBoardDTO.getDonateType());

        // 기존파일 삭제
        Long deleteCount = boardFileRepository.deleteByDonationBoardId(donationBoardDTO.getId());

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
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id, Long welfareId) {
        DonationBoard donationBoard = donationBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        Welfare welfare = welfareRepository.findById(welfareId).orElseThrow(UserNotFoundException::new);
        Integer point = 1000;
        donationBoardRepository.delete(donationBoard);
        welfare.setWelfarePointTotal(welfare.getWelfarePointTotal() - point);

    }

    @Override
    public DonationBoard getCurrentSequence() {
        return donationBoardRepository.getCurrentSequence_QueryDsl();
    }

    @Override
    public Slice<DonationBoardDTO> getRecentList(Pageable pageable) {
        Slice<DonationBoard> donationBoards =
                donationBoardRepository.findAllByIdDescWithPaging_QueryDSL(pageable);
        List<DonationBoardDTO> collect = donationBoards.get().map(board -> donationBoardToDTO(board)).collect(Collectors.toList());

        return new SliceImpl<>(collect, pageable, donationBoards.hasNext());
    }

    @Override
    public Slice<DonationBoardDTO> getPopularList(Pageable pageable) {
        Slice<DonationBoard> donationBoards =
                donationBoardRepository.findAllByPointDescWithPaging_QueryDSL(pageable);
        List<DonationBoardDTO> collect = donationBoards.get().map(board -> donationBoardToDTO(board)).collect(Collectors.toList());

        return new SliceImpl<>(collect, pageable, donationBoards.hasNext());
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
    public Page<DonationBoardDTO> adminGetList(Pageable pageable) {
        Page<DonationBoard> donationBoards = donationBoardRepository.findAllWithPaging_QueryDSL(pageable);
        List<DonationBoardDTO> donationBoardDTOS = donationBoards.get().map(this::adminDonationBoardToDTO).collect(Collectors.toList());
        return new PageImpl<>(donationBoardDTOS, pageable, donationBoards.getTotalElements());
    }
}
