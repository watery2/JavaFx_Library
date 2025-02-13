package com.kitm.darbas1.Models;

import com.kitm.darbas1.Views.ViewFactory;
import com.kitm.darbas1.dao.AuthorsDAO;
import com.kitm.darbas1.dao.BookDAO;
import com.kitm.darbas1.dao.UserDAO;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class Model {
    private static Model model; //Singleton instance
    private final ViewFactory viewFactory;
    public final UserDAO userDAO;
    private boolean loginSuccessFlag;
    private User currentUser;
    public final AuthorsDAO authorsDAO;
    public final BookDAO booksDAO;

    private Model()
    {
        this.viewFactory = new ViewFactory();
        this.userDAO = new UserDAO(new DatabaseDriver().getConnection());
        this.authorsDAO = new AuthorsDAO(new DatabaseDriver().getConnection());
        this.booksDAO = new BookDAO(new DatabaseDriver().getConnection());
        this.currentUser = null;
    }

    /**
    * Return singleton instance of the Model class
    * @return the singleton instance
    */

    public static synchronized Model getInstance(){
        if (model == null)
        {
            model = new Model();
        }

        return model;
    }

    /**
    * Get ViewFactory instance
    * @return ViewFactory instance
    */

    public ViewFactory getViewFactory()
    {
        return viewFactory;
    }

    /**
     *
     * @return loginSuccessFlag
     */

    public boolean getLoginSuccessFlag() {
        return loginSuccessFlag;
    }

    /**
     * set login success flag
     * @param flag
     */

    public void setLoginSuccessFlag(boolean flag){
        this.loginSuccessFlag = flag;
    }

    /**
    * Create new user in DB
    *
    * @param userName
    * @param password
    * */

    public void createUser(String userName, String password)
    {
        userDAO.createUser(userName, password, LocalDate.now());
    }


    public void checkCredentials(String userName, String password){
        User user = userDAO.findUserByCredentials(userName, password);

        if(user != null)
        {
            this.loginSuccessFlag = true;
            this.currentUser = user;
        }
    }

    /**
     * get current user name
     *
     * @return userName
     */

    public String getLoggedUserName()
    {
        return currentUser != null ? currentUser.userNameProperty(): null;
    }

    /**
     * Get current user id
     *
     * @return id - user id
     */

    public int getLoggedUserId()
    {
        return currentUser != null ? currentUser.getId() : null;
    }

    /**
     * Check if user exit in system
     * @param userName
     * @return true if user exist
     */

    public boolean isUserExist(String userName)
    {
        return userDAO.isUserExist(userName);
    }

    /**
     * Check if exist users in system
     *
     * @return true is users exist
     */

    public boolean hasRegisteredUsers()
    {
        return userDAO.countUsers() > 0;
    }

    /**
     * Create author
     */

    public void createAuthor(String firstName, String lastName, String email, String city)
    {
        authorsDAO.create(firstName, lastName, email, city);
    }

    public Author findAuthor(String Name_LastName_Email)
    {
        List<Author> list = getAuthors();

        for (Author i : list)
        {
            if (Name_LastName_Email.equals(i.getFirstName() + " " + i.getLastName() + " " + i.getEmail()))
            {
                return i;
            }
        }

        return null;
    }

    /**
     * Retrieves all authors from DB
     *
     * @return authors
     */

    public ObservableList<Author> getAuthors()
    {
        return authorsDAO.findAll();
    }

    public Author getAuthorsById(int id)
    {
        return authorsDAO.findById(id);
    }

    /**
     * Delete author from DB by ID
     * @param id the ID author
     */

    public void deleteAuthor(int id){

        authorsDAO.delete(id);
    }

    public void deleteBook(int id) {
        booksDAO.delete(id);
    }

    /**
     * Update existing author in the database
     */

    public void updateAuthor(Author author)
    {
        authorsDAO.update(author);
    }

    public void updateBook(Book book)
    {
        booksDAO.update(book);
    }

    public void createBook(String ISBN, String Name, String Category, String Description, int PageNumber, String ReleaseDate, int Price, int AuthorID)
    {
        booksDAO.create(ISBN, Name, Category, Description, PageNumber, ReleaseDate, Price, AuthorID);
    }

    public ObservableList<Book> getBooks()
    {
        return booksDAO.findAll();
    }

}
