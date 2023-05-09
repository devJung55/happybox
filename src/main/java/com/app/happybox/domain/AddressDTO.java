package com.app.happybox.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class AddressDTO {
    private String zipcode;
    private String firstAddress;
    private String addressDetail;

    @Builder
    public AddressDTO(String zipcode, String firstAddress, String addressDetail) {
        this.zipcode = zipcode;
        this.firstAddress = firstAddress;
        this.addressDetail = addressDetail;
    }
}
