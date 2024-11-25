package com.bank.daoImpl;

import com.bank.dao.TransactionDAO;
import com.bank.models.Transaction;
import com.bank.utils.DBConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionDAOImpl implements TransactionDAO {

    private static final Logger logger = Logger.getLogger(TransactionDAOImpl.class.getName());

    // Inserting transaction details.
    @Override
    public void addTransaction(Transaction transaction) {
        final String query = "INSERT INTO Transaction(transaction_id, account_number, type, amount, timestamp) VALUES(?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, transaction.getTransactionId());
            stmt.setInt(2, transaction.getAccountNumber());
            stmt.setString(3, transaction.getType());
            stmt.setDouble(4, transaction.getAmount());
            stmt.setTimestamp(5, Timestamp.valueOf(transaction.getTimestamp()));
            stmt.executeUpdate();

            System.out.println("Transaction is Added.");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception at addTransaction() Method: ", e);
            try {
                throw e;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // Getting total deposits and withdrawals.
    @Override
    public Map<String, Integer> getTransactionSummary() {
        Map<String, Integer> summary = new HashMap<>();
        final String query = "SELECT type, COUNT(*) as total FROM transaction GROUP BY type";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                summary.put(rs.getString("type"), rs.getInt("total"));
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception at getTransactionSummary() Method: ", e);
            try {
                throw e;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        return summary;
    }

}