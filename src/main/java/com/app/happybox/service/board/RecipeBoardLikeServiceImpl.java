package com.app.happybox.service.board;

import com.app.happybox.domain.RecipeBoardLikeDTO;
import com.app.happybox.entity.board.RecipeBoardLike;
import com.app.happybox.repository.board.RecipeBoardLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("recipeBoardLike")
public class RecipeBoardLikeServiceImpl implements RecipeBoardLikeService {
    private final RecipeBoardLikeRepository recipeBoardLikeRepository;

    @Override
    public Page<RecipeBoardLikeDTO> getListByMemberId(Pageable pageable, Long memberId) {
        Page<RecipeBoardLike> recipeBoardLikes = recipeBoardLikeRepository.findBookmarkListWithMemberIdWithPaging_QueryDSL(pageable, memberId);
        List<RecipeBoardLikeDTO> recipeBoardLikeDTOS = recipeBoardLikes.get().map(this::recipeBoardLikeToDTO).collect(Collectors.toList());
        return new PageImpl<>(recipeBoardLikeDTOS, pageable, recipeBoardLikes.getTotalElements());
    }
}
