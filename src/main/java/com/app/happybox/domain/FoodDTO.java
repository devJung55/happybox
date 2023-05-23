package com.app.happybox.domain;

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

    private Long foodCalendarId;

    @Builder
    public FoodDTO(Long id, String foodName, String filePath, String fileUuid, String fileOrgName, Long foodCalendarId) {
        this.id = id;
        this.foodName = foodName;
        this.filePath = filePath;
        this.fileUuid = fileUuid;
        this.fileOrgName = fileOrgName;
        this.foodCalendarId = foodCalendarId;
    }

    public void setFoodCalendarId(Long foodCalendarId) {
        this.foodCalendarId = foodCalendarId;
    }
}
