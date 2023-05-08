package com.app.happybox.repository.subscript;

import com.app.happybox.entity.subscript.Food;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.subscript.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriptionQueryDsl {
//    최신 Top8
    public List<Subscription> findTop8OrderByDate_QueryDSL();

//    이번달 최신 Top3
    public List<Subscription> findTop3BetweenDateOrderByDateDesc_QueryDSL(LocalDateTime startDate, LocalDateTime endDate);

//    리뷰글 많은 순 조회 Top4
    public List<Subscription> findTop4OrderByReviewCount_QueryDSL();

//    구독 지역별 조회
    public Page<Subscription> findAllByAddressCategoryWithPaging_QueryDSL(Pageable pageable, String address);

//    구독 상세 조회
    public Subscription findByIdWithDetail_QueryDSL(Long id);
}
