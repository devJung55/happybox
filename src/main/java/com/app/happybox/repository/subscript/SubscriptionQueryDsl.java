package com.app.happybox.repository.subscript;

import com.app.happybox.domain.SubscriptionSearchDTO;
import com.app.happybox.entity.subscript.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SubscriptionQueryDsl {
    //    최신 Top8
    public List<Subscription> findTop8OrderByDate_QueryDSL();

    //    판매순 Top N 조회
    public List<Subscription> findTopNByOrderCountOrderByOrderCount_QueryDSL(Long limit);

    //    이번달 최신 Top3
    public List<Subscription> findTop3BetweenDateOrderByDateDesc_QueryDSL(LocalDateTime startDate, LocalDateTime endDate);

//    좋아요 Top3
    public List<Subscription> findTop3OrderByLikeCount();

    //    리뷰글 많은 순 조회 Top4
    public List<Subscription> findTopNOrderByReviewCount_QueryDSL(Long limit);

    //    구독 지역별 조회
    public Page<Subscription> findAllBySearchWithPaging_QueryDSL(Pageable pageable, SubscriptionSearchDTO subscriptionSearchDTO);

    //    구독 상세 조회
    public Optional<Subscription> findByIdWithDetail_QueryDSL(Long id);


}
