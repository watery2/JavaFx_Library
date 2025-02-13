package com.kitm.darbas1.Controllers;

import com.kitm.darbas1.Models.Author;
import com.kitm.darbas1.Models.Model;
import com.kitm.darbas1.Utilities.AlertUtility;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddBook implements Initializable {
    public TextField field_ISBN;
    public TextField field_Name;
    public TextField field_category;
    public TextField field_description;
    public TextField field_page_number;
    public TextField field_publis_date;
    public TextField field_price;
    public Button create_Book_btn;
    public ChoiceBox<String> Autoriai;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setChoiceBoxOptions();
        create_Book_btn.setOnAction(event -> onCreate());
    }

    private void onCreate()
    {
        String ISBN = field_ISBN.getText();
        String Name = field_Name.getText();
        String Category = field_category.getText();
        String Description = field_description.getText();

        int PageNumber;
        int Price;

        try{
            PageNumber = Integer.parseInt(field_page_number.getText());
            Price = Integer.parseInt(field_price.getText());
        }
        catch (NumberFormatException e)
        {
            AlertUtility.displayError("Ivestos raides ten kur turi būti numeriai");
            return;
        }

        String PublishDate = field_publis_date.getText();
        String author = Autoriai.getValue();

        if (ISBN.isEmpty() || Name.isEmpty() || Category.isEmpty() || Description.isEmpty() ||
                field_page_number.getText().isEmpty() || field_price.getText().isEmpty() ||
                PublishDate.isEmpty() || author == null || author.isEmpty()) {
            AlertUtility.displayError("Visis laukai turi būti užpildyti");
            return;
        }

            Author foundAuthor = Model.getInstance().findAuthor(author);

        Model.getInstance().createBook(ISBN, Name, Category, Description, PageNumber, PublishDate, Price, foundAuthor.getId());

        AlertUtility.displayInformation("Knyga sekmingai sukurta");

        emptyFields();
    }

    private void emptyFields()
    {
        field_ISBN.setText("");
        field_Name.setText("");
        field_description.setText("");
        field_category.setText("");
        field_price.setText("");
        field_page_number.setText("");
        field_publis_date.setText("");
        Autoriai.setValue("");
    }

    private void setChoiceBoxOptions()
    {
        List<Author> authors = Model.getInstance().getAuthors();

        List<String> list = new ArrayList<>();

        for (Author i : authors)
        {
            list.add(i.getFirstName() + " " + i.getLastName() + " " + i.getEmail());
        }

        Autoriai.getItems().addAll(list);
    }


}
