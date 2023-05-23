package com.app.happybox.service.order;

import com.app.happybox.domain.OrderSubscriptionDTO;
import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.entity.order.OrderSubscription;
import com.app.happybox.entity.user.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface OrderSubsciptionService {
//    관리자 복지관 구독자 목록
    public Page<MemberDTO> getListByWelfareId(Pageable pageable, Long welfare, String subscriberName);

//    일반 마이페이지 구독한 수 조회
    public Long getMySubscriptionCountByMemberId(Long id);

//    마이페이지 구독 상세보기
    public OrderSubscriptionDTO getSubscriptionDetailByMemberId(Long memberId);

//    마이페이지 구독취소
    public void cancelSubscribeById(Long id);

//    마이페이지 구독자 수 조회
    public Long getSubscriberCountByWelfareId(Long welfareId);

    default OrderSubscriptionDTO adminToOrderSubscriptionDTO(OrderSubscription orderSubscription) {
        return OrderSubscriptionDTO.builder()
                .memberId(orderSubscription.getMember().getId())
                .memberName(orderSubscription.getMember().getMemberName())
                .orderStartDate(orderSubscription.getCreatedDate())
                .build();
    }

    default OrderSubscriptionDTO mypageToOrderSubscriptionDTO(OrderSubscription orderSubscription) {
        return OrderSubscriptionDTO.builder()
                .id(orderSubscription.getId())
                .welfareId(orderSubscription.getSubscription().getWelfare().getId())
                .welfareName(orderSubscription.getSubscription().getWelfare().getWelfareName())
                .subscriptionTitle(orderSubscription.getSubscription().getSubscriptionTitle())
                .subscriptionContent(orderSubscription.getSubscription().getSubscriptionContent())
                .orderStartDate(orderSubscription.getCreatedDate())
                .build();
    }

    default MemberDTO toMemberDTO(Member member){
        return MemberDTO.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .address(member.getAddress())
                .memberDeliveryAddress(member.getMemberDeliveryAddress())
                .memberBirth(member.getMemberBirth())
                .userEmail(member.getUserEmail())
                .memberGender(member.getMemberGender())
                .memberName(member.getMemberName())
                .userPassword(member.getUserPassword())
                .userPhoneNumber(member.getUserPhoneNumber())
                .deliveryName(member.getDeliveryName())
                .deliveryPhoneNumber(member.getDeliveryPhoneNumber())
                .userRole(member.getUserRole())
                .userStatus(member.getUserStatus())
                .build();
    }

}
