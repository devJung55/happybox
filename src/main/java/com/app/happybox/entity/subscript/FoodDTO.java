package com.app.happybox.entity.subscript;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class FoodDTO {
    private Long id;

    private String foodName;

    private String filePath;

    private String fileUuid;

    private String fileOrgName;

    @Builder
    public FoodDTO(Long id, String foodName, String filePath, String fileUuid, String fileOrgName) {
        this.id = id;
        this.foodName = foodName;
        this.filePath = filePath;
        this.fileUuid = fileUuid;
        this.fileOrgName = fileOrgName;
    }
}
