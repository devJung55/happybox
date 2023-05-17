package com.app.happybox.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class OrderInfoDTO {
    private String deliveryName;
    private String deliveryPhoneNumber;

    @Builder
    public OrderInfoDTO(String deliveryName, String deliveryPhoneNumber) {
        this.deliveryName = deliveryName;
        this.deliveryPhoneNumber = deliveryPhoneNumber;
    }
}
