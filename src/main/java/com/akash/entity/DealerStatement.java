package com.akash.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DealerStatement {
    LocalDate date;
    String chalanNumber;
    String product;
    Double quantity;
    String unit;
    Double amount;
    Double debit;
    Double credit;
    Double balance;
    String reference;
    String type;

    public DealerStatement(LocalDate date, String chalanNumber, String product, Double quantity, String unit, Double amount, String type) {
        this.date = date;
        this.chalanNumber = chalanNumber;
        this.product = product;
        this.quantity = quantity;
        this.unit = unit;
        this.amount = amount;
        this.type = type;
        this.debit = this.amount;
    }

    public DealerStatement(Double debit, String reference, LocalDate date, String transactionNumber, String type) {
        this.debit = debit;
        this.reference = reference + "-" + transactionNumber;
        this.date = date;
        this.type = type;
    }

    public DealerStatement(String reference, Double credit, LocalDate date, String transactionNumber, String type) {
        this.credit = credit;
        this.reference = reference + "-" + transactionNumber;
        this.date = date;
        this.type = type;
    }

    public String getDate() {
        return this.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getChalanNumber() {
        return this.chalanNumber;
    }

    public void setChalanNumber(String chalanNumber) {
        this.chalanNumber = chalanNumber;
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

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDebit() {
        return this.debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return this.credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getBalance() {
        return this.balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
