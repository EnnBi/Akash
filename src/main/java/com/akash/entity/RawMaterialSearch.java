package com.akash.entity;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class RawMaterialSearch {
    private Long materialTypeId;
    private Long appUserId;
    @DateTimeFormat(pattern="dd-MM-yyyy")
    LocalDate startDate;
    @DateTimeFormat(pattern="dd-MM-yyyy")
    LocalDate endDate;

    public Long getMaterialTypeId() {
        return this.materialTypeId;
    }

    public void setMaterialTypeId(Long materialTypeId) {
        this.materialTypeId = materialTypeId;
    }

    public Long getAppUserId() {
        return this.appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
