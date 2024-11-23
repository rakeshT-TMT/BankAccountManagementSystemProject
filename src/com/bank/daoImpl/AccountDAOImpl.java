package com.bank.daoImpl;

import com.bank.dao.AccountDAO;
import com.bank.models.Account;
import com.bank.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class AccountDAOImpl implements AccountDAO {
    Connection connection;

    // Inserting Bank account details and Creating Bank Account
    @Override
    public void createAccount(Account account) {
        final String query = "INSERT INTO account(account_number, name, address, pin, balance, account_type) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            connection = DBConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            int accountNumber = generateAccountNumber();
            while (isAccountNumberUnique(accountNumber)) {
                accountNumber = generateAccountNumber();
            }

            stmt.setInt(1, accountNumber);
            stmt.setString(2, account.getName());
            stmt.setString(3, account.getAddress());
            stmt.setInt(4, account.getPin());
            stmt.setDouble(5, account.getBalance());
            stmt.setString(6, account.getAccountType());

            stmt.executeUpdate();
            System.out.println("Account Successfully Added. Account Number: " + accountNumber);

        } catch (SQLException e) {
            System.out.println("Exception at createAccount() Method : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Generating a Unique Account number
    public int generateAccountNumber() {
        return 1000000000 + new Random().nextInt(900000000);
    }

    // Checking if the generated account number is present in the table
    public boolean isAccountNumberUnique(int accountNumber) {
        final String query = "SELECT 1 from account where account_number=?";
        connection = DBConnection.getConnection();
        ResultSet rs;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, accountNumber);
            rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.out.println("Exception at isAccountNumberUnique() Method : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //Finding account , checking if the account is present in table

    public Optional<Account> findAccount(int accountNumber) {
        final String query = "SELECT * from Account WHERE account_number=?";

        try {
            connection = DBConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Account account = new Account(
                        rs.getInt("account_number"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getInt("pin"),
                        rs.getDouble("balance"),
                        rs.getString("account_type")
                );
                return Optional.of(account);
            }
        } catch (SQLException e) {
            System.out.println("Exception at findAccount() Method : " + e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    //Updating bank balance when we do deposit and withdrawal operations
    @Override
    public void updateAccount(Account account) {
        final String query = "UPDATE account SET balance=? WHERE account_number=?";
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDouble(1, account.getBalance());
            stmt.setInt(2, account.getAccountNumber());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Exception at updateAccount() Method : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //Getting total accounts by type
    public Map<String, Integer> getAccountSummaryByType() {
        Map<String, Integer> summary = new HashMap<>();
        final String query = "SELECT account_type, COUNT(*) AS count from account GROUP By account_type";
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                summary.put(rs.getString("account_type"),
                        rs.getInt("count"));
            }
        } catch (SQLException e) {
            System.out.println("Exception at getAccountSummaryByType() Method : " + e.getMessage());
            throw new RuntimeException(e);
        }
        return summary;
    }
}
