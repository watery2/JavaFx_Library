package com.kitm.darbas1.dao;

import com.kitm.darbas1.Models.Author;
import com.kitm.darbas1.Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class AuthorsDAO implements GenericDAO{
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AuthorsDAO.class);
    private Connection conn;

    private static final Logger logger = Logger.getLogger(AuthorsDAO.class.getName());

    public AuthorsDAO(Connection conn)
    {
        this.conn = conn;
    }

    @Override
    public Author findById(int id) {
        List<Author> authors = findAll();

        for (Author i : authors)
        {
            if (i.getId() == id)
            {
                return i;
            }
        }

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
        if (!(entity instanceof Author))
        {
            throw new IllegalArgumentException("Excepted Author object");
        }

        Author author = (Author) entity;

        String sql = "UPDATE authors SET firstName = ?, lastName = ?, email = ?, city = ? WHERE id = ?";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());
            stmt.setString(3, author.getEmail());
            stmt.setString(4, author.getCity());
            stmt.setInt(5, author.getId());

            int rowsUpdated = stmt.executeUpdate();

            if(rowsUpdated > 0){
                logger.info("Author updated: " + author);
            }
            else {
                logger.warning("No author found with id: " + author.getId());
            }
        }catch (SQLException e){
            logger.severe("Error updating author: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM Books WHERE AuthorID = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0){
                logger.info("Book with AuthorID " + id + " was deleted successfully");
            }
            else {
                logger.warning("No Book found with AuthorID " + id);
            }
        } catch (SQLException e) {
            logger.severe("Error deleting book with AuthorID: " + id + ": " + e.getMessage());
            e.printStackTrace();
        }

        sql = "DELETE FROM authors WHERE id = ?";
        try(PreparedStatement stmt2 = this.conn.prepareStatement(sql)){
            stmt2.setInt(1, id);
            int rowsAffected = stmt2.executeUpdate();
            if (rowsAffected > 0){
                logger.info("Author with ID " + id + " was deleted successfully");
            }
            else {
                logger.warning("No author found with ID " + id);
            }
        } catch (SQLException e) {
            logger.severe("Error deleting author with ID: " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
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
