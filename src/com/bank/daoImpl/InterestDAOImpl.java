package com.bank.daoImpl;

import com.bank.dao.InterestDAO;
import com.bank.models.Interest;
import com.bank.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InterestDAOImpl implements InterestDAO {
    @Override
    public void addInterest(Interest interest) {
        String query="INSERT INTO Interest(account_number, interest_amount, timestamp) VALUES(?, ?,?)";
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1,interest.getAccountNumber());
            stmt.setDouble(2,interest.getInterestAmount());
            stmt.setTimestamp(3, Timestamp.valueOf(interest.getTimestamp()));
            stmt.executeUpdate();
            System.out.println("Interest is Added.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Interest> getInterestByAccount(int accountNumber) {
        List<Interest> interests=new ArrayList<>();
        String query="SELECT * FROM INTEREST WHERE account_number=?";
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                interests.add(new Interest(
                        rs.getInt("account_number"),
                        rs.getDouble("interest_amount"),
                        rs.getTimestamp("timestamp").toLocalDateTime()
                ));
                return interests;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }
}
