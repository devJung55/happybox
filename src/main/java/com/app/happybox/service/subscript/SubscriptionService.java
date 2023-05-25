package com.app.happybox.service.subscript;

import com.app.happybox.domain.AddressDTO;
import com.app.happybox.domain.FoodDTO;
import com.app.happybox.domain.SubscriptionDTO;
import com.app.happybox.domain.SubscriptionSearchDTO;
import com.app.happybox.domain.user.SubscriptionWelFareDTO;
import com.app.happybox.entity.subscript.*;
import com.app.happybox.entity.user.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriptionService {

    //    최신 8개 조회
    public List<SubscriptionDTO> findRecentTop8();

    //    주문 많은순 N개 조회
    public List<SubscriptionDTO> findByOrderCount(Long limit);

    //    리뷰 많은순 N개 조회
    public List<SubscriptionDTO> findByReviews(Long limit);

    //    좋아요 많은순 3개 조회
    public List<SubscriptionDTO> findTop3ByLikeCount();

    //    검색 조회
    public Page<SubscriptionDTO> findBySearch(Pageable pageable, SubscriptionSearchDTO searchDTO);

    //    상세 조회
    public SubscriptionDTO findByIdWithDetail(Long id);

//    달(month)로 조회
    public List<SubscriptionDTO> findAllBetweenDate(LocalDateTime dateTime);

    //    구독했는지 확인하기 위해 조회
    public Integer existsByMemberIdAndSubscriptionId(Long memberId, Long welfareId);

    //    subscription id로 조회
    public Boolean existsById(Long id);

    // subscription 수정
    public void updateByDTO(SubscriptionWelFareDTO subscriptionWelFareDTO);

    public SubscriptionWelFareDTO getSubscriptionWelfareDTO(Long id);

    public Subscription getId(Long id);




    default SubscriptionDTO subscriptionToDTO(Subscription subscription, List<FoodDTO> foodList) {
        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
                .id(subscription.getId())
                .orderCount(subscription.getOrderCount())
                .reviewAvgRating(subscription.getReviewAvgRating())
                .reviewCount(subscription.getReviewCount())
                .subscriptionPrice(subscription.getSubscriptionPrice())
                .subscriptionTitle(subscription.getSubscriptionTitle())
                .subscriptionContent(subscription.getSubscriptionContent())
                .subscriptLikeCount(subscription.getSubscriptLikeCount())
                .welfareAddress(addressToDTO(subscription.getWelfare().getAddress()))
                .welfareName(subscription.getWelfare().getWelfareName())
                .build();
        subscriptionDTO.setFoodList(foodList);
        return subscriptionDTO;
    }

    default SubscriptionWelFareDTO subscriptionToWelfareDTO(Subscription subscription){
        return SubscriptionWelFareDTO.builder()
                .id(subscription.getId())
                .subscriptionContent(subscription.getSubscriptionContent())
                .subscriptionPrice(subscription.getSubscriptionPrice())
                .subscriptionTitle(subscription.getSubscriptionTitle())
                .build();
    }

    default SubscriptionDTO subscriptionToDTO(Subscription subscription, FoodDTO foodDTO) {
        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
                .id(subscription.getId())
                .orderCount(subscription.getOrderCount())
                .reviewAvgRating(subscription.getReviewAvgRating())
                .reviewCount(subscription.getReviewCount())
                .subscriptionPrice(subscription.getSubscriptionPrice())
                .subscriptionTitle(subscription.getSubscriptionTitle())
                .subscriptionContent(subscription.getSubscriptionContent())
                .subscriptLikeCount(subscription.getSubscriptLikeCount())
                .welfareAddress(addressToDTO(subscription.getWelfare().getAddress()))
                .welfareName(subscription.getWelfare().getWelfareName())
                .build();
        subscriptionDTO.setRepresentFood(foodDTO);
        return subscriptionDTO;
    }

    default SubscriptionDTO subscriptionToDTO(Subscription subscription) {
        return SubscriptionDTO.builder()
                .id(subscription.getId())
                .orderCount(subscription.getOrderCount())
                .reviewAvgRating(subscription.getReviewAvgRating())
                .reviewCount(subscription.getReviewCount())
                .subscriptionPrice(subscription.getSubscriptionPrice())
                .subscriptionTitle(subscription.getSubscriptionTitle())
                .subscriptionContent(subscription.getSubscriptionContent())
                .welfareId(subscription.getWelfare().getId())
                .subscriptLikeCount(subscription.getSubscriptLikeCount())
                .welfareAddress(addressToDTO(subscription.getWelfare().getAddress()))
                .welfareName(subscription.getWelfare().getWelfareName())
                .build();
    }

    default FoodDTO foodToDTO(Food food) {
        return FoodDTO.builder()
                .id(food.getId())
                .fileOrgName(food.getFileOrgName())
                .filePath(food.getFilePath())
                .fileUuid(food.getFileUuid())
                .foodName(food.getFoodName())
                .build();
    }

    default AddressDTO addressToDTO(Address address) {
        return AddressDTO.builder()
                .firstAddress(address.getFirstAddress())
                .addressDetail(address.getAddressDetail())
                .zipcode(address.getZipcode())
                .build();
    }
}
