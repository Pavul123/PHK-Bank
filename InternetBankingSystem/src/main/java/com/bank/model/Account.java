package com.bank.model;

public class Account {
    private String accountNo;
    private int customerId;
    private String accountType;
    private double balance;

    public Account() {}

    public Account(String accountNo, int customerId, String accountType, double balance) {
        this.accountNo   = accountNo;
        this.customerId  = customerId;
        this.accountType = accountType;
        this.balance     = balance;
    }

    public String getAccountNo()                     { return accountNo; }
    public void setAccountNo(String accountNo)       { this.accountNo = accountNo; }

    public int getCustomerId()                       { return customerId; }
    public void setCustomerId(int customerId)        { this.customerId = customerId; }

    public String getAccountType()                   { return accountType; }
    public void setAccountType(String accountType)   { this.accountType = accountType; }

    public double getBalance()                       { return balance; }
    public void setBalance(double balance)           { this.balance = balance; }

    @Override
    public String toString() {
        return "Account{no=" + accountNo + ", type=" + accountType + ", balance=" + balance + "}";
    }
}
