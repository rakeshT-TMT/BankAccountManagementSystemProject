package com.bank.dao;

import com.bank.models.Account;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public interface AccountDAO {
    void createAccount(Account account) throws SQLException;

    Optional<Account> findAccount(int accountNumber) throws SQLException;

    void updateAccount(Account account) throws SQLException;

    Map<String, Integer> getAccountSummaryByType() throws SQLException;

}
