package com.kitm.darbas1.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
    private IntegerProperty id;
    private StringProperty ISBN;
    private StringProperty Name;
    private StringProperty Category;
    private StringProperty Description;
    private IntegerProperty PageNumber;
    private StringProperty ReleaseDate;
    private IntegerProperty Price;
    private IntegerProperty AuthorID;
    private StringProperty AuthorName;

    public Book(int id, String ISBN, String name, String category, String description, int pageNumber, String releaseDate, int price, int authorID) {
        this.id = new SimpleIntegerProperty(id);
        this.ISBN = new SimpleStringProperty(ISBN);
        this.Name = new SimpleStringProperty(name);
        this.Category = new SimpleStringProperty(category);
        this.Description = new SimpleStringProperty(description);
        this.PageNumber = new SimpleIntegerProperty(pageNumber);
        this.ReleaseDate = new SimpleStringProperty(releaseDate);
        this.Price = new SimpleIntegerProperty(price);
        this.AuthorID = new SimpleIntegerProperty(authorID);
        Author author = Model.getInstance().getAuthorsById(authorID);
        this.AuthorName = new SimpleStringProperty((author.getFirstName() + " " + author.getLastName()));
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
        return ISBN.get();
    }

    public StringProperty ISBNProperty() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN.set(ISBN);
    }

    public String getName() {
        return Name.get();
    }

    public StringProperty nameProperty() {
        return Name;
    }

    public void setName(String name) {
        this.Name.set(name);
    }

    public String getCategory() {
        return Category.get();
    }

    public StringProperty categoryProperty() {
        return Category;
    }

    public void setCategory(String category) {
        this.Category.set(category);
    }

    public String getDescription() {
        return Description.get();
    }

    public StringProperty descriptionProperty() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description.set(description);
    }

    public int getPageNumber() {
        return PageNumber.get();
    }

    public IntegerProperty pageNumberProperty() {
        return PageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.PageNumber.set(pageNumber);
    }

    public String getReleaseDate() {
        return ReleaseDate.get();
    }

    public StringProperty releaseDateProperty() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.ReleaseDate.set(releaseDate);
    }

    public int getPrice() {
        return Price.get();
    }

    public IntegerProperty priceProperty() {
        return Price;
    }

    public void setPrice(int price) {
        this.Price.set(price);
    }

    public int getAuthorID() {
        return AuthorID.get();
    }

    public IntegerProperty authorIDProperty() {
        return AuthorID;
    }

    public void setAuthorID(int authorID) {
        this.AuthorID.set(authorID);
    }

    public String getAuthorName() {
        return AuthorName.get();
    }

    public StringProperty authorNameProperty() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        this.AuthorName.set(authorName);
    }
}
