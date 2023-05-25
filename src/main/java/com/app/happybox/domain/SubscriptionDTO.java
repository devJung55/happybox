package com.app.happybox.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @ToString
public class SubscriptionDTO {

    /* ===== 구독 상품 정보 ===== */
    private Long id;

    private String subscriptionTitle;

    private String subscriptionContent;

    private Integer subscriptionPrice;

    private Long welfareId;

    /* ----- 반정규화 ----- */

    // 구독 좋아요 갯수 (조회가 많기 때문에 컬럼으로 추가했음)
    private Integer subscriptLikeCount;

    //    리뷰수
    private Long reviewCount;

    //    리뷰 평균
    private Double reviewAvgRating;

    //    주문수
    private Long orderCount;

    /* ======================= */

    //    복지관 명
    private String welfareName;

    //    복지관 주소
    private AddressDTO address;

    //    파일 list
    private List<FoodDTO> foodList = new ArrayList<>();

    //    대표음식
    private FoodDTO representFood;

    @Builder
    public SubscriptionDTO(Long id, String subscriptionTitle, String subscriptionContent, Integer subscriptionPrice,
                           Integer subscriptLikeCount, Long reviewCount, Double reviewAvgRating,
                           Long orderCount, String welfareName, AddressDTO welfareAddress, Long welfareId) {
        this.id = id;
        this.subscriptionTitle = subscriptionTitle;
        this.subscriptionContent = subscriptionContent;
        this.subscriptionPrice = subscriptionPrice;
        this.subscriptLikeCount = subscriptLikeCount;
        this.reviewCount = reviewCount;
        this.reviewAvgRating = reviewAvgRating;
        this.orderCount = orderCount;
        this.welfareName = welfareName;
        this.address = welfareAddress;
        this.welfareId = welfareId;
    }

    public void setFoodList(List<FoodDTO> foodList) {
        this.foodList = foodList;
    }

    public void setRepresentFood(FoodDTO representFood) {
        this.representFood = representFood;
    }
}
