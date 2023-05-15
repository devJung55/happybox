package com.app.happybox.service.board;

import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.board.ReviewBoardDTO;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.exception.BoardNotFoundException;
import com.app.happybox.repository.board.BoardFileRepository;
import com.app.happybox.repository.board.ReviewBoardRepository;
import com.app.happybox.repository.subscript.SubscriptionRepository;
import com.app.happybox.repository.user.MemberRepository;
import com.app.happybox.type.FileRepresent;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
public class ReviewBoardServiceImpl implements ReviewBoardService {
    private final ReviewBoardRepository reviewBoardRepository;
    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final BoardFileRepository boardFileRepository;

    @Override
    public ReviewBoardDTO getDetail(Long id) {
        ReviewBoard reviewBoard = reviewBoardRepository.findById_QueryDSL(id).orElseThrow(() -> {
            throw new BoardNotFoundException();
        });
        return reviewBoardToDTO(reviewBoard);
    }

    @Override @Transactional
    public void write(ReviewBoardDTO reviewBoardDTO, Long memberId, Long subscriptionId) {
        List<BoardFileDTO> boardFileDTOS = reviewBoardDTO.getBoardFiles();
        memberRepository.findById(memberId).ifPresent(
                member -> reviewBoardDTO.setMemberDTO(toMemberDTO(member))
        );
        subscriptionRepository.findById(subscriptionId).ifPresent(
                subscription -> reviewBoardDTO.setSubscriptionDTO(toSubscriptionDTO(subscription))
        );
        reviewBoardRepository.save(toReviewBoardEntity(reviewBoardDTO));
        if(boardFileDTOS != null){
            for (int i = 0; i < boardFileDTOS.size(); i++) {
                if(i == 0){
                    boardFileDTOS.get(i).setFileRepresent(FileRepresent.REPRESENT);
                }else {
                    boardFileDTOS.get(i).setFileRepresent(FileRepresent.ORDINARY);
                }
                boardFileDTOS.get(i).setBoard(getCurrentSequence());
                boardFileRepository.save(toBoardFileEntity(boardFileDTOS.get(i)));
            }
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
