package com.bank.service;

import com.bank.dao.AccountDAO;
import com.bank.dao.InterestDAO;
import com.bank.dao.TransactionDAO;
import com.bank.daoImpl.AccountDAOImpl;
import com.bank.daoImpl.InterestDAOImpl;
import com.bank.daoImpl.TransactionDAOImpl;
import com.bank.models.Account;
import com.bank.models.Interest;
import com.bank.models.Transaction;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;



public class BankService {

    public AccountDAO accountDAO=new AccountDAOImpl();
    public TransactionDAO transactionDAO=new TransactionDAOImpl();
    InterestDAO interestDAO= new InterestDAOImpl();

    // Adding Account to Database
//    public void addAccount(int accountNumber, String name, String address, int pin, double balance, String accountType){
//
//        accountDAO.createAccount(new Account(accountNumber, name, address, pin, balance, accountType));
//
//
//    }

    //Deposit money and updated the account balance
    public void depositMoney(int accountNumber, int pin, double amount){
        if (amount<=0){
            System.out.println("Amount should be positive");
            return;
        }
        Optional<Account> accountOpt=accountDAO.findAccount(accountNumber);
        if(accountOpt.isPresent()){
            Account account=accountOpt.get();
            if(account.getPin()==pin){
                account.setBalance(account.getBalance()+amount);
                accountDAO.updateAccount(account);
                System.out.println("Deposit successful. New Balance: "+account.getBalance());
                // Adding Transaction details into transaction table
                Transaction transaction=new Transaction(0,accountNumber,"Deposit", amount, LocalDateTime.now());

                transactionDAO.addTransaction(transaction);


            }else{
                System.out.println("Incorrect Pin");
            }
        }else{
            System.out.println("Account not found");
        }

    }
    //Withdrawal money and updated the account balance
    public void withdrawalMoney(int accountNumber, int pin, double amount){
        if (amount<=0){
            System.out.println("Amount should be positive");
            return;
        }
        Optional<Account> accountOpt=accountDAO.findAccount(accountNumber);
        if(accountOpt.isPresent()){
            Account account=accountOpt.get();

            if(account.getPin()==pin){
                if(account.getBalance()>amount){
                    account.setBalance(account.getBalance()-amount);
                    accountDAO.updateAccount(account);
                    System.out.println("Withdrawal successful. Remaining Balance: "+account.getBalance());
                    // Adding Transaction details into transaction table
                    Transaction transaction=new Transaction(0,accountNumber,"Withdraw", amount, LocalDateTime.now());
                    transactionDAO.addTransaction(transaction);

                }else {
                    System.out.println("Less Balance");
                }
            }else{
                System.out.println("Incorrect Pin");
            }
        }else{
            System.out.println("Account not found");
        }


    }
    // Checking Account balance
    public void balanceAmount(int accountNumber, int pin){

        Optional<Account> accountOpt=accountDAO.findAccount(accountNumber);
        if(accountOpt.isPresent()){
            Account account=accountOpt.get();
            if(account.getPin()==pin){
                System.out.println("Account Balance: "+account.getBalance());

            }else{
                System.out.println("Incorrect Pin");
            }
        }else{
            System.out.println("Account not found");
        }

    }

    //Calculating interest to the balance amount
    public void calculateInterest(int accountNumber, int pin){

        Optional<Account> accountOpt=accountDAO.findAccount(accountNumber);
        if(accountOpt.isPresent()){
            Account account=accountOpt.get();
            if(account.getPin()==pin){
                if(Objects.equals(account.getAccountType(), "Savings")){
                    double interestRate=0.04;
                    double interestAmount=account.getBalance()*interestRate;
                    System.out.println("Interest: "+interestAmount);
                    Interest interest=new Interest(accountNumber,interestAmount,LocalDateTime.now());

                    interestDAO.addInterest(interest);
                }else{
                    System.out.println("Not a Savings account");
                }

            }else{
                System.out.println("Incorrect Pin");
            }
        }else{
            System.out.println("Account not found");
        }

    }

    // View count of account types
    public void viewAccountSummary(){
        Map<String , Integer> accountSummary=accountDAO.gatAccountSummaryByType();
        accountSummary.forEach((type, count)-> System.out.println(type+" : "+count));

    }

    public void viewTransactionSummary(){
        Map<String, Integer> transactionSummary=transactionDAO.getTransactionSummary();
        transactionSummary.forEach((type, count)-> System.out.println(type+" : "+count));

    }
}
