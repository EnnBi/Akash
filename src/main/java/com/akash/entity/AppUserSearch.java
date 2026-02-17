package com.akash.entity;

public class AppUserSearch {
    private String name;
    private String contact;
    private String accountNumber;
    private String ledgerNumber;
    private Long userTypeId;
    private Long labourGroupId;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getLedgerNumber() {
        return this.ledgerNumber;
    }

    public void setLedgerNumber(String ledgerNumber) {
        this.ledgerNumber = ledgerNumber;
    }

    public Long getUserTypeId() {
        return this.userTypeId;
    }

    public void setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Long getLabourGroupId() {
        return this.labourGroupId;
    }

    public void setLabourGroupId(Long labourGroupId) {
        this.labourGroupId = labourGroupId;
    }
}
