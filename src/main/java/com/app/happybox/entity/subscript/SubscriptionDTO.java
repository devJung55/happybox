package com.app.happybox.entity.subscript;

import com.app.happybox.entity.user.Address;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter @ToString
public class SubscriptionDTO {
    private Long id;

    /* ===== 구독 상품 정보 ===== */
    private String subscriptionTitle;

    private Integer subscriptionPrice;

    private Integer subscriptLikeCount;

    /* ======================= */

    //    복지관 명
    private String welfareName;

    //    복지관 주소
    private Address welfareAddress;

    //    리뷰수
    private Long reviewCount;

    //    리뷰 평균
    private Double reviewAvgRating;

    //    주문수
    private Long orderCount;

    //    파일 list
    private List<Food> foodList;

    @Builder
    public SubscriptionDTO(Long id, String subscriptionTitle, Integer subscriptionPrice, Integer subscriptLikeCount, String welfareName, Address welfareAddress, Long reviewCount, Double reviewAvgRating, Long orderCount) {
        this.id = id;
        this.subscriptionTitle = subscriptionTitle;
        this.subscriptionPrice = subscriptionPrice;
        this.subscriptLikeCount = subscriptLikeCount;
        this.welfareName = welfareName;
        this.welfareAddress = welfareAddress;
        this.reviewCount = reviewCount;
        this.reviewAvgRating = reviewAvgRating;
        this.orderCount = orderCount;
    }
}
