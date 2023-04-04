package Controller;

import DAO.DBCountries;
import Models.Country;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
import java.util.Optional;

/**
 * This class is a Controller template.
 *
 * Controls the Login screen of the app.
 */
public class LoginScreenController implements Initializable {

//    public TableColumn idColumn;        //  ------------   DO I NEED THESE?
//    public TableColumn nameColumn;
//    public TableColumn passwordColumn;
//    public TableView dataTable;

    @FXML private Button loginButton;
    @FXML private Button cancelButton;
    @FXML private TextField userNameField;
    @FXML private TextField passwordField;


    /**
     * Initializes the Login screen
     *
     * @param url            the location
     * @param resourceBundle the resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize screen
        ResourceBundle rb = ResourceBundle.getBundle("language");
        System.out.println(rb.getString("welcome"));
    }
}
