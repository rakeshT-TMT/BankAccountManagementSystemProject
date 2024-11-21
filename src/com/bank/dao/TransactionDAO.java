package com.bank.dao;

import com.bank.models.Account;
import com.bank.models.Transaction;

import java.util.Optional;

public interface TransactionDAO {
    void addTransaction(Transaction transaction);

    Optional<Transaction> findTransaction(int accountNumber);
}
