package com.app.happybox.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @ToString @Builder
public class PaymentDTO {
    private Long id;
    private Long paymentAmount;
    private String paymentStatus;
    private String userName;
    private LocalDateTime createdDate;
}
