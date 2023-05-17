package com.app.happybox.domain;

import com.app.happybox.entity.file.BoardFileDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter @ToString @Builder
public class SubscriptionLikeDTO {
    private Long id;
    private String welfareName;
    private Integer subscriptionPrice;
//    private List<BoardFileDTO> boardFiles;
}
