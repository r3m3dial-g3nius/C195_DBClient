package Controller;

import DAO.DBCountries;
import DAO.DBCustomers;
import DAO.DBDivisions;
import Helper.DBConnection;
import Models.Country;
import Models.Customer;
import Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is a Controller for the Customer Screen.
 *
 * Controls the Customer screen of the app.
 */
public class CustomerScreenController implements Initializable {

    Stage stage;
    Parent scene;
    static Customer selectedCustomer;

    public static Customer getSelectedCustomer()
    {
        return selectedCustomer;
    }

    @FXML
    private ComboBox<String> dropDownCountry;

    @FXML
    private ComboBox<String> dropDownDivision;

    @FXML
    private Button resetFilterButton;

    @FXML
    private TableColumn<?, ?> columnCustomerID;

    @FXML
    private TableColumn<?, ?> columnName;

    @FXML
    private TableColumn<?, ?> columnAddress;

    @FXML
    private TableColumn<?, ?> column1stLevelDivision;

    @FXML
    private TableColumn<?, ?> columnCountry;

    @FXML
    private TableColumn<?, ?> columnPostalCode;

    @FXML
    private TableColumn<?, ?> columnPhone;

    @FXML
    private TableView<Customer> customersTableView;

    @FXML
    private Button mainMenuButton;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button modifyCustomerButton;

    @FXML
    private Button deleteCustomerButton;

    /**
     * fires when the Reset button is pressed; reloads table without filters
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionResetFilter(ActionEvent event) throws IOException
    {
        System.out.println("Reset filter button pressed");

//        dropDownCountry.getSelectionModel().clearSelection();
//        dropDownDivision.getSelectionModel().clearSelection();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/Customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();

    }

    /**
     * fires when the either combobox receives a new selection; applies user selected filters from Country and Division to tableview
     *
     * Lambda expressions #1 - populates string list of division names from list of division objects
     * Lambda expressions #1 - populates string list of country names from list of country objects
     * @param event
     */
    @FXML
    void onActionApplyFilter(ActionEvent event)
    {
        System.out.println("Apply filter button pressed");

        String countryFilter = dropDownCountry.getValue();
        String divisionFilter = dropDownDivision.getValue();

        ObservableList<Customer> customerList = DBCustomers.getAllCustomers();
        ObservableList<Customer> filteredCustomerList = FXCollections.observableArrayList();


        //   -----   if either dropdown has a selection   -----
        if (!countryFilter.equals("Country") || !divisionFilter.equals("Division"))
        {
            //  -----   if only country drop down is selected   -----
            if (!countryFilter.equals("Country") && divisionFilter.equals("Division"))
            {
                System.out.println(countryFilter);          // test

                //  -----   update dropDownDivision to reflect divisions in selected country   -----
                ObservableList<Division> allDivisions = DBDivisions.getAllDivisions();
                ObservableList<String> filteredDivisionNames = FXCollections.observableArrayList();

                for (Division division : allDivisions)
                {
                    if (division.getCountryName(division.getCountryID()).equals(countryFilter))
                    {
                        filteredDivisionNames.add(division.getDivisionName());
                    }
                }

                dropDownDivision.setItems(filteredDivisionNames);

                for (Customer customer : customerList)
                {
                    if (customer.getCustomerCountry().equals(countryFilter))
                    {
                        filteredCustomerList.add(customer);
                    }
                }
            }

            //  -----   if only division drop down is selected   -----
            else if (countryFilter.equals("Country") && !divisionFilter.equals("Division"))
            {
                for (Customer customer : customerList)
                {
                    if (customer.getDivisionName().equals(divisionFilter))
                    {
                        filteredCustomerList.add(customer);
                    }
                }
            }

            //  -----   if country & division drop down are selected   -----
            else if (!countryFilter.equals("Country") && !divisionFilter.equals("Division"))
            {
                for (Customer customer : customerList)
                {
                    if (customer.getCustomerCountry().equals(countryFilter) && customer.getDivisionName().equals(divisionFilter))
                    {
                        filteredCustomerList.add(customer);
                    }
                }
            }

            //  ------------------------   VVVVV   update tableview   VVVVV   ------------------------
            //  ------   Creates observable list of string values to populate drop down boxes to filter customer table   ------
            ObservableList<Division> allDivisions = DBDivisions.getAllDivisions();
            ObservableList<String> divisionNames = FXCollections.observableArrayList();

            ObservableList<Country> allCountries = DBCountries.getAllCountries();
            ObservableList<String> countryNames = FXCollections.observableArrayList();

            //  --->   LAMBDA expression #1   <---
            allDivisions.forEach(division -> divisionNames.add(division.getDivisionName()));

            //  --->   LAMBDA expression #2   <---
            allCountries.forEach(country -> countryNames.add(country.getCountryName()));

            customersTableView.setItems(filteredCustomerList);

            columnCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            columnName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            columnAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            column1stLevelDivision.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
            columnCountry.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
            columnPostalCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            columnPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        }

        else
        {
            //   do nothing
            System.out.println("No filter selected");
        }
    }

