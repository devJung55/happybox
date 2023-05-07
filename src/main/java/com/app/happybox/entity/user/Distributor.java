package com.app.happybox.entity.user;

import com.app.happybox.entity.order.Product;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TBL_DISTRIBUTOR")
@DiscriminatorValue("DISTRIBUTOR")
@Getter @ToString(callSuper = true, exclude = "products") @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Distributor extends User{

    @NotNull @Column(unique = true)
    private String distributorName;

    /* 유통 상품 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "distributor", orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public Distributor(String userId, String userPassword, Address address, String userEmail, String userPhoneNumber, String distributorName) {
        super(userId, userPassword, address, userEmail, userPhoneNumber);
        this.distributorName = distributorName;
    }
}
