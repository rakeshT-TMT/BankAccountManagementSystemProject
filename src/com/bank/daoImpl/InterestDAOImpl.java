package com.bank.daoImpl;

import com.bank.dao.InterestDAO;
import com.bank.models.Interest;
import com.bank.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterestDAOImpl implements InterestDAO {

    private static final Logger logger = Logger.getLogger(InterestDAOImpl.class.getName());

    // Inserting interest details
    @Override
    public void addInterest(Interest interest) {
        final String query = "INSERT INTO Interest(account_number, interest_amount, timestamp) VALUES(?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, interest.getAccountNumber());
            stmt.setDouble(2, interest.getInterestAmount());
            stmt.setTimestamp(3, Timestamp.valueOf(interest.getTimestamp()));
            stmt.executeUpdate();

            System.out.println("Interest is Added.");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception at addInterest() Method: ", e);
            throw new DataAccessException("Failed to add interest to account: " + interest.getAccountNumber(), e);
        }
    }

    // Custom exception to handle database-related errors more specifically
    public static class DataAccessException extends RuntimeException {
        public DataAccessException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}



//Getting Interest of a specified account
//
//    @Override
//    public List<Interest> getInterestByAccount(int accountNumber) {
//        List<Interest> interests=new ArrayList<>();
//        String query="SELECT * FROM INTEREST WHERE account_number=?";
//        try (Connection connection = DBConnection.getConnection()) {
//            PreparedStatement stmt = connection.prepareStatement(query);
//
//            ResultSet rs=stmt.executeQuery();
//            while (rs.next()){
//                interests.add(new Interest(
//                        rs.getInt("account_number"),
//                        rs.getDouble("interest_amount"),
//                        rs.getTimestamp("timestamp").toLocalDateTime()
//                ));
//
//            }
//            return interests;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

