package com.kitm.darbas1.dao;

import com.kitm.darbas1.Models.Author;
import com.kitm.darbas1.Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class AuthorsDAO implements GenericDAO{
    private Connection conn;

    private static final Logger logger = Logger.getLogger(AuthorsDAO.class.getName());

    public AuthorsDAO(Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public Object findById(int id) {
        return null;
    }

    @Override
    public void create(String fName, String lastName, String email, String city) {
        String sql = "INSERT INTO authors(firstName, lastName, email, city, date, userID) VALUES(?, ?, ?, ?, ?, ?)";

        int userId = Model.getInstance().getLoggedUserId();

        try(PreparedStatement stmt = this.conn.prepareStatement(sql))
        {
            stmt.setString(1, fName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, city);
            stmt.setDate(5, Date.valueOf(LocalDate.now()));
            stmt.setInt(6, userId);

            stmt.executeUpdate();

        }catch (SQLException e)
        {
            logger.severe("Error creating User: ");
        }
    }

    @Override
    public void update(Object entity) {

    }

    @Override
    public void delete(Object id) {

    }

    @Override
    public List all() {
        return List.of();
    }

    @Override
    public ObservableList<Author> findAll() {
        ObservableList<Author> authors = FXCollections.observableArrayList();
        String sql = "SELECT id, firstName, lastName, email, city, date FROM authors";

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

                Author author = new Author(id, firsName, lastName, email, city);
                authors.add(author);
            }

        }catch (SQLException e)
        {
            logger.severe(e.getMessage());
        }

        return authors;
    }
}
