package com.bank.daoImpl;

import com.bank.dao.AccountDAO;
import com.bank.models.Account;
import com.bank.utils.DBConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class AccountDAOImpl implements AccountDAO {
    Connection connection;

    // Creating Bank Account
    @Override
    public void createAccount(Account account) {
        String query="INSERT INTO account(account_number, name, address, pin, balance, account_type) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            connection= DBConnection.getConnection();
            PreparedStatement stmt=connection.prepareStatement(query);
            int accountNumber=generateAccountNumber();
            while (isAccountNumberUnique(accountNumber)){
                accountNumber=generateAccountNumber();
            }

            stmt.setInt(1,accountNumber);
            stmt.setString(2, account.getName());
            stmt.setString(3, account.getAddress());
            stmt.setInt(4,account.getPin());
            stmt.setDouble(5, account.getBalance());
            stmt.setString(6,account.getAccountType());

            stmt.executeUpdate();
            System.out.println("Account Successfully Added. Account Number: "+accountNumber);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Generating a Unique Account number
    public int generateAccountNumber(){
        return 1000000000 + new Random().nextInt(900000000) ;
    }

    // Checking if the generated account number is present in the table
    public boolean isAccountNumberUnique(int accountNumber) {
        String query = "SELECT 1 from account where account_number=?";
        connection = DBConnection.getConnection();
        ResultSet rs;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, accountNumber);
            rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public Optional<Account> findAccount(int accountNumber) {
        String query="SELECT * from Account WHERE account_number=?";

        try {
            connection = DBConnection.getConnection();
            PreparedStatement stmt=connection.prepareStatement(query);
            stmt.setInt(1, accountNumber);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                Account account=new Account(
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
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean updateAccount(Account account) {
        String query="UPDATE account SET balance=? WHERE account_number=?";
        Connection connection=DBConnection.getConnection();
        try {
            PreparedStatement stmt=connection.prepareStatement(query);
            stmt.setDouble(1,account.getBalance());
            stmt.setInt(2,account.getAccountNumber());
            return stmt.executeUpdate()>0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Map<String, Long> gatAccountSummaryByType(){
        Map<String, Long> summary=new HashMap<>();
        String query="SELECT account_type, COUNT(*) AS count from account GROUP By account_type";
        Connection connection=DBConnection.getConnection();
        try {
            PreparedStatement stmt=connection.prepareStatement(query);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                summary.put(rs.getString("account_type"),
                        rs.getLong("count"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return summary;
    }

    public Map<String, Double> getTransactionSummary(){
        Map<String , Double> summary=new HashMap<>();
        String query="SELECT transaction_type, SUM(amount) as total FROM transaction GROUP BY transaction_type";
        try {
            PreparedStatement stmt=connection.prepareStatement(query);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                summary.put(rs.getString("transaction_type"),
                        rs.getDouble("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return summary;
    }

}
