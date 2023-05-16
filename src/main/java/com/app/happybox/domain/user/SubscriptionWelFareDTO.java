package com.app.happybox.domain.user;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@Getter
@NoArgsConstructor
public class SubscriptionWelFareDTO {

    private Long id;
    private String subscriptionTitle;
    private Integer subscriptionPrice;
    private String subscriptionContent;

    @Builder
    public SubscriptionWelFareDTO(Long id, String subscriptionTitle, Integer subscriptionPrice, String subscriptionContent) {
        this.id = id;
        this.subscriptionTitle = subscriptionTitle;
        this.subscriptionPrice = subscriptionPrice;
        this.subscriptionContent = subscriptionContent;
    }
}
