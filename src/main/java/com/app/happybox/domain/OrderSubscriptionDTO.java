package com.app.happybox.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class OrderSubscriptionDTO {
    private Long memberId;  // 구독자 PK
    private String memberName;
    private LocalDateTime orderStartDate;

    @Builder
    public OrderSubscriptionDTO(Long memberId, String memberName, LocalDateTime orderStartDate) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.orderStartDate = orderStartDate;
    }
}
