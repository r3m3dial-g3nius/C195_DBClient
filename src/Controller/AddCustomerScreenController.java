package Controller;

import DAO.DBCountries;
import DAO.DBCustomers;
import DAO.DBDivisions;
import Models.Country;
import Models.Division;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class controls the Add Customer screen.
 *
 * Controls the Add Customer screen of the app.
 */
public class AddCustomerScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private ComboBox<String> dropDownCountry;

    @FXML
    private ComboBox<String> dropDownDivision;

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
    void onActionAddNewCustomer(ActionEvent event) throws SQLException, IOException {
        String customerName = textFieldCustomerName.getText();
        String customerAddress = textFieldAddress.getText();
        String postalCode = textFieldPostalCode.getText();
        String phoneNumber = textFieldPhone.getText();

        String countryName = dropDownCountry.getValue();
        String divisionName = dropDownDivision.getValue();

        if (!customerName.isEmpty() || !customerAddress.isEmpty() || !postalCode.isEmpty() || !phoneNumber.isEmpty() || !countryName.isEmpty() || !divisionName.isEmpty())
        {
            System.out.println("Adding " + customerName);

            //  ----->   ADD NEW CUSTOMER VIA SQL   <-----
            DBCustomers.addNewCustomer(customerName, customerAddress, postalCode, phoneNumber, divisionName);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/Views/Customers.fxml"));
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();                 //  ----------------   Center Screen
            stage.show();

        }
    }









    @FXML
    void onActionCountrySelect(ActionEvent event) {
        ObservableList<Division> allDivisions = DBDivisions.getAllDivisions();
        ObservableList<String> filteredDivisionNames = FXCollections.observableArrayList();

        String countryName = dropDownCountry.getValue().toString();

        for (Division d : allDivisions)
        {
            if (d.getCountryName(d.getCountryID()).equals(countryName))
            {
                filteredDivisionNames.add(d.getDivisionName());
            }
        }

        dropDownDivision.setItems(filteredDivisionNames);
    }

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
     * Initializes the Add Customer screen
     *
     * @param url the location
     * @param resourceBundle the resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // initialize screen, populate dropDownCountry box w string values
        ObservableList<Country> allCountries = DBCountries.getAllCountries();
        ObservableList<String> allCountriesString = FXCollections.observableArrayList();

        allCountries.forEach(country -> allCountriesString.add(country.toString()));

        dropDownCountry.setItems(allCountriesString);
        dropDownDivision.getSelectionModel().clearSelection();
        textFieldCustomerID.setPromptText("Auto Generated");
    }



}