    /**
     * fires when the Main Menu button is selected; returns user to Main Menu
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();
    }

    /**
     * fires when Add button is selected; opens Add Customer screen
     * @param event
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        System.out.println("Add Customer Button pressed");

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();
    }

    /**
     * fires when Modify button is selected; opens Modify Customer screen
     * @param event
     */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {
        System.out.println("Modify Customer button pressed");

        selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a Customer");
            alert.showAndWait();
            return;
        }

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/ModifyCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();

    }


    /**
     * fires when Delete button is pressed; deletes all appointments associated w/ selected Customer, then deletes Customer
     * @param event
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event)
    {
        System.out.println("Delete Customer button pressed");

        selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();

        //  -------------------------------   Check for selection   ---------------------------------------
        if (selectedCustomer == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a Customer");
            alert.showAndWait();
            return;
        }

        //  -------------------------------   Confirm delete   ---------------------------------------

        if (selectedCustomer != null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer Confirmation");
            alert.setContentText("Are you sure you want to delete this customer?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK)
            {
                //      ---------------------------------------------    FIX ME   -------------------------------------
                //      ---------------------------------------------    FIX ME   -------------------------------------
                //      ---------------------------------------------    FIX ME   -------------------------------------
                //      ---------------------------------------------    FIX ME   -------------------------------------
                //      ---------------------------------------------    FIX ME   -------------------------------------
                System.out.println("Insert code to delete all of selected customer's appointments then delete customer");
                //      ---------------------------------------------    FIX ME   -------------------------------------
                //      ---------------------------------------------    FIX ME   -------------------------------------
                //      ---------------------------------------------    FIX ME   -------------------------------------
                //      ---------------------------------------------    FIX ME   -------------------------------------
                //      ---------------------------------------------    FIX ME   -------------------------------------
            }

            else if (result.isPresent() && result.get() != ButtonType.OK)
            {
                System.out.println("Deletion cancelled");

            }
        }
    }


    /**
     * Initializes the Customer screen
     *
     * Lambda expression #1 - populates ObservableList with String values of division names
     * Lambda expression #2 - populates ObservableList with String values of country names
     *
     * @param url the location
     * @param resourceBundle the resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            //  ------   Creates observable list of string values to populate drop down boxes to filter customer table   ------
            ObservableList<Division> allDivisions = DBDivisions.getAllDivisions();
            ObservableList<String> divisionNames = FXCollections.observableArrayList();

            ObservableList<Country> allCountries = DBCountries.getAllCountries();
            ObservableList<String> countryNames = FXCollections.observableArrayList();

            //  --->   LAMBDA expression #1  <---
            allDivisions.forEach(division -> divisionNames.add(division.getDivisionName()));

            //  --->   LAMBDA expression #2  <---
            allCountries.forEach(country -> countryNames.add(country.getCountryName()));


            customersTableView.setItems(DBCustomers.getAllCustomers());

            columnCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            columnName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            columnAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            column1stLevelDivision.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
            columnCountry.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
            columnPostalCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            columnPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));

            dropDownCountry.setValue("Country");                //   Set dropdown box label
            dropDownCountry.setItems(countryNames);             //   Populate dropdown box items
            dropDownCountry.setVisibleRowCount(5);              //   Limit dropdown box row count to 5

            dropDownDivision.setValue("Division");              //   Set dropdown box label
            dropDownDivision.setItems(divisionNames);           //   Populate dropdown box items
            dropDownDivision.setVisibleRowCount(5);              //   Limit dropdown box row count to 5
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
