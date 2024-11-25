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
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDAOImpl implements AccountDAO {
    private static final Logger logger = Logger.getLogger(AccountDAOImpl.class.getName());

    // Inserting Bank account details and Creating Bank Account
    @Override
    public void createAccount(Account account) {
        final String query = "INSERT INTO account(account_number, name, address, pin, balance, account_type) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

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
            logger.info("Account Successfully Added. Account Number: " + accountNumber);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception at createAccount() Method: ", e);
            try {
                throw e; // Rethrow exception to parent class
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // Generating a Unique Account number
    public int generateAccountNumber() {
        return 1000000000 + new Random().nextInt(900000000);
    }

    // Checking if the generated account number is present in the table
    public boolean isAccountNumberUnique(final int accountNumber) throws SQLException {
        final String query = "SELECT 1 from account where account_number=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception at isAccountNumberUnique() Method: ", e);
            throw e; // Rethrow exception to parent class
        }
    }

    // Finding account, checking if the account is present in the table
    @Override
    public Optional<Account> findAccount(final int accountNumber) {
        final String query = "SELECT * from Account WHERE account_number=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

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
            logger.log(Level.SEVERE, "Exception at findAccount() Method: ", e);
            try {
                throw e; // Rethrow exception to parent class
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return Optional.empty();
    }

    // Updating bank balance when we do deposit and withdrawal operations
    @Override
    public void updateAccount(Account account) {
        final String query = "UPDATE account SET balance=? WHERE account_number=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setDouble(1, account.getBalance());
            stmt.setInt(2, account.getAccountNumber());
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception at updateAccount() Method: ", e);
            try {
                throw e; // Rethrow exception to parent class
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // Getting total accounts by type
    @Override
    public Map<String, Integer> getAccountSummaryByType() {
        final Map<String, Integer> summary = new HashMap<>();
        final String query = "SELECT account_type, COUNT(*) AS count from account GROUP By account_type";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                summary.put(rs.getString("account_type"),
                        rs.getInt("count"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception at getAccountSummaryByType() Method: ", e);
            try {
                throw e; // Rethrow exception to parent class
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return summary;
    }
}
