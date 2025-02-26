package com.kitm.darbas1.Controllers;

import com.kitm.darbas1.Models.Author;
import com.kitm.darbas1.Models.Book;
import com.kitm.darbas1.Models.BookAcc;
import com.kitm.darbas1.Models.Model;
import com.kitm.darbas1.Utilities.AlertUtility;
import com.kitm.darbas1.Utilities.DialogUtility;
import com.kitm.darbas1.Views.MenuItems;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookAccController implements Initializable {
    public TableColumn col_Id;
    public TableColumn col_ISBN;
    public TableColumn col_Name;
    public TableColumn col_TakeDate;
    public TableColumn col_BackDate;
    public MenuItem remove_BookAcc;
    public TextField filterISBN;
    public TextField filterName;
    public TextField filterTakeDate;
    public TextField filterBackDate;
    public Button filterButton;
    public TextField filterReader;
    public TableColumn col_Reader;
    public TableView Book_table;
    public Button GiveBook_btn;
    public TableColumn col_CBackDate;
    public TableColumn col_Status;
    public MenuItem GiveBack_BookAcc;
    public ChoiceBox filter_choice;

    private FilteredList<BookAcc> filteredAccounts;

    private String[] choices = {"Paimta", "Gražinta", "Veluoja", "Visos"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GiveBook_btn.setOnAction(event -> onGiveBook());

        filter_choice.getItems().addAll(choices);
        filter_choice.setValue("Visos");

        initTableColumns();
        loadBookAccData();

        GiveBack_BookAcc.setOnAction(event -> GiveBack());
        remove_BookAcc.setOnAction(event -> onRemoveAccount());

        filterButton.setOnAction(event -> applyFilters());
    }

    private void onGiveBook()
    {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.GIVE_BOOK);
    }

    private void initTableColumns(){
        col_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_ISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        col_Reader.setCellValueFactory(new PropertyValueFactory<>("reader"));
        col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_TakeDate.setCellValueFactory(new PropertyValueFactory<>("takenDate"));
        col_BackDate.setCellValueFactory(new PropertyValueFactory<>("giveBackDate"));
        col_CBackDate.setCellValueFactory(new PropertyValueFactory<>("givenBackDate"));
        col_Status.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    public void loadBookAccData()
    {
        ObservableList<BookAcc> bookAccs = Model.getInstance().findAllBookAccounts();

        filteredAccounts = new FilteredList<>(bookAccs, p -> true);
        Book_table.setItems(filteredAccounts);
    }

    public void GiveBack()
    {
        BookAcc selectedAccount = (BookAcc) Book_table.getSelectionModel().getSelectedItem();

        if (selectedAccount.getStatusInt() == 0)
        {
            AlertUtility.displayError("Knyga jau gražinta");
        }
        else
        {
            Optional<BookAcc> result = DialogUtility.showGiveBackBookDialog(selectedAccount);
            result.ifPresent(updateBookAccount ->{
                Model.getInstance().updateBookAccount(updateBookAccount);
                Model.getInstance().setBookTakenStatus(updateBookAccount.getBookID(), 0);
                loadBookAccData();
            });
        }
    }

    private void onRemoveAccount()
    {
        BookAcc selectedAccount = (BookAcc) Book_table.getSelectionModel().getSelectedItem();

        if (selectedAccount == null)
        {
            AlertUtility.displayError("Pasirinkite apskaitą");
        }

        boolean confirmed = AlertUtility.displayConfirmation("Ar tikrai norite pasalinit apskaitą");

        if (confirmed)
        {
            if (selectedAccount.getStatusInt() == 1)
            {
                Model.getInstance().setBookTakenStatus(selectedAccount.getBookID(), 0);
            }
            Model.getInstance().deleteBookAccount(selectedAccount.getId());

            loadBookAccData();
            AlertUtility.displayInformation("apskaitą pašalinta sėkmingai");
        }
    }

    private void applyFilters(){

        String filterChoice = filter_choice.getValue().toString();

        filteredAccounts.setPredicate(bookAcc->{

            if (filterChoice.equals("Visos") || filterChoice.isEmpty()) {
                return true;
            }
            if(!filterChoice.isEmpty() && !bookAcc.getStatus().contains(filterChoice)){
                return false;
            }


            return true;
        });
    }
}
