package com.kitm.darbas1.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBook implements Initializable {
    public TextField field_ISBN;
    public TextField field_Name;
    public TextField field_category;
    public TextField field_description;
    public TextField field_page_number;
    public TextField field_publis_date;
    public TextField field_price;
    public TextField field_Author;
    public Button create_Book_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
