package com.kitm.darbas1.dao;

import com.kitm.darbas1.Utilities.UserUtility;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Locale;
import java.util.logging.Logger;

public class UserDAO {
    private Connection conn;

    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    /**
     * Initialize connection to DB
     */

    public UserDAO(Connection conn){
        this.conn = conn;
    }

    /**
     * Create new user in DB
     * @param userName string
     * @param password string
     * @param date date
     */

    public void createUser(String userName, String password, LocalDate date)
    {
        String sql = "INSERT INTO users (UserName, Password, Date) VALUES (?, ?, ?)";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, userName);
            stmt.setString(2, UserUtility.hashPassword(password));
            stmt.setDate(3, Date.valueOf(date));
            stmt.executeUpdate();
        }catch (SQLException e)
        {
            logger.severe("Error creating user: " + e.getMessage());
        }
    }


}
