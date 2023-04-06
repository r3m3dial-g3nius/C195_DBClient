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
    private Button exitButton;

    @FXML
    private Button okButton;

    @FXML
    void onActionExit(ActionEvent event)
    {
        System.out.println("Exit Button pressed");
        System.exit(0);
    }

    @FXML
    void onActionOK(ActionEvent event) {
        // code here
        System.out.println("Main Menu OK button pressed");

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
