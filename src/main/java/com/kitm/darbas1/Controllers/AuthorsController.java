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

public class AuthorsController implements Initializable {

    public Button add_author_btn;
    public TableView authors_table;
    public TableColumn col_ID;
    public TableColumn col_firstName;
    public TableColumn col_lastName;
    public TableColumn col_email;
    public TableColumn col_city;
    public MenuItem remove;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add_author_btn.setOnAction(event->onCreateAuthor());
    }

    /**
     * Open create author window
     */

    public void onCreateAuthor()
    {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.CREATE_AUTHOR);
    }
}
