package com.kitm.darbas1.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDate;

public class BookAcc {

    private IntegerProperty id;
    private String ISBN;
    private String reader;
    private String name;

    private String takenDate;
    private String giveBackDate;
    private String givenBackDate;
    private String status;
    private IntegerProperty statusInt;
    private IntegerProperty bookID;
    private IntegerProperty readerID;

    public BookAcc(int id, int bookID, int readerID, String takenDate, String giveBackDate, String givenBackDate, int status)
    {
        this.id = new SimpleIntegerProperty(id);
        this.statusInt = new SimpleIntegerProperty(status);
        this.bookID = new SimpleIntegerProperty(bookID);
        this.readerID = new SimpleIntegerProperty(readerID);

        Book book = Model.getInstance().getBookByID(bookID);
        if (book != null)
        {
            this.ISBN = book.getISBN();
            this.name = book.getName();
        }
        else
        {
            this.ISBN = "-";
            this.name = "-";
        }

        Reader reader1 = Model.getInstance().getReaderById(readerID);

        if (reader1 != null)
        {
            this.reader = reader1.getFirstName() + " " + reader1.getLastName();
        }
        else
        {
            this.reader = "-";
        }


        this.takenDate = takenDate;
        this.giveBackDate = giveBackDate;
        this.givenBackDate = givenBackDate;

        if (status == 1)
        {
            if (LocalDate.now().isAfter(LocalDate.parse(giveBackDate)))
            {
                this.status = "Veluoja";
            }
            else
            {
                this.status = "Paimta";
            }
        }
        else
        {
            this.status = "Gražinta";
        }

    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(String takenDate) {
        this.takenDate = takenDate;
    }

    public String getGiveBackDate() {
        return giveBackDate;
    }

    public void setGiveBackDate(String giveBackDate) {
        this.giveBackDate = giveBackDate;
    }

    public String getGivenBackDate() {
        return givenBackDate;
    }

    public void setGivenBackDate(String givenBackDate) {
        this.givenBackDate = givenBackDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusInt() {
        return statusInt.get();
    }

    public IntegerProperty statusIntProperty() {
        return statusInt;
    }

    public void setStatusInt(int statusInt) {
        this.statusInt.set(statusInt);
    }

    public int getBookID() {
        return bookID.get();
    }

    public IntegerProperty bookIDProperty() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID.set(bookID);
    }

    public int getReaderID() {
        return readerID.get();
    }

    public IntegerProperty readerIDProperty() {
        return readerID;
    }

    public void setReaderID(int readerID) {
        this.readerID.set(readerID);
    }
}
