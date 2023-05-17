package com.app.happybox.service.subscript;

import com.app.happybox.domain.SubscriptionLikeDTO;
import com.app.happybox.entity.subscript.SubscriptionLike;
import com.app.happybox.repository.subscript.SubscriptionLikeRepository;
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
@Qualifier("subscriptionLike")
public class SubscriptionLikeServiceImpl implements SubscriptionLikeService {
    private final SubscriptionLikeRepository subscriptionLikeRepository;

    @Override
    public Page<SubscriptionLikeDTO> getListSubscriptionBookmarkByMemberId(Pageable pageable, Long memberId) {
        Page<SubscriptionLike> subscriptionLikes = subscriptionLikeRepository.findSubscriptionBookmarkByMemberIdWithPaging_QueryDSL(pageable, memberId);
        List<SubscriptionLikeDTO> subscriptionLikeDTOS = subscriptionLikes.get().map(this::subscriptionLikeToDTO).collect(Collectors.toList());
        return new PageImpl<>(subscriptionLikeDTOS, pageable, subscriptionLikes.getTotalElements());
    }
}
