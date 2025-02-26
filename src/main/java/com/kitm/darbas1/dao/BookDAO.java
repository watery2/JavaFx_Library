package com.kitm.darbas1.dao;

import com.kitm.darbas1.Models.Author;
import com.kitm.darbas1.Models.Book;
import com.kitm.darbas1.Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class BookDAO{

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BookDAO.class);
    private Connection conn;

    private static final Logger logger = Logger.getLogger(BookDAO.class.getName());

    public BookDAO(Connection conn)
    {
        this.conn = conn;
    }

    public Book findById(int id) {
        List<Book> books = findAll();

        for (Book i : books)
        {
            if (i.getId() == id)
            {
                return i;
            }
        }
        return null;
    }

    public void create(String ISBN, String Name, String Category, String Description, int PageNumber, String ReleaseDate, int Price, int AuthorID) {
        String sql = "INSERT INTO Books(ISBN, Name, Category, Description, PageNumber, ReleaseDate, Price, AuthorID, UserID, Taken) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int userID = Model.getInstance().getLoggedUserId();

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, ISBN);
            stmt.setString(2, Name);
            stmt.setString(3, Category);
            stmt.setString(4, Description);
            stmt.setInt(5, PageNumber);
            stmt.setString(6, ReleaseDate);
            stmt.setInt(7, Price);
            stmt.setInt(8, AuthorID);
            stmt.setInt(9, userID);
            // 0 = not taken,  1 = taken
            stmt.setInt(10, 0);

            stmt.executeUpdate();
        }catch (SQLException e)
        {
            logger.severe("Error adding book");
        }
    }

    public void update(Object entity) {
        if (!(entity instanceof Book))
        {
            throw new IllegalArgumentException("Excepted Book object");
        }

        Book book = (Book) entity;

        String sql = "UPDATE Books SET ISBN = ?, Name = ?, Category = ?, Description = ?, PageNumber = ?, ReleaseDate = ?, Price = ?, AuthorID = ?, Taken = ? WHERE id = ?";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, book.getISBN());
            stmt.setString(2, book.getName());
            stmt.setString(3, book.getCategory());
            stmt.setString(4, book.getDescription());
            stmt.setInt(5, book.getPageNumber());
            stmt.setString(6, book.getReleaseDate());
            stmt.setInt(7, book.getPrice());
            stmt.setInt(8, book.getAuthorID());
            stmt.setInt(9, book.getId());
            stmt.setInt(10, book.getTaken());

            stmt.executeUpdate();

        }catch (SQLException e)
        {
            logger.severe("Error updating book: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Books WHERE id = ?";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0){
                logger.info("Books with ID " + id + " was deleted successfully");
            }
            else {
                logger.warning("No book found with ID " + id);
            }
        } catch (SQLException e) {
            logger.severe("Error deleting book with ID: " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List all() {
        return List.of();
    }

    public ObservableList<Book> findAll() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        String sql = "SELECT id, ISBN, Name, Category, Description, PageNumber, ReleaseDate, Price, AuthorID, Taken FROM Books";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String ISBN = resultSet.getString("ISBN");
                String Name = resultSet.getString("Name");
                String Category = resultSet.getString("Category");
                String Description = resultSet.getString("Description");
                int PageNumber = resultSet.getInt("PageNumber");
                String ReleaseDate = resultSet.getString("ReleaseDate");
                int Price = resultSet.getInt("Price");
                int AuthorID = resultSet.getInt("AuthorID");
                int Taken = resultSet.getInt("Taken");

                Book book = new Book(id, ISBN, Name, Category, Description, PageNumber, ReleaseDate, Price, AuthorID, Taken);
                books.add(book);
            }
        }catch (SQLException e){
            logger.severe(e.getMessage());
        }

        return books;
    }

    public void setTakenStatus(int id, int status)
    {
        String sql = "UPDATE Books SET Taken = ? WHERE id = ?";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql))
        {
            stmt.setInt(1, status);
            stmt.setInt(2, id);

            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            logger.severe("Error seting taken status for book " + e);
        }
    }
}
