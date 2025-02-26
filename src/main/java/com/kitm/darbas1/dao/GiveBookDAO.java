package com.kitm.darbas1.dao;

import com.kitm.darbas1.Controllers.GiveBook;
import com.kitm.darbas1.Models.BookAcc;
import com.kitm.darbas1.Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Logger;

public class GiveBookDAO {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GiveBookDAO.class);
    private Connection conn;

    private static final Logger logger = Logger.getLogger(GiveBookDAO.class.getName());

    public GiveBookDAO(Connection conn)
    {
        this.conn = conn;
    }

    public void create(int bookID, int readerID, LocalDate giveBackDate)
    {
        String sql = "INSERT INTO GivenBooks(BookID, ReaderID, TakenDate, GiveBackDate, GivenBackDate, Status, userID) VALUES(?, ?, ?, ?, ?, ?, ?)";
        int userId = Model.getInstance().getLoggedUserId();

        try(PreparedStatement stmt = this.conn.prepareStatement(sql))
        {
            stmt.setInt(1, bookID);
            stmt.setInt(2, readerID);
            stmt.setString(3, LocalDate.now().toString());
            stmt.setString(4, giveBackDate.toString());
            stmt.setString(5, "-");
            stmt.setInt(6, 1);
            stmt.setInt(7, userId);

            stmt.executeUpdate();

            Model.getInstance().setBookTakenStatus(bookID, 1);
        }
        catch (SQLException e)
        {
            logger.severe("Error creating GiveBook: " + e);
        }
    }

    public void update(BookAcc bookAcc) {
        String sql = "UPDATE GivenBooks SET GivenBackDate = ?, Status = ? WHERE id = ?";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql))
        {
            stmt.setString(1, bookAcc.getGivenBackDate());
            stmt.setInt(2, bookAcc.getStatusInt());
            stmt.setInt(3, bookAcc.getId());

            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            logger.severe("Failed to update Book account " + e);
        }

    }

    public ObservableList<BookAcc> findAll()
    {
        ObservableList<BookAcc> bookAccs = FXCollections.observableArrayList();
        String sql = "SELECT id, BookID, ReaderID, TakenDate, GiveBackDate, GivenBackDate, Status FROM GivenBooks";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql))
        {
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                int bookID = resultSet.getInt("BookID");
                int readerID = resultSet.getInt("ReaderID");
                String takenDate = resultSet.getString("TakenDate");
                String giveBackDate = resultSet.getString("GiveBackDate");
                String givenBackDate = resultSet.getString("GivenBackDate");
                int status = resultSet.getInt("Status");

                BookAcc bookAcc = new BookAcc(id, bookID, readerID, takenDate, giveBackDate, givenBackDate, status);

                bookAccs.add(bookAcc);
            }
        }
        catch (SQLException e)
        {
            logger.severe(e.getMessage());
        }

        return  bookAccs;
    }

    public void delete(int id)
    {
        String sql = "DELETE FROM GivenBooks WHERE id = ?";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            logger.severe("Failled to delete book account " + e);
        }
    }
}
