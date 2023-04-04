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
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * This class is a Controller template.
 *
 * Controls the Login screen of the app.
 */
public class LoginScreenController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userNameField;

    @FXML
    void onActionCancel(ActionEvent event) {

    }

    @FXML
    void onActionLogin(ActionEvent event) {

    }


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
