package com.kitm.darbas1.dao;

import com.kitm.darbas1.Models.Author;
import com.kitm.darbas1.Models.Model;
import com.kitm.darbas1.Models.Reader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class ReaderDAO {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ReaderDAO.class);
    private Connection conn;

    private static final Logger logger = Logger.getLogger(ReaderDAO.class.getName());

    public ReaderDAO(Connection conn)
    {
        this.conn = conn;
    }

    public Reader getById(int id)
    {
        List<Reader> readers = findAll();

        for (Reader i : readers)
        {
            if (i.getId() == id)
            {
                return i;
            }
        }

        return null;
    }

    public void create(String fName, String lastName, String email, String city) {
        String sql = "INSERT INTO Reader(FirstName, LastName, Email, City, UserID) VALUES(?, ?, ?, ?, ?)";

        int userId = Model.getInstance().getLoggedUserId();

        try(PreparedStatement stmt = this.conn.prepareStatement(sql))
        {
            stmt.setString(1, fName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, city);
            stmt.setInt(5, userId);

            stmt.executeUpdate();

        }catch (SQLException e)
        {
            logger.severe("Error creating User: ");
        }
    }

    public ObservableList<Reader> findAll() {
        ObservableList<Reader> readers = FXCollections.observableArrayList();
        String sql = "SELECT id, FirstName, LastName, Email, City FROM Reader";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql))
        {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String firsName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String city = resultSet.getString("city");

                Reader reader = new Reader(id, firsName, lastName, email, city);
                readers.add(reader);
            }

        }catch (SQLException e)
        {
            logger.severe(e.getMessage());
        }

        return readers;
    }

    public void delete(int id)
    {
        String sql = "DELETE FROM Reader WHERE id = ?";
        try(PreparedStatement stmt2 = this.conn.prepareStatement(sql)){
            stmt2.setInt(1, id);
            int rowsAffected = stmt2.executeUpdate();
            if (rowsAffected > 0){
                logger.info("Reader with ID " + id + " was deleted successfully");
            }
            else {
                logger.warning("No reader found with ID " + id);
            }
        } catch (SQLException e) {
            logger.severe("Error deleting reader with ID: " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(Object entity) {

        if (!(entity instanceof Reader))
        {
            throw new IllegalArgumentException("Excepted Reader object");
        }

        Reader reader = (Reader) entity;

        String sql = "UPDATE Reader SET FirstName = ?, LastName = ?, Email = ?, City = ? WHERE id = ?";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, reader.getFirstName());
            stmt.setString(2, reader.getLastName());
            stmt.setString(3, reader.getEmail());
            stmt.setString(4, reader.getCity());
            stmt.setInt(5, reader.getId());

            int rowsUpdated = stmt.executeUpdate();

            if(rowsUpdated > 0){
                logger.info("Reader updated: " + reader);
            }
            else {
                logger.warning("No author found with id: " + reader.getId());
            }
        }catch (SQLException e){
            logger.severe("Error updating reader: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
