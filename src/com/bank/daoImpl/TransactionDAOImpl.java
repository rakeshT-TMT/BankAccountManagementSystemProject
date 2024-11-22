package com.bank.daoImpl;

import com.bank.dao.TransactionDAO;
import com.bank.models.Account;
import com.bank.models.Interest;
import com.bank.models.Transaction;
import com.bank.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TransactionDAOImpl implements TransactionDAO {


    @Override
    public void addTransaction(Transaction transaction) {
        String query="INSERT INTO Transaction(transaction_id, account_number, type, amount, timestamp) VALUES(?, ?, ?, ?,?)";
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);
            int transactionId=generateTransactionId();
            while (!isTransactionIdUnique(transactionId)){
                transactionId=generateTransactionId();
            }


            stmt.setInt(1,transaction.getTransactionId());
            stmt.setInt(2,transaction.getAccountNumber());
            stmt.setString(3,transaction.getType());
            stmt.setDouble(4,transaction.getAmount());
            stmt.setTimestamp(5, Timestamp.valueOf(transaction.getTimestamp()));
            stmt.executeUpdate();
            System.out.println("Transaction is Added.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Optional<Transaction> findTransaction(int accountNumber) {
        String query="SELECT * from transaction WHERE account_number=?";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement stmt=connection.prepareStatement(query);
            stmt.setInt(1, accountNumber);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                Transaction transaction=new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("account_number"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("timestamp").toLocalDateTime()

                );
                return Optional.of(transaction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }

    public int generateTransactionId(){
        return 10000 + new Random().nextInt(9000) ;
    }

    // Checking if the generated account number is present in the table
    public boolean isTransactionIdUnique(int transactionId) {
        String query = "SELECT 1 from transaction where transaction_id=?";
        Connection connection = DBConnection.getConnection();
        ResultSet rs;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, transactionId);
            rs = stmt.executeQuery();
            return !rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Interest> getTransacionByAccount(int accountNumber){
        List<Transaction> transactions=new ArrayList<>();
        String query="SELECT * FROM transaction WHERE account_number=?";
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                transactions.add(new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("account_number"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("timestamp").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }
}