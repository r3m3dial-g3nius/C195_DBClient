package Controller;

import DAO.DBCountries;
import DAO.DBUsers;
import Models.Country;
import Models.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
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

    /**
     * fires when Cancel button is pressed
     *
     * @param event closes app
     */
    @FXML
    void onActionCancel(ActionEvent event) {
        System.out.println("Cancel Button Pressed");
        System.exit(0);

//        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();    //  WHICH ONE IS CORRECT?
//        stage.close();

    }

    /**
     * fires when Login button is pressed
     *
     * @param event validates login credentials, progresses to either Main Menu or Error Message
     */
    @FXML
    void onActionLogin(ActionEvent event)
    {
        try
        {
//            System.out.println("Login Button pressed!\n");                                    // test print

            String user_name = userNameField.getText();
            String password = passwordField.getText();

            ResourceBundle rb = ResourceBundle.getBundle("language");

            User authorizedUser = DBUsers.getUser(user_name, password);

//        System.out.println(user_name + " - " + password);                           // test user_name and password field input
//        System.out.println("User ID: " + authorizedUser.getUserID());               //
//        System.out.println("User Name: " + authorizedUser.getUserName());           //
//        System.out.println("Password: " + authorizedUser.getPassword());            //

            if (authorizedUser.getUserID() == 0)
            {
                System.out.println("Whoops!  Invalid login credentials...please try again");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("error"));
                alert.setContentText(rb.getString("error_message"));
                alert.show();
            }

            else
            {
                System.out.println("Username '" + user_name + "' successfully logged in at " + Timestamp.valueOf(LocalDateTime.now()));
                System.out.println();
            }

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

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
        Locale locale = Locale.getDefault();
        Locale.setDefault(locale);

        ZoneId timeZone = ZoneId.systemDefault();

        ResourceBundle rb = ResourceBundle.getBundle("language");   // -----   Locale.getDefault() ??
        System.out.println(rb.getString("welcome"));
    }
}
