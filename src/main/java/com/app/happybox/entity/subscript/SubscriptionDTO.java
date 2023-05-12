package com.app.happybox.entity.subscript;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.entity.user.Address;
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

    private Integer subscriptionPrice;

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

    @Builder
    public SubscriptionDTO(Long id, String subscriptionTitle, Integer subscriptionPrice,
                           Integer subscriptLikeCount, Long reviewCount, Double reviewAvgRating,
                           Long orderCount, String welfareName, AddressDTO welfareAddress) {
        this.id = id;
        this.subscriptionTitle = subscriptionTitle;
        this.subscriptionPrice = subscriptionPrice;
        this.subscriptLikeCount = subscriptLikeCount;
        this.reviewCount = reviewCount;
        this.reviewAvgRating = reviewAvgRating;
        this.orderCount = orderCount;
        this.welfareName = welfareName;
        this.address = welfareAddress;
    }

    public void setFoodList(List<FoodDTO> foodList) {
        this.foodList = foodList;
    }
}
