package com.kitm.darbas1.Views;

import com.kitm.darbas1.Controllers.RouteController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {

    private final ObjectProperty<MenuItems> userSelectedMenuItem;
    private AnchorPane dashboard;

    public ViewFactory(){
        this.userSelectedMenuItem = new SimpleObjectProperty<>();
    }

    /*
    * Getter for user selected menu item
    * @return the Object property
    */

    public ObjectProperty<MenuItems> getUserSelectedMenuItem()
    {
        return userSelectedMenuItem;
    }

    /*
     * Show dashboard
     */

    public AnchorPane getDashboardView()
    {
        if (dashboard == null)
        {
            try{
                dashboard = new FXMLLoader(getClass().getResource("/Fxml/Dashboard.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return dashboard;
    }

    /*
    * Show login window
    */

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    /*
     * Show register window
     */

    public void showRegisterWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Register.fxml"));
        createStage(loader);
    }

    /*
    * Show main window
    */

    public void showMainWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Main.fxml"));
        RouteController controller = new RouteController();
        loader.setController(controller);
        createStage(loader);
    }

    /*
    * Create and display new stage.
    * @param loader the FXML loader instance. Load fxml file and create scene
    */

    public void createStage(FXMLLoader loader)
    {
        Scene scene = null;

        try{
            scene = new Scene(loader.load());
        }catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("KITM knygynas");
        stage.show();
    }

    /*
    * Close provided stage
    * @param stage to close
    */

    public void closeStage(Stage stage)
    {
        stage.close();
    }
}
