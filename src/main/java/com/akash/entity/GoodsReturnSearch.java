package com.akash.entity;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class GoodsReturnSearch {
    String receiptNumber;
    Long customerId;
    @DateTimeFormat(pattern="dd-MM-yyyy")
    LocalDate startDate;
    @DateTimeFormat(pattern="dd-MM-yyyy")
    LocalDate endDate;

    public String getReceiptNumber() {
        return this.receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
