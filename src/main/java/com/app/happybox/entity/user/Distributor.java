package com.app.happybox.entity.user;

import com.app.happybox.entity.product.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity @Table(name = "TBL_DISTRIBUTOR")
@Getter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Distributor extends User{

    /* 유통 상품 List */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "distributor", orphanRemoval = true)
    private List<Product> products;
}
