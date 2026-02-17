package com.akash.entity.dto;

import java.time.LocalDate;

public class ManufactureDTO {
    long id;
    String product;
    String size;
    LocalDate date;
    Double quantity;
    Double amount;

    public ManufactureDTO(long id, String product, String size, LocalDate date, Double quantity, Double amount) {
        this.id = id;
        this.product = product;
        this.size = size;
        this.date = date;
        this.quantity = quantity;
        this.amount = amount;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProduct() {
        return this.product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
