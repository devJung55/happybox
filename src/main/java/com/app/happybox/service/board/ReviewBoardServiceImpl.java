package com.app.happybox.service.board;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.board.ReviewBoardDTO;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.user.Member;
import com.app.happybox.exception.BoardNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.board.BoardFileRepository;
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
        ReviewBoard reviewBoard = reviewBoardRepository.findById_QueryDSL(id).orElseThrow(() -> {
            throw new BoardNotFoundException();
        });
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

    //    현재 시퀀스 가져오기
    @Override
    public ReviewBoard getCurrentSequence() {
        return reviewBoardRepository.getCurrentSequence_QueryDsl();
    }

    @Override
    public Slice<ReviewBoardDTO> getReviewBoards(Pageable pageable) {
        Slice<ReviewBoard> reviewBoards =
                reviewBoardRepository.findAllByIdDescWithPaging_QueryDSL(PageRequest.of(0, 10));
        List<ReviewBoardDTO> collect = reviewBoards.get().map(board -> reviewBoardToDTO(board)).collect(Collectors.toList());

        return new SliceImpl<>(collect, pageable, reviewBoards.hasNext());
    }

    @Override
    public Slice<ReviewBoardDTO> getPopularReviewBoards(Pageable pageable) {
        Slice<ReviewBoard> reviewBoards =
                reviewBoardRepository.findAllByLikeCountDescWithPaging_QueryDSL(PageRequest.of(0, 10));
        List<ReviewBoardDTO> collect = reviewBoards.get().map(board -> reviewBoardToDTO(board)).collect(Collectors.toList());
        return new SliceImpl<>(collect, pageable, reviewBoards.hasNext());
    }

    @Override
    public Page<ReviewBoardDTO> findAllByMemberIdDescWithPaging_QueryDSL(Pageable pageable, Long memberId) {
        return null;
    }


}
