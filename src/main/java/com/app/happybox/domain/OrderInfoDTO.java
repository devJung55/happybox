package com.app.happybox.domain;

import com.app.happybox.entity.user.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @ToString
public class OrderInfoDTO {
    private Long id;
    private String deliveryName;
    private String deliveryPhoneNumber;
    private List<Long>cartIds = new ArrayList<>();
    private AddressDTO addressDTO;

    @Builder
    public OrderInfoDTO(String deliveryName, String deliveryPhoneNumber, List<Long> cartIds, AddressDTO addressDTO) {
        this.deliveryName = deliveryName;
        this.deliveryPhoneNumber = deliveryPhoneNumber;
        this.cartIds = cartIds;
        this.addressDTO = addressDTO;
    }
}
