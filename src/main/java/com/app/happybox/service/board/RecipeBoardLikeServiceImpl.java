package com.app.happybox.service.board;

import com.app.happybox.domain.RecipeBoardLikeDTO;
import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.board.RecipeBoardLike;
import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.board.ReviewBoardLike;
import com.app.happybox.entity.user.Member;
import com.app.happybox.exception.BoardNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.board.RecipeBoardLikeRepository;
import com.app.happybox.repository.board.RecipeBoardRepository;
import com.app.happybox.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("recipeBoardLike")
@Slf4j
public class RecipeBoardLikeServiceImpl implements RecipeBoardLikeService {
    private final RecipeBoardLikeRepository recipeBoardLikeRepository;
    private final RecipeBoardRepository recipeBoardRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean checkOutLike(Long recipeBoardId, Long memberId) {
        boolean check = recipeBoardLikeRepository.checkMemberLikesRecipeBoard_QueryDSL(recipeBoardId, memberId);
        log.info(String.valueOf(check));
        RecipeBoard recipeBoard = recipeBoardRepository.findById(recipeBoardId).orElseThrow(BoardNotFoundException::new);

        if(check) {
            // 삭제
            recipeBoardLikeRepository.deleteUserLikeByUserAndRecipeBoard(memberId, recipeBoardId);

            // 좋아요 수 감소
            Integer recipeLikeCount = recipeBoard.getRecipeLikeCount();
            recipeBoard.setRecipeLikeCount(--recipeLikeCount);

        } else {
            Member member = memberRepository.findById(memberId).orElseThrow(UserNotFoundException::new);

            // 저장
            recipeBoardLikeRepository.save(new RecipeBoardLike(member, recipeBoard));

            // 구독 좋아요 수 증가
            Integer recipeLikeCount = recipeBoard.getRecipeLikeCount();
            recipeBoard.setRecipeLikeCount(++recipeLikeCount);
        }

        return check;
    }

    @Override
    public boolean checkLike(Long recipeBoardId, Long memberId) {
        return recipeBoardLikeRepository.checkMemberLikesRecipeBoard_QueryDSL(recipeBoardId, memberId);
    }

    @Override
    public Page<RecipeBoardLikeDTO> getListByMemberId(Pageable pageable, Long memberId) {
        Page<RecipeBoardLike> recipeBoardLikes = recipeBoardLikeRepository.findBookmarkListWithMemberIdWithPaging_QueryDSL(pageable, memberId);
        List<RecipeBoardLikeDTO> recipeBoardLikeDTOS = recipeBoardLikes.get().map(this::recipeBoardLikeToDTO).collect(Collectors.toList());
        return new PageImpl<>(recipeBoardLikeDTOS, pageable, recipeBoardLikes.getTotalElements());
    }

    @Override
    public void cancelBookmarkRecipeById(Long id) {
        recipeBoardLikeRepository.deleteById(id);
    }
}
