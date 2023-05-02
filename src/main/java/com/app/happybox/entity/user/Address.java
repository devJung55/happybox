package com.app.happybox.entity.user;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    /* kakao 주소 api 에 따라 변경해야함 */
    /* 상세주소를 밖으로 빼야할수도? */
    private String zipcode;
    private String firstAddress;
    private String addressDetail;
}
