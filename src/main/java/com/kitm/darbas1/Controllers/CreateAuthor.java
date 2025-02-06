package com.kitm.darbas1.Controllers;

import com.kitm.darbas1.Models.Model;
import com.kitm.darbas1.Utilities.AlertUtility;
import com.kitm.darbas1.Views.MenuItems;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateAuthor implements Initializable {

    public TextField field_firstName;
    public TextField field_lastName;
    public TextField field_email;
    public TextField field_city;
    public Button create_author_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_author_btn.setOnAction(even -> onAuthor());
    }


    private void onAuthor()
    {
        String fName = field_firstName.getText();
        String lName = field_lastName.getText();
        String email = field_email.getText();
        String city = field_city.getText();

        // Create the author

        Model.getInstance().createAuthor(fName, lName, email, city);

        AlertUtility.displayInformation("Autorius sekmingai sukurtas");

        emptyFields();
    }

    private void emptyFields()
    {
        field_firstName.setText("");
        field_lastName.setText("");
        field_email.setText("");
        field_city.setText("");
    }

}
