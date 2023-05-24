package com.app.happybox.domain;

import com.app.happybox.domain.user.UserFileDTO;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.file.UserFile;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter @ToString @Builder
public class SubscriptionLikeDTO {
    private Long id;
    private Long welfareId; // PK
    private Long subscriptionId; // PK
    private String welfareName;
    private Integer subscriptionPrice;
    private UserFileDTO userFileDTO;

    public void setUserFileDTO(UserFileDTO userFileDTO) {
        this.userFileDTO = userFileDTO;
    }
}
