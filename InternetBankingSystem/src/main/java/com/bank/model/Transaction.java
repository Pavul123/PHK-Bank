package com.bank.model;

import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private String accountNo;
    private String type;
    private double amount;
    private Timestamp date;
    private String description;

    public Transaction() {}

    public Transaction(String accountNo, String type, double amount, String description) {
        this.accountNo   = accountNo;
        this.type        = type;
        this.amount      = amount;
        this.description = description;
    }

    public int getTransactionId()                        { return transactionId; }
    public void setTransactionId(int transactionId)      { this.transactionId = transactionId; }

    public String getAccountNo()                         { return accountNo; }
    public void setAccountNo(String accountNo)           { this.accountNo = accountNo; }

    public String getType()                              { return type; }
    public void setType(String type)                     { this.type = type; }

    public double getAmount()                            { return amount; }
    public void setAmount(double amount)                 { this.amount = amount; }

    public Timestamp getDate()                           { return date; }
    public void setDate(Timestamp date)                  { this.date = date; }

    public String getDescription()                       { return description; }
    public void setDescription(String description)       { this.description = description; }
}
