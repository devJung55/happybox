package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.subscript.SubscriptionDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SubscriptionQueryDsl {
//    이번달 최신 Top3
    public List<SubscriptionDTO> findTop3BetweenDateOrderByDateDesc_QueryDSL(LocalDateTime startDate, LocalDateTime endDate);

//    리뷰글 많은 순 조회 Top4
    public List<SubscriptionDTO> findTop4ByReviewCountOrderByReviewCount_QueryDSL();
}
