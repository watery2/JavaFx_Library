package com.kitm.darbas1.Controllers;

import com.kitm.darbas1.Models.Author;
import com.kitm.darbas1.Models.Book;
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
    public TextField filterISBN;
    public TextField filterAuthor;
    public TextField filterName;
    public Button filterButton;

    private FilteredList<Book> filteredBooks;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add_book_btn.setOnAction(event -> onAddBook());

        initTableColumns();
        loadBookData();
        setRowFactoryForAuthorsTable();

//        filteredBooks = new FilteredList<>(Model.getInstance().getBooks());
//        books_table.setItems(filteredBooks);
        filterButton.setOnAction(event-> applyFilters());

        remove.setOnAction(event -> onRemoverBook());
    }

    public void onAddBook()
    {
        if (Model.getInstance().getAuthors().size() > 0)
        {
            Model.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuItems.ADD_BOOK);
        }
        else {
            AlertUtility.displayError("Negalima pridėti knygos, kai nėra autorių");
        }
    }

    private void initTableColumns() {
        col_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_ISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("Category"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        col_page_number.setCellValueFactory(new PropertyValueFactory<>("PageNumber"));
        col_publish_date.setCellValueFactory(new PropertyValueFactory<>("ReleaseDate"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        col_Author.setCellValueFactory(new PropertyValueFactory<>("AuthorName"));
    }

    public void loadBookData(){
        ObservableList<Book> books = Model.getInstance().getBooks();

        filteredBooks = new FilteredList<>(books, p -> true);
        books_table.setItems(filteredBooks);
    }


    private void onRemoverBook(){
        Book selectedBook = (Book) books_table.getSelectionModel().getSelectedItem();

        if(selectedBook == null) {
            AlertUtility.displayError("Pasirinkite knygą");
        }

        boolean confirmed = AlertUtility.displayConfirmation("Ar tirkai norite pašalinti knygą?");

        if (confirmed){
            Model.getInstance().deleteBook(selectedBook.getId());
//            ObservableList<Book> books = books_table.getItems();
//            books.remove(selectedBook);
            loadBookData();
            AlertUtility.displayInformation("Knyga pasalinta sekmingai");
        }
    }

    private void setRowFactoryForAuthorsTable(){
        books_table.setRowFactory(tv ->{
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    Book selectedBook = row.getItem();
                    editBook(selectedBook);
                }
            });

            return row;
        });
    }

    private void editBook(Book book)
    {
        Optional<Book> result = DialogUtility.showEditBookDialog(book);
        result.ifPresent(updateBook -> {
            Model.getInstance().updateBook(updateBook);
            loadBookData();
        });
    }

    private void applyFilters(){
        String ISBNFilter = filterISBN.getText().toLowerCase();
        String AuthorFilter = filterAuthor.getText().toLowerCase();
        String NameFilter = filterName.getText().toLowerCase();

        filteredBooks.setPredicate(book->{
            if(!ISBNFilter.isEmpty() && !book.getISBN().toLowerCase().contains(ISBNFilter)){
                return false;
            }

            if(!AuthorFilter.isEmpty() && !book.getAuthorName().toLowerCase().contains(AuthorFilter)){
                return false;
            }

            if(!NameFilter.isEmpty() && !book.getName().toLowerCase().contains(NameFilter)){
                return false;
            }

            return true;
        });
    }
}
