package com.app.happybox.entity.subscript;

import com.app.happybox.entity.type.SubOption;
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

    private SubOption subOption;
    /* ======================= */

    //    복지관 명
    private String welfareName;

    //    리뷰수
    private Long reviewCount;

    @QueryProjection
    public SubscriptionDTO(Long id, String subscriptionTitle, Integer subscriptionPrice, Integer subscriptLikeCount, SubOption subOption, String welfareName) {
        this.id = id;
        this.subscriptionTitle = subscriptionTitle;
        this.subscriptionPrice = subscriptionPrice;
        this.subscriptLikeCount = subscriptLikeCount;
        this.subOption = subOption;
        this.welfareName = welfareName;
    }

    @QueryProjection
    public SubscriptionDTO(Long id, String subscriptionTitle, Integer subscriptionPrice, Integer subscriptLikeCount, SubOption subOption, String welfareName, Long reviewCount) {
        this.id = id;
        this.subscriptionTitle = subscriptionTitle;
        this.subscriptionPrice = subscriptionPrice;
        this.subscriptLikeCount = subscriptLikeCount;
        this.subOption = subOption;
        this.welfareName = welfareName;
        this.reviewCount = reviewCount;
    }
}
