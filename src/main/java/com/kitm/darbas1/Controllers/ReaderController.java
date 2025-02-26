package com.kitm.darbas1.Controllers;

import com.kitm.darbas1.Models.Author;
import com.kitm.darbas1.Models.Model;
import com.kitm.darbas1.Models.Reader;
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

public class ReaderController implements Initializable {

    public Button add_reader_btn;
    public TableView reader_table;
    public TableColumn col_Id;
    public TableColumn col_firstName;
    public TableColumn col_lastName;
    public TableColumn col_email;
    public TableColumn col_city;
    public MenuItem remove_reader;
    public TextField filterFirstName;
    public TextField filterLastname;
    public TextField filterCity;
    public Button filterButton;

    private FilteredList<Reader> filteredReaders;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initTableColumns();
        loadAuthorData();

        add_reader_btn.setOnAction(event -> onReader());
        remove_reader.setOnAction(event -> onRemoverReader());

        setRowFactoryForReaderTable();

        filterButton.setOnAction(event -> applyFilters());
    }

    private  void onReader()
    {
        Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.CREATE_READER);
    }

    private void initTableColumns(){
        col_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_city.setCellValueFactory(new PropertyValueFactory<>("city"));
    }

    public void loadAuthorData(){
        ObservableList<Reader> readers = Model.getInstance().getReaders();

        filteredReaders = new FilteredList<>(readers, p -> true);
        reader_table.setItems(filteredReaders);
    }

    private void onRemoverReader()
    {
        Reader selectedReader = (Reader) reader_table.getSelectionModel().getSelectedItem();
        if(selectedReader == null){
            AlertUtility.displayError("Pasirinkite Skaitytoja");
        }

        boolean confirmed = AlertUtility.displayConfirmation(
                "Ar tikrai norite pasalinti skaitytoja ?"
        );

        if(confirmed){
            Model.getInstance().deleteReader(selectedReader.getId());
            loadAuthorData();
            AlertUtility.displayInformation("skaitytojas pasalintas sekmingai");
        }
    }

    private void setRowFactoryForReaderTable(){
        reader_table.setRowFactory(tv ->{
            TableRow<Reader> row = new TableRow<>();
            row.setOnMouseClicked(event ->{
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    Reader selectedReader = row.getItem();
                    editAuthor(selectedReader);
                }
            });

            return row;
        });
    }

    private void editAuthor(Reader reader){
        Optional<Reader> result = DialogUtility.showEditReaderDialog(reader);
        result.ifPresent(updateReader ->{
            Model.getInstance().updateReader(updateReader);
            loadAuthorData();
        });
    }

    private void applyFilters(){
        String firstNameFilter = filterFirstName.getText().toLowerCase();
        String lastNameFilter = filterLastname.getText().toLowerCase();
        String cityFilter = filterCity.getText().toLowerCase();

        filteredReaders.setPredicate(reader->{
            if(!firstNameFilter.isEmpty() && !reader.getFirstName().toLowerCase().contains(firstNameFilter)){
                return false;
            }

            if(!lastNameFilter.isEmpty() && !reader.getLastName().toLowerCase().contains(lastNameFilter)){
                return false;
            }

            if(!cityFilter.isEmpty() && !reader.getCity().toLowerCase().contains(cityFilter)){
                return false;
            }

            return true;
        });
    }
}

