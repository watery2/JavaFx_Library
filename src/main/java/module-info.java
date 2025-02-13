module com.kitm.darbas1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;


    opens com.kitm.darbas1 to javafx.fxml;
    exports com.kitm.darbas1;
    exports com.kitm.darbas1.Controllers;
    exports com.kitm.darbas1.Models;
    exports com.kitm.darbas1.Views;

}