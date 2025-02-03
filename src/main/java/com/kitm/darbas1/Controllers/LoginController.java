package com.kitm.darbas1.Controllers;

import com.kitm.darbas1.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public TextField user_name_field;
    @FXML
    public PasswordField password_field;
    @FXML
    public Hyperlink register_link;
    @FXML
    public Button login_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        register_link.setOnAction(actionEvent -> onRegister());
        login_btn.setOnAction(actionEvent -> onLogin());
    }

    /*
    * Handle login action
    */

    public void onLogin(){
        Stage stage = (Stage) register_link.getScene().getWindow();
        Model.getInstance().getViewFactory().showMainWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
    }

    /*
    Handle register action
     */

    public void onRegister(){
        Stage stage = (Stage) register_link.getScene().getWindow();

        Model.getInstance().getViewFactory().showRegisterWindow();

        Model.getInstance().getViewFactory().closeStage(stage);
    }


}
