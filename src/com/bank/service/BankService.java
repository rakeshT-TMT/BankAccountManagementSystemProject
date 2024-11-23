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

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankService {

    private static final Logger logger = Logger.getLogger(BankService.class.getName());

    public AccountDAO accountDAO = new AccountDAOImpl();
    public TransactionDAO transactionDAO = new TransactionDAOImpl();
    public InterestDAO interestDAO = new InterestDAOImpl();

    // Deposit money and update the account balance
    public void depositMoney(final int accountNumber, final int pin, final double amount) {
        if (amount <= 0) {
            logger.warning("Amount should be positive");
            return;
        }

        Optional<Account> accountOpt;
        try {
            accountOpt = accountDAO.findAccount(accountNumber);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error finding account during deposit", e);
            throw new RuntimeException(e); // Rethrow exception to parent
        }

        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            if (account.getPin() == pin) {
                account.setBalance(account.getBalance() + amount);
                try {
                    accountDAO.updateAccount(account);
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, "Error updating account during deposit", e);
                    throw new RuntimeException(e); // Rethrow exception to parent
                }
                logger.info("Deposit successful. New Balance: " + account.getBalance());

                // Adding Transaction details into the transaction table
                Transaction transaction = new Transaction(0, accountNumber, "Deposit", amount, LocalDateTime.now());
                transactionDAO.addTransaction(transaction);
            } else {
                logger.warning("Incorrect Pin during deposit");
            }
        } else {
            logger.warning("Account not found during deposit");
        }
    }

    // Withdrawal money and update the account balance
    public void withdrawalMoney(final int accountNumber, final int pin, final double amount) {
        if (amount <= 0) {
            logger.warning("Amount should be positive");
            return;
        }

        Optional<Account> accountOpt;
        try {
            accountOpt = accountDAO.findAccount(accountNumber);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error finding account during withdrawal", e);
            throw new RuntimeException(e); // Rethrow exception to parent
        }

        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();

            if (account.getPin() == pin) {
                if (account.getBalance() >= amount) {
                    account.setBalance(account.getBalance() - amount);
                    try {
                        accountDAO.updateAccount(account);
                    } catch (SQLException e) {
                        logger.log(Level.SEVERE, "Error updating account during withdrawal", e);
                        throw new RuntimeException(e); // Rethrow exception to parent
                    }
                    logger.info("Withdrawal successful. Remaining Balance: " + account.getBalance());

                    // Adding Transaction details into the transaction table
                    Transaction transaction = new Transaction(0, accountNumber, "Withdraw", amount, LocalDateTime.now());
                    transactionDAO.addTransaction(transaction);

                } else {
                    logger.warning("Insufficient Balance for withdrawal");
                }
            } else {
                logger.warning("Incorrect Pin during withdrawal");
            }
        } else {
            logger.warning("Account not found during withdrawal");
        }
    }

    // Checking Account balance
    public void balanceAmount(final int accountNumber, final int pin) {
        Optional<Account> accountOpt;
        try {
            accountOpt = accountDAO.findAccount(accountNumber);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error finding account during balance check", e);
            throw new RuntimeException(e); // Rethrow exception to parent
        }

        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            if (account.getPin() == pin) {
                logger.info("Account Balance: " + account.getBalance());
            } else {
                logger.warning("Incorrect Pin during balance check");
            }
        } else {
            logger.warning("Account not found during balance check");
        }
    }

    // Calculating interest to the balance amount
    public void calculateInterest(final int accountNumber, final int pin) {
        Optional<Account> accountOpt;
        try {
            accountOpt = accountDAO.findAccount(accountNumber);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error finding account during interest calculation", e);
            throw new RuntimeException(e); // Rethrow exception to parent
        }

        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            if (account.getPin() == pin) {
                if (Objects.equals(account.getAccountType(), "Savings")) {
                    double interestRate = 0.04;
                    double interestAmount = account.getBalance() * interestRate;
                    logger.info("Interest: " + interestAmount);

                    // Adding interest into the Interest table
                    Interest interest = new Interest(accountNumber, interestAmount, LocalDateTime.now());
                    interestDAO.addInterest(interest);
                } else {
                    logger.warning("Account type is not 'Savings' during interest calculation");
                }
            } else {
                logger.warning("Incorrect Pin during interest calculation");
            }
        } else {
            logger.warning("Account not found during interest calculation");
        }
    }

    // View count of account types
    public void viewAccountSummary() {
        Map<String, Integer> accountSummary;
        try {
            accountSummary = accountDAO.getAccountSummaryByType();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error getting account summary", e);
            throw new RuntimeException(e); // Rethrow exception to parent
        }
        accountSummary.forEach((type, count) -> logger.info(type + " : " + count));
    }

    // View count of withdrawals and deposits
    public void viewTransactionSummary() {
        Map<String, Integer> transactionSummary = transactionDAO.getTransactionSummary();
        transactionSummary.forEach((type, count) -> logger.info(type + " : " + count));
    }
}
