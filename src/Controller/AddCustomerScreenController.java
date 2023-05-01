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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class controls the Add Customer screen.
 * The Add Customer screen enables the user to enter and upload new customer data to the database.
 */
public class AddCustomerScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private ComboBox<String> dropDownCountry;

    @FXML
    private ComboBox<String> dropDownDivision;

    @FXML
    private Label labelDivision;

    @FXML
    private Button addButton;

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


    /**
     * This method adds a new customer to the database.
     * After the user inputs valid new customer data, a new Customer object is created and uploaded to the database.
     *
     * @param event Executes when Add button is pressed.
     * @throws SQLException In the event of an SQL error.
     * @throws IOException In the event of an IO error.
     */
    @FXML
    void onActionAddNewCustomer(ActionEvent event) throws SQLException, IOException
    {
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


    /**
     * This method populates dropDownDivision combobox with the appropriate list once the user selects a value in dropDownCountry combobox.
     * The label labelDivision is also updated to the appropriate term as related to their customs.
     *
     * @param event Executes when user selects country from dropDownCountry combobox.
     */
    @FXML
    void onActionCountrySelect(ActionEvent event)
    {
        ObservableList<Division> allDivisions = DBDivisions.getAllDivisions();
        ObservableList<String> filteredDivisionNames = FXCollections.observableArrayList();

        String countryName = dropDownCountry.getValue().toString();

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
     * This method returns the user to the previous screen (Customers screen).
     *
     * @param event Executes when the user presses the Cancel button.
     * @throws IOException In the event of an IO error.
     */
    @FXML
    void onActionReturnPreviousScreen(ActionEvent event) throws IOException
    {
        System.out.println("Cancel button pressed");

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/Customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();
    }


    /**
     * This method initializes the Add Customer screen.
     * Here dropDownCountry and dropDownDivision comboboxes are populated.
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
        dropDownCountry.setVisibleRowCount(5);              //   Limit dropdown box row count to 5

        dropDownDivision.getSelectionModel().clearSelection();
        dropDownDivision.setVisibleRowCount(5);              //   Limit dropdown box row count to 5

        textFieldCustomerID.setPromptText("Auto Generated");
    }



}
