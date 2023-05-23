package com.app.happybox.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString @NoArgsConstructor
public class DeleteIdsDTO {
    private List<Long> ids = new ArrayList<>();
}
