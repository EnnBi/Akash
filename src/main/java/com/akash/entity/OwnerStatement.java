package com.akash.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OwnerStatement {
    String user;
    String transactionNumber;
    String transactionType;
    String transactionBy;
    String accountNumber;
    String responsiblePerson;
    Double amount;
    LocalDate date;
    Double debit;
    Double credit;
    Double balance;

    public OwnerStatement(LocalDate date, String user, String transactionNumber, String transactionType, String transactionBy, String accountNumber, String responsiblePerson, Double amount) {
        this.date = date;
        this.user = user;
        this.transactionNumber = transactionNumber;
        this.transactionType = transactionType;
        this.transactionBy = transactionBy;
        this.accountNumber = accountNumber;
        this.responsiblePerson = responsiblePerson;
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

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTransactionNumber() {
        return this.transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionBy() {
        return this.transactionBy;
    }

    public void setTransactionBy(String transactionBy) {
        this.transactionBy = transactionBy;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getResponsiblePerson() {
        return this.responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
}
