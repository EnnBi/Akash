package com.akash.entity;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class ManufactureSearch {
    Long productId;
    Long sizeId;
    @DateTimeFormat(pattern="dd-MM-yyyy")
    LocalDate startDate;
    @DateTimeFormat(pattern="dd-MM-yyyy")
    LocalDate endDate;
    Long labourId;

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSizeId() {
        return this.sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
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

    public Long getLabourId() {
        return this.labourId;
    }

    public void setLabourId(Long labourId) {
        this.labourId = labourId;
    }
}
