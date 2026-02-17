package com.akash.entity.dto;

import java.time.LocalDate;

public class BillBookDTO {
    long id;
    String receiptNumber;
    String customerName;
    LocalDate date;
    String vehicle;
    String site;
    Double total;

    public BillBookDTO(long id, String receiptNumber, String customerName, String address, LocalDate date, String site, Double total) {
        this.id = id;
        this.receiptNumber = receiptNumber;
        this.customerName = customerName + "-" + address;
        this.date = date;
        this.site = site;
        this.total = total;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReceiptNumber() {
        return this.receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getSite() {
        return this.site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
