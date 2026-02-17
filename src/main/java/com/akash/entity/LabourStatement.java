package com.akash.entity;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.Transient;

public class LabourStatement {
    LocalDate date;
    String product;
    Double quantity;
    Double amount;
    Double loading;
    Double unloading;
    Double debit;
    Double credit;
    Double balance;
    String reference;
    String type;
    @Transient
    DecimalFormat df = new DecimalFormat("#.##");

    public LabourStatement(LocalDate date, String product, String size, Double quantity, Double amount, String type) {
        this.date = date;
        this.product = product + "-" + size;
        this.quantity = quantity;
        this.amount = amount;
        this.type = type;
        this.debit = this.amount;
    }

    public LabourStatement(LocalDate date, Double loading, Double unloading, String receiptNumber, String type) {
        this.date = date;
        this.loading = loading;
        this.unloading = unloading;
        this.type = type;
        this.reference = "BB-" + receiptNumber;
        this.calculateDebit();
    }

    public LabourStatement(LocalDate date, Double loading, String receiptNumber, String type) {
        this.date = date;
        this.loading = loading;
        this.unloading = 0.0;
        this.type = type;
        this.reference = "BB-" + receiptNumber;
        this.calculateDebit();
    }

    public LabourStatement(Double unloading, LocalDate date, String receiptNumber, String type) {
        this.date = date;
        this.loading = 0.0;
        this.unloading = unloading;
        this.type = type;
        this.reference = "BB-" + receiptNumber;
        this.calculateDebit();
    }

    public LabourStatement(Double debit, String reference, LocalDate date, String transactionNumber, String type) {
        this.debit = debit;
        this.reference = reference + "-" + transactionNumber;
        this.date = date;
        this.type = type;
    }

    public LabourStatement(String reference, Double credit, LocalDate date, String transactionNumber, String type) {
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

    public Double getAmount() {
        if (this.amount != null) {
            return Double.valueOf(this.df.format(this.amount));
        }
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getLoading() {
        if (this.loading != null) {
            return Double.valueOf(this.df.format(this.loading));
        }
        return this.loading;
    }

    public void setLoading(Double loading) {
        this.loading = loading;
    }

    public Double getUnloading() {
        if (this.unloading != null) {
            return Double.valueOf(this.df.format(this.unloading));
        }
        return this.unloading;
    }

    public void setUnloading(Double unloading) {
        this.unloading = unloading;
    }

    public Double getDebit() {
        if (this.debit != null) {
            return Double.valueOf(this.df.format(this.debit));
        }
        return this.debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        if (this.credit != null) {
            return Double.valueOf(this.df.format(this.credit));
        }
        return this.credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getBalance() {
        if (this.balance != null) {
            return Double.valueOf(this.df.format(this.balance));
        }
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

    void calculateDebit() {
        this.loading = this.loading == null ? 0.0 : this.loading;
        this.unloading = this.unloading == null ? 0.0 : this.unloading;
        this.debit = this.loading + this.unloading;
    }
}
