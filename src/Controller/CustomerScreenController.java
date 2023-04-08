package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is a Controller template.
 *
 * Controls the XXXXX screen of the app.
 */
public class CustomerScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button addCustomerButton;

    @FXML
    private TableColumn<?, ?> column_Address;

    @FXML
    private TableColumn<?, ?> column_Customer_ID;

    @FXML
    private TableColumn<?, ?> column_Name;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Button mainMenuButton;

    @FXML
    private Button modifyCustomerButton;

    @FXML
    void onActionAddCustomer(ActionEvent event) {

    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

    }

    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();
    }

    @FXML
    void onActionModifyCustomer(ActionEvent event) {

    }

    /**
     * Initializes the XXXXXXX screen
     *
     * @param url the location
     * @param resourceBundle the resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // initialize screen
    }



}
