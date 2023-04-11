package Controller;

import DAO.DBCountries;
import DAO.DBCustomers;
import DAO.DBDivisions;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is a Controller for the Customer Screen.
 *
 * Controls the Customer screen of the app.
 */
public class CustomerScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private ChoiceBox<String> dropDownCountry;

    @FXML
    private ChoiceBox<String> dropDownDivision;

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

    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();
    }

    @FXML
    void onActionAddCustomer(ActionEvent event) {

    }

    @FXML
    void onActionModifyCustomer(ActionEvent event) {

    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

    }

    /**
     * Initializes the Customer screen
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

            //  --->   LAMBDA expression   <---
            allDivisions.forEach(division -> divisionNames.add(division.getDivisionName()));

            ObservableList<Country> allCountries = DBCountries.getAllCountries();
            ObservableList<String> countryNames = FXCollections.observableArrayList();

            //  --->   LAMBDA expression   <---
            allCountries.forEach(country -> countryNames.add(country.getCountryName()));


            customersTableView.setItems(DBCustomers.getAllCustomers());

            columnCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            columnName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            columnAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            column1stLevelDivision.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
            columnCountry.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
            columnPostalCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            columnPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));

            dropDownDivision.setItems(divisionNames);
            dropDownCountry.setItems(countryNames);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
