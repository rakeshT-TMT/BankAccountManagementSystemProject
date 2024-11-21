package com.bank.models;

public class Account {

    private  int accountNumber;
    private String name;
    private String address;
    private int pin;
    private double balance;
    private String accountType;

    public Account(int accountNumber, String name, String address, int pin, double balance, String accountType) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.address = address;
        this.pin = pin;
        this.balance = balance;
        this.accountType = accountType;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", pin=" + pin +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
