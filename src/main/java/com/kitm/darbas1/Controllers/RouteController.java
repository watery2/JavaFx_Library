package com.kitm.darbas1.Controllers;

import com.kitm.darbas1.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class RouteController implements Initializable {

    public BorderPane parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().addListener((observable, oldValue, newVal)->{
            switch (newVal){
                case AUTHORS:
                    parent.setCenter(Model.getInstance().getViewFactory().getAuthorsView());
                    break;
                case CREATE_AUTHOR:
                    parent.setCenter(Model.getInstance().getViewFactory().getCreateAuthorView());
                    break;
                case BOOKS:
                    parent.setCenter(Model.getInstance().getViewFactory().getBookView());
                    break;
                case ADD_BOOK:
                    parent.setCenter(Model.getInstance().getViewFactory().getAddBookView());
                    break;
                case READERS:
                    parent.setCenter(Model.getInstance().getViewFactory().getReadersView());
                    break;
                case CREATE_READER:
                    parent.setCenter(Model.getInstance().getViewFactory().getCreateReaderView());
                    break;
                case BOOK_ACCOUNTING:
                    parent.setCenter(Model.getInstance().getViewFactory().getBookAccView());
                    break;
                case GIVE_BOOK:
                    parent.setCenter(Model.getInstance().getViewFactory().getGiveBookView());
                    break;
                default:
                    parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
            }
        });
    }
}
