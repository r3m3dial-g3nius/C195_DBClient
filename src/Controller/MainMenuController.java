package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the Main Menu screen of the app.
 */
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * Main Menu label (title)
     */
    @FXML
    private Label mainMenuTitle;

    /**
     * Button to open Appointments screen
     */
    @FXML
    private Button appointmentsButton;

    /**
     * Button to open Customers screen
     */
    @FXML
    private Button customersButton;

    /**
     * Button to open Reports Screen
     */
    @FXML
    private Button reportsButton;

    /**
     * Button to exit app
     */
    @FXML
    private Button exitButton;

    /**
     * fires when Appointments button is pressed
     * @param event
     */
    @FXML
    void onActionAppointmentButton(ActionEvent event) {
        System.out.println("Appointments button pressed");

    }

    /**
     * fires when Customers button is pressed
     * @param event
     */
    @FXML
    void onActionCustomersButton(ActionEvent event) throws IOException {
        System.out.println("Customers button pressed");
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/Customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();
    }

    /**
     * fires when Reports button is pressed
     * @param event
     */
    @FXML
    void onActionReportsButton(ActionEvent event) {
        System.out.println("Reports button pressed");

    }

    /**
     * fires when the Exit button is pressed
     * @param event
     */
    @FXML
    void onActionExit(ActionEvent event)
    {
        System.out.println("Exit Button pressed");
        System.exit(0);
    }

    /**
     * Initializes the Main Menu screen
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
