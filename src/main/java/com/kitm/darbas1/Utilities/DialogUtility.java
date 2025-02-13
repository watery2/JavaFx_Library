package com.kitm.darbas1.Utilities;

import com.kitm.darbas1.Models.Author;
import com.kitm.darbas1.Models.Book;
import com.kitm.darbas1.Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DialogUtility {
    /**
     * Display dialog for editing Author information
     * @param author - the Author object for editing
     */

    public static Optional<Author> showEditAuthorDialog(Author author){
        Dialog<Author> dialog = new Dialog();
        dialog.setTitle("Redaguoti autorių");
        dialog.setHeaderText("Redaguokite pasirinkto autoriaus duomenis");

        ButtonType saveButtonType = new ButtonType("Issaugoti", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField firstNameField = new TextField(author.getFirstName());
        TextField lastNameField = new TextField(author.getLastName());
        TextField emailField = new TextField(author.getEmail());
        TextField cityField = new TextField(author.getCity());

        grid.add(new Label("Vardas:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Pavarde:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("El. pastas:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Miestas:"), 0, 3);
        grid.add(cityField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == saveButtonType){
                author.setFirstName(firstNameField.getText().trim());
                author.setLastName(lastNameField.getText().trim());
                author.setEmail(emailField.getText().trim());
                author.setCity(cityField.getText().trim());
            }

            return author;
        });

        return dialog.showAndWait();
    }

    public static Optional<Book> showEditBookDialog(Book book){
        Dialog<Book> dialog = new Dialog();
        dialog.setTitle("Redaguoti knygą");
        dialog.setHeaderText("Redaguokite pasirinkto autoriaus duomenis");

        ButtonType saveButtonType = new ButtonType("Issaugoti", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField ISBN = new TextField(book.getISBN());
        TextField Name = new TextField(book.getName());
        TextField Category = new TextField(book.getCategory());
        TextField Description = new TextField(book.getDescription());
        TextField PageNumber = new TextField(book.getPageNumber() + "");
        TextField ReleaseDate = new TextField(book.getReleaseDate());
        TextField Price = new TextField(book.getPrice() + "");

        List<Author> authors = Model.getInstance().getAuthors();

        List<String> list = new ArrayList<>();

        for (Author i : authors)
        {
            list.add(i.getFirstName() + " " + i.getLastName() + " " + i.getEmail());
        }

        ObservableList<String> observableList = FXCollections.observableArrayList(list);

        ChoiceBox AuthorField = new ChoiceBox<>(observableList);

        Author author = Model.getInstance().getAuthorsById(book.getAuthorID());

        AuthorField.setValue(author.getFirstName() + " " + author.getLastName() + " " + author.getEmail());

        grid.add(new Label("ISBN:"), 0, 0);
        grid.add(ISBN, 1, 0);
        grid.add(new Label("Pavadinimas:"), 0, 1);
        grid.add(Name, 1, 1);
        grid.add(new Label("Kategorija"), 0, 2);
        grid.add(Category, 1, 2);
        grid.add(new Label("Aprašymas:"), 0, 3);
        grid.add(Description, 1, 3);
        grid.add(new Label("Puslapių numeris:"), 0, 4);
        grid.add(PageNumber, 1, 4);
        grid.add(new Label("Išleidimo data"), 0, 5);
        grid.add(ReleaseDate, 1, 5);
        grid.add(new Label("Kaina"), 0, 6);
        grid.add(Price, 1, 6);
        grid.add(new Label("Autorius:"), 0, 7);
        grid.add(AuthorField, 1, 7);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == saveButtonType){

                if (ISBN.getText().isEmpty() || Name.getText().isEmpty() || Category.getText().isEmpty() ||
                        Description.getText().isEmpty() || ReleaseDate.getText().isEmpty() || PageNumber.getText().isEmpty() ||
                        Price.getText().isEmpty()) {

                    AlertUtility.displayError("Visi laukeliai turi būti užpildyti");
                }
                else {
                    book.setISBN(ISBN.getText().trim());
                    book.setName(Name.getText().trim());
                    book.setCategory(Category.getText().trim());
                    book.setDescription(Description.getText().trim());
                    book.setReleaseDate(ReleaseDate.getText().trim());
                    book.setAuthorID(Model.getInstance().findAuthor(AuthorField.getValue().toString()).getId());

                    try{
                        book.setPageNumber(Integer.parseInt(PageNumber.getText()));
                        book.setPrice(Integer.parseInt(Price.getText()));
                    }catch (Exception e)
                    {
                        AlertUtility.displayError("Reides įvestos į ten kur turi būti numeriai");
                    }
                }

            }

            return book;
        });

        return dialog.showAndWait();
    }
}
