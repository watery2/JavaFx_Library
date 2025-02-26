package com.kitm.darbas1.Controllers;

import com.kitm.darbas1.Models.Model;
import com.kitm.darbas1.Views.MenuItems;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    public Button logout_btn;
    public Text current_user_text;
    public Button authors_btn;
    public Button book_btn;
    public Button reader_btn;
    public Button BookAcc_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // show logged user name in menu
        current_user_text.setText(Model.getInstance().getLoggedUserName());

        //Add listeners to the menu buttons
        addListeners();
    }

    private void addListeners(){
        logout_btn.setOnAction(event -> onLogout());
        authors_btn.setOnAction(event -> onAuthors());
        book_btn.setOnAction(event -> onBooks());
        reader_btn.setOnAction(event -> onReader());
        BookAcc_btn.setOnAction(event -> onBookAcc());
    }

    /**
     * Handle authors window
     */

    public void onAuthors()
    {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.AUTHORS);
    }

    public void onBooks()
    {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.BOOKS);
    }

    public void onReader()
    {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.READERS);
    }

    public void onBookAcc()
    {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.BOOK_ACCOUNTING);
    }

    /**
     * Handle logout event
     */

    public void onLogout()
    {
        Stage stage = (Stage) logout_btn.getScene().getWindow();

        Model.getInstance().getViewFactory().closeStage(stage);

        Model.getInstance().getViewFactory().showLoginWindow();

        Model.getInstance().setLoginSuccessFlag(false);
    }
}
