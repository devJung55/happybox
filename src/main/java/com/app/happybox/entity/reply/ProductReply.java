package com.app.happybox.entity.reply;

import com.app.happybox.entity.product.Product;
import com.app.happybox.entity.user.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity @Table(name = "TBL_PRODUCT_REPLY")
@DynamicInsert
@DiscriminatorValue("PRODUCT")
@Getter @ToString(callSuper = true, exclude = "product") @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductReply extends Reply {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Product product;

    @Builder
    public ProductReply(String replyContent, User user, Product product) {
        super(replyContent, user);
        this.product = product;
    }
}
