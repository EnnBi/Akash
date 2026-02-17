package com.akash.entity;

import com.akash.entity.SalesStatement;
import com.akash.entity.Vehicle;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CustomerStatement {
    long id;
    String receiptNumber;
    LocalDate date;
    String vehicleNo;
    String address;
    Double loading;
    Double unloading;
    Double carraige;
    Double debit;
    Double amount;
    Double credit;
    Double balance;
    String reference;
    List<SalesStatement> sales;
    String type;
    Double discount;
    DecimalFormat df = new DecimalFormat("#.##");

    public CustomerStatement(long id, String receiptNumber, LocalDate date, Vehicle vehicle, String vehicleNo, String address, String site, Double loading, Double unloading, Double carraige, Double discount, Double credit) {
        this.id = id;
        this.receiptNumber = receiptNumber;
        this.date = date;
        this.vehicleNo = vehicle != null ? vehicle.getNumber() : vehicleNo;
        this.loading = loading;
        this.unloading = unloading;
        this.carraige = carraige;
        this.type = "BillBook";
        this.discount = discount;
        this.credit = credit;
        this.address = site != null && !"".equals(site) ? site : address;
        this.calculatetotal();
    }

    private void calculatetotal() {
        this.loading = this.loading == null ? Double.valueOf(0.0) : this.loading;
        this.unloading = this.unloading == null ? Double.valueOf(0.0) : this.unloading;
        this.carraige = this.carraige == null ? Double.valueOf(0.0) : this.carraige;
        this.credit = this.credit == null ? Double.valueOf(0.0) : this.credit;
        this.discount = this.discount == null ? Double.valueOf(0.0) : this.discount;
        this.amount = this.credit - (this.carraige + this.loading + this.unloading - this.discount);
    }

    public CustomerStatement(Double debit, String reference, LocalDate date, String transactionNumber) {
        this.date = date;
        this.debit = debit;
        this.reference = reference + "-" + transactionNumber;
        this.type = "DayBook";
    }

    public CustomerStatement(String reference, Double credit, LocalDate date, String transactionNumber) {
        this.date = date;
        this.credit = credit;
        this.reference = reference + "-" + transactionNumber;
        this.type = "DayBook";
    }

    public CustomerStatement(Double debit, LocalDate date, String ownerName) {
        this.date = date;
        this.debit = debit;
        this.reference = "DUES-" + ownerName;
        this.type = "Dues";
    }

    public CustomerStatement(LocalDate date, Double debit, String recieptNumber) {
        this.date = date;
        this.debit = debit;
        this.reference = "RG-" + recieptNumber;
        this.type = "RG";
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

    public String getDate() {
        return this.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public LocalDate getLocalDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getVehicleNo() {
        return this.vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLoading() {
        return this.loading;
    }

    public void setLoading(Double loading) {
        this.loading = loading;
    }

    public Double getUnloading() {
        return this.unloading;
    }

    public void setUnloading(Double unloading) {
        this.unloading = unloading;
    }

    public Double getCarraige() {
        return this.carraige;
    }

    public void setCarraige(Double carraige) {
        this.carraige = carraige;
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
        if (this.balance != null) {
            this.balance = Double.valueOf(this.df.format(this.balance));
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

    public List<SalesStatement> getSales() {
        return this.sales;
    }

    public void setSales(List<SalesStatement> sales) {
        this.sales = sales;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
