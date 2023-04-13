package Controller;

import Models.Country;
import Models.Division;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class controls the Add Customer screen.
 *
 * Controls the Add Customer screen of the app.
 */
public class AddCustomerScreenController implements Initializable {


    @FXML
    private ComboBox<Country> dropDownCountry;

    @FXML
    private ComboBox<Division> dropDownDivision;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField textFieldCustomerID;

    @FXML
    private TextField textFieldCustomerName;

    @FXML
    private TextField textFieldAddress;

    @FXML
    private TextField textFieldPostalCode;

    @FXML
    private TextField textFieldPhone;

    @FXML
    void onActionAddNewCustomer(ActionEvent event) {

    }

    @FXML
    void onActionCountrySelect(ActionEvent event) {

    }

    @FXML
    void onActionReturnPreviousScreen(ActionEvent event) {

    }


    /**
     * Initializes the Add Customer screen
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
