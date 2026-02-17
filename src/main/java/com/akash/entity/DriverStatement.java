package com.akash.entity;

import com.akash.util.CommonMethods;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.Transient;

public class DriverStatement {
    String receiptNumber;
    String customerName;
    String site;
    Double carraige;
    Double loading;
    Double unloading;
    Double total;
    Double debit;
    Double credit;
    Double balance;
    String reference;
    LocalDate date;
    String type;
    @Transient
    DecimalFormat df = new DecimalFormat("#.##");

    public DriverStatement(Double debit, String reference, LocalDate date, String transactionNumber, String type) {
        this.debit = debit;
        this.reference = reference + "-" + transactionNumber;
        this.date = date;
        this.type = type;
    }

    public DriverStatement(String reference, Double credit, LocalDate date, String transactionNumber, String type) {
        this.credit = credit;
        this.reference = reference + "-" + transactionNumber;
        this.date = date;
        this.type = type;
    }

    public DriverStatement(LocalDate date, String receiptNumber, String customerName, String site, Double carraige, Double loading, Double unloading, String type) {
        this.date = date;
        this.receiptNumber = receiptNumber;
        this.customerName = customerName;
        this.site = site;
        this.carraige = carraige;
        this.loading = loading;
        this.unloading = unloading;
        this.type = type;
        this.calculateDebit();
    }

    public String getDate() {
        return this.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public String getSite() {
        return this.site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getCarraige() {
        return this.carraige;
    }

    public void setCarraige(Double carraige) {
        this.carraige = carraige;
        this.calculateDebit();
    }

    public Double getLoading() {
        return this.loading;
    }

    public void setLoading(Double loading) {
        this.loading = loading;
        this.calculateDebit();
    }

    public Double getUnloading() {
        return this.unloading;
    }

    public void setUnloading(Double unloading) {
        this.unloading = unloading;
        this.calculateDebit();
    }

    public Double getDebit() {
        return CommonMethods.format(this.debit);
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return CommonMethods.format(this.credit);
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getBalance() {
        return CommonMethods.format(this.balance);
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
        this.debit = this.carraige + this.loading + this.unloading;
    }

    public String toString() {
        return "DriverStatement [date=" + this.date + "]";
    }
}
