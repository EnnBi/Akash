package com.akash.entity;

import com.akash.entity.AppUser;
import com.akash.util.CommonMethods;
import java.util.Objects;

public class BalanceSheet {
    AppUser user;
    Double balance;
    Double debit;
    Double credit;

    public AppUser getUser() {
        return this.user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Double getBalance() {
        return CommonMethods.format(this.balance);
    }

    public void setBalance(Double balance) {
        this.balance = Objects.isNull(balance) ? Double.valueOf(0.0) : balance;
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
}
