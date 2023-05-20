package com.app.happybox.service.board;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.board.ReviewBoardDTO;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.reply.ProductReply;
import com.app.happybox.entity.reply.ReplyDTO;
import com.app.happybox.entity.user.Member;
import com.app.happybox.exception.BoardNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.board.BoardFileRepository;
import com.app.happybox.repository.board.ReviewBoardFileRepository;
import com.app.happybox.repository.board.ReviewBoardRepository;
import com.app.happybox.repository.subscript.SubscriptionRepository;
import com.app.happybox.repository.user.MemberRepository;
import com.app.happybox.repository.user.WelfareRepository;
import com.app.happybox.type.FileRepresent;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("reviewBoard")
@Slf4j
public class ReviewBoardServiceImpl implements ReviewBoardService {
    private final ReviewBoardRepository reviewBoardRepository;
    private final MemberRepository memberRepository;
    private final BoardFileRepository boardFileRepository;

    @Override
    public ReviewBoardDTO getDetail(Long id) {
        ReviewBoard reviewBoard = reviewBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        return reviewBoardToDTO(reviewBoard);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void write(ReviewBoardDTO reviewBoardDTO, Long memberId) {
        List<BoardFileDTO> boardFileDTOS = reviewBoardDTO.getReviewBoardFiles();
        // 아이디 조회 실패 시 Exception
        Member member = memberRepository.findById(memberId).orElseThrow(UserNotFoundException::new);

        // 게시판에 setMember
        ReviewBoard reviewBoard = toReviewBoardEntity(reviewBoardDTO);
        reviewBoard.setMember(member);

        reviewBoardRepository.save(reviewBoard);

        int count = 0;

        for (int i = 0; i < boardFileDTOS.size(); i++) {
            if(boardFileDTOS.get(i) == null) continue;

            if (count == 0) {
                boardFileDTOS.get(i).setFileRepresent(FileRepresent.REPRESENT);
                count++;
            } else {
                boardFileDTOS.get(i).setFileRepresent(FileRepresent.ORDINARY);
            }

            boardFileDTOS.get(i).setReviewBoardDTO(reviewBoardToDTO(getCurrentSequence()));
            // 엔티티
            BoardFile boardFile = toBoardFileEntity(boardFileDTOS.get(i));

            boardFile.setReviewBoard(reviewBoard);

            boardFileRepository.save(boardFile);
        }
    }

    @Override @Transactional
    public void update(ReviewBoardDTO reviewBoardDTO, Long memberId) {
        List<BoardFileDTO> boardFileDTOS = reviewBoardDTO.getReviewBoardFiles();

        ReviewBoard reviewBoard = reviewBoardRepository.findById(reviewBoardDTO.getId()).orElseThrow(BoardNotFoundException::new);

        reviewBoard.setBoardTitle(reviewBoardDTO.getBoardTitle());
        reviewBoard.setBoardContent(reviewBoardDTO.getBoardContent());
        reviewBoard.setWelfareName(reviewBoardDTO.getWelfareName());

        // 기존파일 삭제
        Long deleteCount = boardFileRepository.deleteByReviewBoardId(reviewBoardDTO.getId());

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

            boardFileDTOS.get(i).setReviewBoardDTO(reviewBoardToDTO(getCurrentSequence()));
            // 엔티티
            BoardFile boardFile = toBoardFileEntity(boardFileDTOS.get(i));

            boardFile.setReviewBoard(reviewBoard);

            boardFileRepository.save(boardFile);
        }

    }

    @Override
    public void delete(Long id, Long memberId) {
        ReviewBoard reviewBoard = reviewBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        reviewBoardRepository.delete(reviewBoard);
    }

    //    현재 시퀀스 가져오기
    @Override
    public ReviewBoard getCurrentSequence() {
        return reviewBoardRepository.getCurrentSequence_QueryDsl();
    }

    @Override
    public Slice<ReviewBoardDTO> getReviewBoards(Pageable pageable) {
        Slice<ReviewBoard> reviewBoards =
                reviewBoardRepository.findAllByIdDescWithPaging_QueryDSL(pageable);
        List<ReviewBoardDTO> collect = reviewBoards.get().map(board -> reviewBoardToDTO(board)).collect(Collectors.toList());

        return new SliceImpl<>(collect, pageable, reviewBoards.hasNext());
    }

    @Override
    public Slice<ReviewBoardDTO> getPopularReviewBoards(Pageable pageable) {
        Slice<ReviewBoard> reviewBoards =
                reviewBoardRepository.findAllByLikeCountDescWithPaging_QueryDSL(pageable);
        List<ReviewBoardDTO> collect = reviewBoards.get().map(board -> reviewBoardToDTO(board)).collect(Collectors.toList());
        return new SliceImpl<>(collect, pageable, reviewBoards.hasNext());
    }

//    마이페이지 나의 리뷰 목록
    @Override
    public Page<ReviewBoardDTO> getReviewListByMemberId(Pageable pageable, Long memberId) {
        Page<ReviewBoard> reviewBoards = reviewBoardRepository.findAllByMemberIdDescWithPaging_QueryDSL(pageable, memberId);
        List<ReviewBoardDTO> reviewBoardDTOS = reviewBoards.get().map(this::reviewBoardToDTO).collect(Collectors.toList());
        return new PageImpl<>(reviewBoardDTOS, pageable, reviewBoards.getTotalElements());
    }

    @Override
    public List<ReviewBoardDTO> findTop8Recent() {
        List<ReviewBoard> reviewBoards = reviewBoardRepository.findTop8OrderByDate_QueryDSL();
        return reviewBoards.stream().map(this::reviewBoardToDTO).collect(Collectors.toList());
    }


}
