package com.bank.dao;

import com.bank.models.Account;
import com.bank.models.Transaction;

import java.util.Map;
import java.util.Optional;

public interface TransactionDAO {
    void addTransaction(Transaction transaction);
    Map<String, Integer> getTransactionSummary();

}
