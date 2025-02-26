package com.kitm.darbas1.Controllers;

import com.kitm.darbas1.Models.Author;
import com.kitm.darbas1.Models.Model;
import com.kitm.darbas1.Utilities.AlertUtility;
import com.kitm.darbas1.Utilities.DialogUtility;
import com.kitm.darbas1.Views.MenuItems;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
//
//public class AuthorsController implements Initializable {
//
//    public Button add_author_btn;
//    public TableView authors_table;
//    public TableColumn col_ID;
//    public TableColumn col_firstName;
//    public TableColumn col_lastName;
//    public TableColumn col_email;
//    public TableColumn col_city;
//    public MenuItem remove;
//    public TextField filterFirstName;
//    public TextField filterLastName;
//    public TextField filterCity;
//    public Button filterButton;
//
//    private FilteredList<Author> filteredAuthors;
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        add_author_btn.setOnAction(event->onCreateAuthor());
//        initTableColumns();
//        remove.setOnAction(event -> onRemoveAuthor());
//        setRowFactoryFroAuthorsTable();
//
//        // Data filtering
//        filteredAuthors = new FilteredList<>(Model.getInstance().getAuthors(), p -> true);
//        authors_table.setItems(filteredAuthors);
//
//        filterButton.setOnAction(event -> applyFilters());
//
//    }
//
//    /**
//     * Open create author window
//     */
//
//    public void onCreateAuthor()
//    {
//        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.CREATE_AUTHOR);
//    }
//
//    /**
//     * Init the table columns with Author model
//     */
//
//    private void initTableColumns(){
//        col_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
//        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
//        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
//        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
//        col_city.setCellValueFactory(new PropertyValueFactory<>("city"));
//    }
//
//    /**
//     * Load authors data into table
//     */
//
//    public void loadAuthorData(){
//        ObservableList<Author> authors = Model.getInstance().getAuthors();
//
//        filteredAuthors = new FilteredList<>(authors, p -> true);
//        authors_table.setItems(filteredAuthors);
//    }
//
//    /**
//     * Handle author remove
//     */
//
//    private void onRemoveAuthor(){
//        Author selectedAuthor = (Author) authors_table.getSelectionModel().getSelectedItem();
//        if (selectedAuthor == null){
//            AlertUtility.displayError("Pasirinkite autorių");
//        }
//
//        boolean confirmed = AlertUtility.displayConfirmation("Ar tikrai norite pasalinit autoriu ?");
//
//        if (confirmed){
//           Model.getInstance().deleteAuthor(selectedAuthor.getId());
//           loadAuthorData();
//           authors_table.refresh();
//           AlertUtility.displayInformation("Autorius pasalintas sekmingai");
//        }
//    }
//
//    /**
//     * Sets row factory for the author table
//     */
//
//    private void setRowFactoryFroAuthorsTable()
//    {
//        authors_table.setRowFactory(tv -> {
//            TableRow<Author> row = new TableRow<>();
//            row.setOnMouseClicked(event -> {
//                if (event.getClickCount() == 2 && (!row.isEmpty()))
//                {
//                    Author selectedAuthor = row.getItem();
//                    editAuthor(selectedAuthor);
//                }
//            });
//
//            return row;
//        });
//    }
//
//    /**
//     * Opens dialog for editing author
//     * @param author
//     */
//
//    private void editAuthor(Author author){
//        Optional<Author> result = DialogUtility.showEditAuthorDialog(author);
//        result.ifPresent(updateAuthor -> {
//            Model.getInstance().updateAuthor(updateAuthor);
//            loadAuthorData();
//        });
//    }
//
//    /**
//     * Authors data filter
//     */
//
//    private void applyFilters(){
//        String firstNameFilter = filterFirstName.getText().toLowerCase();
//        String lastNameFilter = filterLastName.getText().toLowerCase();
//        String cityFilter = filterCity.getText().toLowerCase();
//
//        filteredAuthors.setPredicate(author -> {
//            if (!firstNameFilter.isEmpty() && !author.getFirstName().contains(firstNameFilter)){
//                return false;
//            }
//            if (!lastNameFilter.isEmpty() && !author.getLastName().contains(lastNameFilter)){
//                return false;
//            }
//            if(!cityFilter.isEmpty() && !author.getCity().toLowerCase().contains(cityFilter)){
//                return false;
//            }
//
//            return true;
//        });
//    }
//}


