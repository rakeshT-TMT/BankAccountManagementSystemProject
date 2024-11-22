package com.bank.daoImpl;

import com.bank.dao.TransactionDAO;

import com.bank.models.Transaction;
import com.bank.utils.DBConnection;

import java.sql.*;
import java.util.*;

public class TransactionDAOImpl implements TransactionDAO {

    //Inserting transaction details.
    @Override
    public void addTransaction(Transaction transaction) {
        final String query="INSERT INTO Transaction(transaction_id, account_number, type, amount, timestamp) VALUES(?, ?, ?, ?,?)";
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);


            stmt.setInt(1,transaction.getTransactionId());
            stmt.setInt(2,transaction.getAccountNumber());
            stmt.setString(3,transaction.getType());
            stmt.setDouble(4,transaction.getAmount());
            stmt.setTimestamp(5, Timestamp.valueOf(transaction.getTimestamp()));
            stmt.executeUpdate();
            System.out.println("Transaction is Added.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Getting total deposits and withdrawals.

    public Map<String, Integer> getTransactionSummary(){
        Map<String , Integer> summary=new HashMap<>();
        final String query="SELECT type, COUNT(*) as total FROM transaction GROUP BY type";
        Connection connection=DBConnection.getConnection();
        try {
            PreparedStatement stmt=connection.prepareStatement(query);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                summary.put(rs.getString("type"),
                        rs.getInt("total"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return summary;
    }




























//    public List<Interest> getTransactionByAccount(int accountNumber){
//        List<Transaction> transactions=new ArrayList<>();
//        String query="SELECT * FROM transaction WHERE account_number=?";
//        try (Connection connection = DBConnection.getConnection()) {
//            PreparedStatement stmt = connection.prepareStatement(query);
//
//            ResultSet rs=stmt.executeQuery();
//            while (rs.next()){
//                transactions.add(new Transaction(
//                        rs.getInt("transaction_id"),
//                        rs.getInt("account_number"),
//                        rs.getString("type"),
//                        rs.getDouble("amount"),
//                        rs.getTimestamp("timestamp").toLocalDateTime()
//                ));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return List.of();
//    }
}