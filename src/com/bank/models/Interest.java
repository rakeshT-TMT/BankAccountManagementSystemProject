package com.bank.models;

import java.time.LocalDateTime;

public class Interest {
    private int accountNumber;
    private double interestAmount;
    private LocalDateTime timestamp;

    public Interest(int accountNumber, double interestAmount, LocalDateTime timestamp) {
        this.accountNumber = accountNumber;
        this.interestAmount = interestAmount;
        this.timestamp = timestamp;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(double interestAmount) {
        this.interestAmount = interestAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Interest{" +
                "accountNumber=" + accountNumber +
                ", interestAmount=" + interestAmount +
                ", timestamp=" + timestamp +
                '}';
    }
}
