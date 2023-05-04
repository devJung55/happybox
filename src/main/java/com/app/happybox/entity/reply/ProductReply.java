package com.app.happybox.entity.reply;

import com.app.happybox.entity.board.Board;
import com.app.happybox.entity.order.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@DiscriminatorValue("PRODUCT")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductReply extends Reply {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Product product;
}
