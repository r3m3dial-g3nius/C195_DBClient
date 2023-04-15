package Controller;

import DAO.DBCountries;
import DAO.DBCustomers;
import DAO.DBDivisions;
import Models.Country;
import Models.Customer;
import Models.Division;
import Controller.LoginScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class controls the Modify Customer screen
 */
public class ModifyCustomerScreenController implements Initializable{

    Stage stage;
    Parent scene;
    Customer selectedCustomer;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<String> dropDownCountry;

    @FXML
    private ComboBox<String> dropDownDivision;

    @FXML
    private Label labelDivision;

    @FXML
    private TextField textFieldAddress;

    @FXML
    private TextField textFieldCustomerID;

    @FXML
    private TextField textFieldCustomerName;

    @FXML
    private TextField textFieldPhone;

    @FXML
    private TextField textFieldPostalCode;

    @FXML
    private Button updateButton;

    /**
     * fires when user selects country from country drop down, populates division drop down w appropriate divisions based on country selection
     *
     * @param event
     */
    @FXML
    void onActionCountrySelect(ActionEvent event) throws NullPointerException
    {
        dropDownDivision.setValue("");

        ObservableList<Division> allDivisions = DBDivisions.getAllDivisions();
        ObservableList<String> filteredDivisionNames = FXCollections.observableArrayList();

        String countryName = dropDownCountry.getValue();

        if (countryName.equals("U.S"))
        {
            labelDivision.setText("State");
        }

        else if (countryName.equals("Canada"))
        {
            labelDivision.setText("Province/Territory");
        }

        else if (countryName.equals("UK"))
        {
            labelDivision.setText("Country/Province");
        }

        for (Division d : allDivisions)
        {
            if (d.getCountryName(d.getCountryID()).equals(countryName))
            {
                filteredDivisionNames.add(d.getDivisionName());
            }
        }

        dropDownDivision.setItems(filteredDivisionNames);

    }

    /**
     * fires when Cancel button is pressed, returns user to previous screen
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionReturnPreviousScreen(ActionEvent event) throws IOException {
        System.out.println("Cancel button pressed");

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/Customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();

    }

    /**
     * fires when Update button is pressed, updates Customer data in database
     *
     * @param event
     */
    @FXML
    void onActionUpdateCustomer(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ObservableList<Country> allCountries = DBCountries.getAllCountries();
        ObservableList<String> allCountriesString = FXCollections.observableArrayList();

        allCountries.forEach(country -> allCountriesString.add(country.toString()));

        selectedCustomer = CustomerScreenController.getSelectedCustomer();
        String countryName = selectedCustomer.getCustomerCountry();

        textFieldCustomerID.setText(Integer.toString(selectedCustomer.getCustomerID()));
        textFieldCustomerName.setText(selectedCustomer.getCustomerName());
        textFieldAddress.setText(selectedCustomer.getCustomerAddress());
        textFieldPostalCode.setText(selectedCustomer.getCustomerPostalCode());
        textFieldPhone.setText(selectedCustomer.getCustomerPhone());

        dropDownCountry.setItems(allCountriesString);

        dropDownCountry.setVisibleRowCount(5);              //   Limit dropdown box row count to 5
        dropDownDivision.setVisibleRowCount(5);              //   Limit dropdown box row count to 5


        dropDownCountry.setValue(countryName);
        dropDownDivision.setValue(selectedCustomer.getDivisionName());


        if (countryName.equals("U.S"))
        {
            labelDivision.setText("State");
        }

        else if (countryName.equals("Canada"))
        {
            labelDivision.setText("Province/Territory");
        }

        else if (countryName.equals("UK"))
        {
            labelDivision.setText("Country/Province");
        }

    }

}
