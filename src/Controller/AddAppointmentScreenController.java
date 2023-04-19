package Controller;

import DAO.DBContacts;
import Models.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class manages the Add Appointment screen.
 *
 * Controls the Add Appointment screen of the app.
 */
public class AddAppointmentScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private DatePicker datePickerStart;

    @FXML
    private DatePicker datePickerEnd;

    @FXML
    private ComboBox<?> dropDownStartTime;

    @FXML
    private ComboBox<?> dropDownEndTime;

    @FXML
    private ComboBox<String> dropDownContact;

    @FXML
    private ComboBox<?> dropDownCustomer;

    @FXML
    private ComboBox<?> dropDownUser;

    @FXML
    private TextField textFieldAppointmentID;

    @FXML
    private TextField textFieldDescription;

    @FXML
    private TextField textFieldLocation;

    @FXML
    private TextField textFieldPhone;

    @FXML
    private TextField textFieldPostalCode;

    @FXML
    private TextField textFieldTitle;

    @FXML
    private TextField textFieldType;

    @FXML
    void onActionContactSelect(ActionEvent event) {

    }

    @FXML
    void onActionCountrySelect(ActionEvent event) {

    }

    @FXML
    void onActionAddNewCustomer(ActionEvent event) {

    }

    @FXML
    void onActionReturnPreviousScreen(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();
    }








    /**
     * Initializes the Add Appointment screen
     *
     * @param url the location
     * @param resourceBundle the resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // initialize screen
        try {

            //   ---------->   populate dropDownContact   <----------
            ObservableList<Contact> allContacts = DBContacts.getAllContacts();
            ObservableList<String> contactNames = FXCollections.observableArrayList();

            //  --->   LAMBDA expression #1  <---
            allContacts.forEach(contact -> contactNames.add(contact.getContactName()));
            dropDownContact.setItems(contactNames);
            dropDownContact.setPromptText("ALL");
            dropDownContact.setVisibleRowCount(5);

            //   ---------->   populate dropDownCustomer   <----------




            //   ---------->   populate dropDownUser   <----------



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



}
