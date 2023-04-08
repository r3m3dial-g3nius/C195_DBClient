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
import java.util.Scanner;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controls the Login screen of the app.
 */
public class LoginScreenController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * "Login" label at top of window
     */
    @FXML
    private Label loginScreenTitle;

    /**
     * Label for Password text field
     */
    @FXML
    private Label passwordLabel;

    /**
     * Label for Username text field
     */
    @FXML
    private Label userNameLabel;

    /**
     * The Cancel button on the Login Screen
     */
    @FXML
    private Button cancelButton;

    /**
     * The Login button on the Login screen
     */
    @FXML
    private Button loginButton;

    /**
     * The password text field in the Login Screen
     */
    @FXML
    private PasswordField passwordField;

    /**
     * The Username text field in the Login Screen
     */
    @FXML
    private TextField userNameField;

    /**
     * Label for "User Location"
     */
    @FXML
    private Label userLocationLabel;

    /**
     * relays user location per system Zone ID
     */
    @FXML
    private Label userLocationText;

    /**
     * fires when Cancel button is pressed
     *
     * @param event closes app
     */
    @FXML
    void onActionCancel(ActionEvent event) {
        System.out.println("Cancel Button Pressed");
        System.exit(0);
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
            System.out.println("Login Button pressed!\n");                                    // test print

            String user_name = userNameField.getText();
            String password = passwordField.getText();

            ResourceBundle rb = ResourceBundle.getBundle("language");

            User newUser = DBUsers.getUser(user_name, password);

//        System.out.println(user_name + " - " + password);                           // test user_name and password field input
//        System.out.println("User ID: " + newUser.getUserID());               //
//        System.out.println("User Name: " + newUser.getUserName());           //
//        System.out.println("Password: " + newUser.getPassword());            //

            if (newUser.getUserID() == 0)               // -----------------   If invalid login
            {
                System.out.println("Whoops!  Invalid login credentials...please try again");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("error"));
                alert.setContentText(rb.getString("error_message"));
                alert.show();
            }

            else                                        //  ----------------   else Valid login
            {
                System.out.println("Username '" + user_name + "' successfully logged in at " + Timestamp.valueOf(LocalDateTime.now()));
                System.out.println();

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.centerOnScreen();                 //  ----------------   Center Screen
                stage.show();
            }
        }

        catch (IOException e)
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
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        boolean testing = false;

        //   VVVVVV  -----------------   TEST LOGIN TRANSLATION   -----------------------   VVVVVV
        Locale fr = new Locale("fr", "FR");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter 'F' for French or any other key for English: ");
        String setLangToFrench = scanner.nextLine().toLowerCase();

        if (setLangToFrench.equals("f"))
        {
            Locale.setDefault(fr);
            testing = true;
        }
        //   ^^^^^^  ------------------   TEST LOGIN TRANSLATION   -----------------------   ^^^^^^


        //   --------------------   Detects timezone and updates User Location Text   -------------
        ZoneId timeZone = ZoneId.systemDefault();
        userLocationText.setText(timeZone.toString());

        //   ----------------   Get Resource bundle and confirm language w "welcome"   -------------
        ResourceBundle rb = ResourceBundle.getBundle("language", Locale.getDefault());
        System.out.println(rb.getString("welcome"));

        //   --------------------   set GUI labels/buttons here   --------------------------
        if (Locale.getDefault().getLanguage().equals("fr"))
        {
            System.out.println("Language set to French");
            loginScreenTitle.setText(rb.getString("login"));
            userNameLabel.setText(rb.getString("username"));
            passwordLabel.setText(rb.getString("password"));
            loginButton.setText(rb.getString("login"));
            cancelButton.setText(rb.getString("cancel"));
            userLocationLabel.setText(rb.getString("user_location"));
            userLocationText.setText(timeZone.toString());
        }

        if ((Locale.getDefault().getLanguage().equals("fr")) && testing)
        {
            userLocationText.setText("!France (Test)");
        }

        //   -------------------------------------------------------------------------------


    }
}
