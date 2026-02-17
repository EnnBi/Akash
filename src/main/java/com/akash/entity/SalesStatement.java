package com.akash.entity;

public class SalesStatement {
    String product;
    Double quantity;
    Double unitPrice;
    Double amount;

    public SalesStatement(String product, String size, Double quantity, Double unitPrice, Double amount) {
        this.product = product + "-" + size;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }

    public String getProduct() {
        return this.product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String toString() {
        return this.product + "   " + this.quantity + "  " + this.unitPrice + "  " + this.amount + " /n";
    }
}
