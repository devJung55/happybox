package com.app.happybox.entity.subscript;

import com.app.happybox.entity.type.SubOption;
import com.app.happybox.entity.user.Address;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

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

    @QueryProjection
    public SubscriptionDTO(Long id, String subscriptionTitle, Integer subscriptionPrice, Integer subscriptLikeCount, Address welfareAddress,
                           String welfareName, Long reviewCount) {
        this.id = id;
        this.subscriptionTitle = subscriptionTitle;
        this.subscriptionPrice = subscriptionPrice;
        this.subscriptLikeCount = subscriptLikeCount;
        this.welfareAddress = welfareAddress;
        this.welfareName = welfareName;
        this.reviewCount = reviewCount;
    }

    @QueryProjection
    public SubscriptionDTO(Long id, String subscriptionTitle, Integer subscriptionPrice, Integer subscriptLikeCount, Address welfareAddress,
                           String welfareName, Long reviewCount, Double reviewAvgRating, Long orderCount) {
        this.id = id;
        this.subscriptionTitle = subscriptionTitle;
        this.subscriptionPrice = subscriptionPrice;
        this.subscriptLikeCount = subscriptLikeCount;
        this.welfareAddress = welfareAddress;
        this.welfareName = welfareName;
        this.reviewCount = reviewCount;
        this.reviewAvgRating = reviewAvgRating;
        this.orderCount = orderCount;
    }
}