public class AuthorsController implements Initializable {
    public Button add_author_btn;
    public TableView authors_table;
    public TableColumn col_Id;
    public TableColumn col_firstName;
    public TableColumn col_lastName;
    public TableColumn col_email;
    public TableColumn col_city;
    public MenuItem remove_author;
    public TextField filterFirstName;
    public TextField filterLastname;
    public TextField filterCity;
    public Button filterButton;

    private FilteredList<Author> filteredAuthors;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        add_author_btn.setOnAction(event -> onCreateAuthor());
        initTableColumns();
        loadAuthorData();
        remove_author.setOnAction(event -> onRemoveAuthor() );
        setRowFactoryForAuthorsTable();

        //Data filtering
        filteredAuthors = new FilteredList<>(Model.getInstance().getAuthors());
        authors_table.setItems(filteredAuthors);
        filterButton.setOnAction(event-> applyFilters());
    }

    /**
     * Open create author window
     */

    public void onCreateAuthor(){
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.CREATE_AUTHOR);
    }

    /*
     * Init the table columns with Author model
     * */

    private void initTableColumns(){
        col_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_city.setCellValueFactory(new PropertyValueFactory<>("city"));
    }

    /**
     * Load authors data into table
     */

//    private void loadAuthorData(){
//        ObservableList<Author> authors = Model.getInstance().getAuthors();
//        authors_table.setItems(authors);
//    }

        public void loadAuthorData(){
        ObservableList<Author> authors = Model.getInstance().getAuthors();

        filteredAuthors = new FilteredList<>(authors, p -> true);
        authors_table.setItems(filteredAuthors);
    }

    /**
     * Handle author remove
     */

    private void onRemoveAuthor(){
        Author selectedAuthor = (Author) authors_table.getSelectionModel().getSelectedItem();
        if(selectedAuthor == null){
            AlertUtility.displayError("Pasirinkite autorių");
        }

        boolean confirmed = AlertUtility.displayConfirmation(
                "Ar tikrai norite pasalinti autoriu ?"
        );

        if(confirmed){
            Model.getInstance().deleteAuthor(selectedAuthor.getId());
//            ObservableList<Author> authors = authors_table.getItems();
//            authors.remove(selectedAuthor);
            loadAuthorData();
            AlertUtility.displayInformation("Autorius pasalintas sekmingai");
        }
    }

    /**
     * Sets row factory for the author table
     */

    private void setRowFactoryForAuthorsTable(){
        authors_table.setRowFactory(tv ->{
            TableRow<Author> row = new TableRow<>();
            row.setOnMouseClicked(event ->{
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    Author selectedAuthor = row.getItem();
                    editAuthor(selectedAuthor);
                }
            });

            return row;
        });
    }

    /**
     * Opens dialog for editing author
     * @param author
     */

    private void editAuthor(Author author){
        Optional<Author> result = DialogUtility.showEditAuthorDialog(author);
        result.ifPresent(updateAuthor ->{
            Model.getInstance().updateAuthor(updateAuthor);
            loadAuthorData();
        });
    }

    /**
     * Authors data filter
     */
    private void applyFilters(){
        String firstNameFilter = filterFirstName.getText().toLowerCase();
        String lastNameFilter = filterLastname.getText().toLowerCase();
        String cityFilter = filterCity.getText().toLowerCase();

        filteredAuthors.setPredicate(author->{
            if(!firstNameFilter.isEmpty() && !author.getFirstName().toLowerCase().contains(firstNameFilter)){
                return false;
            }

            if(!lastNameFilter.isEmpty() && !author.getLastName().toLowerCase().contains(lastNameFilter)){
                return false;
            }

            if(!cityFilter.isEmpty() && !author.getCity().toLowerCase().contains(cityFilter)){
                return false;
            }

            return true;
        });
    }



}
