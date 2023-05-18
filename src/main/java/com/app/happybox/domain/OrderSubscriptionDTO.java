package com.app.happybox.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class OrderSubscriptionDTO {
    private Long id;
    private Long memberId;  // PK
    private Long welfareId;  // PK
    private String memberName;
    private String welfareName;
    private String subscriptionTitle;
    private String subscriptionContent;
    private LocalDateTime orderStartDate;
}
