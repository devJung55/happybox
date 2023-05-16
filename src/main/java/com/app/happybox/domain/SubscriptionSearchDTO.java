package com.app.happybox.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class SubscriptionSearchDTO {
    private String searchFirstAddress;
    private String searchText;
}
