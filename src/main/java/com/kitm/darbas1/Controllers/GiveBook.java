package com.kitm.darbas1.Controllers;

import com.kitm.darbas1.Models.Book;
import com.kitm.darbas1.Models.Model;
import com.kitm.darbas1.Models.Reader;
import com.kitm.darbas1.Utilities.AlertUtility;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GiveBook  implements Initializable {
    public ChoiceBox<String> Books;
    public ChoiceBox<String> Readers;
    public Button create_Book_btn;
    public DatePicker datePicker;

    HashMap<String, Integer> books;
    HashMap<String, Integer> readers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getInfo();

        create_Book_btn.setOnAction(event -> onGiveBook());

    }

    private void getInfo()
    {
        ObservableList<Book> gotBooks = Model.getInstance().getBooks();
        ObservableList<Reader> gotReaders = Model.getInstance().getReaders();

        books = new HashMap<String, Integer>();
        readers = new HashMap<String, Integer>();

        for (Book i : gotBooks)
        {
            if (i.getTaken() == 0)
            {
                books.put(i.getName() + " " + i.getAuthorName(), i.getId());
            }
        }

        for (Reader i : gotReaders)
        {
            readers.put(i.getFirstName() + " " + i.getLastName() + " " + i.getEmail(), i.getId());
        }

        Books.getItems().clear();
        Readers.getItems().clear();

        Books.getItems().addAll(books.keySet());
        Readers.getItems().addAll(readers.keySet());
    }

    private void onGiveBook()
    {
        if (Books == null || Readers == null || datePicker == null)
        {
            AlertUtility.displayError("Neviskas įvesta");
        }
        else
        {
            String bookString = Books.getValue();
            String readersString = Readers.getValue();

            Model.getInstance().giveBook(books.get(bookString), readers.get(readersString), datePicker.getValue());

            emptyFields();

            AlertUtility.displayInformation("Knyga sekmingai duota");

        }
    }

    private void emptyFields()
    {

        getInfo();

        Books.setValue(null);
        Readers.setValue(null);
        datePicker.setValue(null);
    }
}
