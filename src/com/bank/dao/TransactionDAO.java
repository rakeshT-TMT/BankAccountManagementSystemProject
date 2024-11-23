package com.bank.dao;

import com.bank.models.Transaction;

import java.util.Map;

public interface TransactionDAO {
    void addTransaction(Transaction transaction);

    Map<String, Integer> getTransactionSummary();

}
