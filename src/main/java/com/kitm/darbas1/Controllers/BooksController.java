package com.kitm.darbas1.Controllers;

import com.kitm.darbas1.Models.Model;
import com.kitm.darbas1.Views.MenuItems;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class BooksController implements Initializable {
    public Button add_book_btn;
    public TableView books_table;
    public TableColumn col_ID;
    public TableColumn col_ISBN;
    public TableColumn col_title;
    public TableColumn col_category;
    public TableColumn col_description;
    public TableColumn col_page_number;
    public TableColumn col_publish_date;
    public TableColumn col_price;
    public TableColumn col_Author;
    public MenuItem remove;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add_book_btn.setOnAction(event -> onAddBook());
    }

    public void onAddBook()
    {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.ADD_BOOK);
    }
}
