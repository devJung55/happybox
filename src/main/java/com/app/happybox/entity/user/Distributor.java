package com.app.happybox.entity.user;

import com.app.happybox.entity.product.Product;
import com.app.happybox.type.Role;
import com.app.happybox.type.UserStatus;
import com.sun.istack.NotNull;
import lombok.*;

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

    @Builder
    public Distributor(String userId, String userPassword, Address address, String userEmail, String userPhoneNumber, Role userRole, String distributorName) {
        super(userId, userPassword, address, userEmail, userPhoneNumber, userRole);
        this.distributorName = distributorName;
    }
}
