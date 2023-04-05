package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the Main Menu screen of the app.
 */
public class MainMenuController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Button okButton;

    @FXML
    void onActionCancel(ActionEvent event)
    {
        // code here
    }

    @FXML
    void onActionOK(ActionEvent event) {
        // code here

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
