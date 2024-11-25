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
            try {
                throw e;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
