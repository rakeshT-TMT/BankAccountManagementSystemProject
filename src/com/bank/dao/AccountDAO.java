package com.bank.dao;

import com.bank.models.Account;

import java.util.Map;
import java.util.Optional;

public interface AccountDAO {
    void createAccount(Account account);
    Optional<Account> findAccount(int accountNumber);
    boolean updateAccount(Account account);

    Map<String, Long> gatAccountSummaryByType();

    Map<String, Double> getTransactionSummary();
}
