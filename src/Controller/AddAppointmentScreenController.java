package Controller;

import Utility.TimeTraveller;
import DAO.DBContacts;
import DAO.DBCustomers;
import DAO.DBUsers;
import Models.Contact;
import Models.Customer;
import Models.User;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    private ComboBox<String> dropDownStartTime;

    @FXML
    private ComboBox<String> dropDownEndTime;

    @FXML
    private ComboBox<String> dropDownContact;

    @FXML
    private ComboBox<String> dropDownCustomer;

    @FXML
    private ComboBox<String> dropDownUser;

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
    private TextField textFieldCustomerName;

    @FXML
    void onActionSetCustomerTextField(ActionEvent actionEvent)
    {
        ObservableList<Customer> allCustomers = DBCustomers.getAllCustomers();

        int customerID = Integer.parseInt(dropDownCustomer.getSelectionModel().getSelectedItem());
        String customerName = "";

        for (Customer c : allCustomers)
        {
            if (customerID == c.getCustomerID())
            {
                customerName = c.getCustomerName();
            }
        }

        textFieldCustomerName.setText(customerName);
    }

    @FXML
    private TextField textFieldUserName;

    @FXML
    void onActionSetUserTextField(ActionEvent actionEvent)
    {
        ObservableList<User> allUsers = DBUsers.getAllUsers();

        int userID = Integer.parseInt(dropDownUser.getSelectionModel().getSelectedItem());
        String userName = "";

        for (User u : allUsers)
        {
            if (userID == u.getUserID())
            {
                userName = u.getUserName();
            }
        }

        textFieldUserName.setText(userName);
    }















    @FXML
    void onActionAddNewCustomer(ActionEvent event) {
        System.out.println("Adding new customer");

        DateTimeFormatter hourMinFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd");

        String title = textFieldTitle.getText();
        String description = textFieldDescription.getText();
        String location = textFieldLocation.getText();
        String contact = dropDownContact.getValue();
        String type = textFieldType.getText();
        int customerID = Integer.parseInt(dropDownCustomer.getValue());
        int userID = Integer.parseInt(dropDownUser.getValue());

        //   -----   start/end time   -----                                  *** String?
        String startTime = dropDownStartTime.getValue();
        String endTime = dropDownEndTime.getValue();
        LocalTime startingTime = LocalTime.parse(dropDownStartTime.getValue(), hourMinFormatter);
        LocalTime endingTime = LocalTime.parse(dropDownEndTime.getValue(), hourMinFormatter);

        //   -----   start/end date   -----                                  *** String?
        String startDate = datePickerStart.getValue().format(dateFormatter);
        String endDate = datePickerEnd.getValue().format(dateFormatter);
        LocalDate startingDate = datePickerStart.getValue();
        LocalDate endingDate = datePickerEnd.getValue();






        //   -----------------------------   Test input data   ---------------------------------------
        System.out.println(title);
        System.out.println(description);
        System.out.println(location);
        System.out.println(contact);
        System.out.println(type);
        System.out.println(startTime);
        System.out.println(endTime);
        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(customerID);
        System.out.println(userID);

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
        try {

            //   ---------->   populate dropDownContact   <----------
            ObservableList<Contact> allContacts = DBContacts.getAllContacts();
            ObservableList<String> contactNames = FXCollections.observableArrayList();

            //  --->   LAMBDA expression #1  <---
            allContacts.forEach(contact -> contactNames.add(contact.getContactName()));

            dropDownContact.setItems(contactNames);
            dropDownContact.setVisibleRowCount(5);


            //   ---------->   populate dropDownCustomer   <----------
            ObservableList<Customer> allCustomers = DBCustomers.getAllCustomers();
            ObservableList<String> customerIDs = FXCollections.observableArrayList();

            //  --->   LAMBDA expression #2  <---
            allCustomers.forEach(customer -> customerIDs.add(String.valueOf(customer.getCustomerID())));

            dropDownCustomer.setItems(customerIDs);
            dropDownCustomer.setVisibleRowCount(5);


            //   ---------->   populate dropDownUser   <----------
            ObservableList<User> allUsers = DBUsers.getAllUsers();
            ObservableList<String> userIDs = FXCollections.observableArrayList();

            //  --->   LAMBDA expression #3  <---
            allUsers.forEach(user -> userIDs.add(String.valueOf(user.getUserID())));

            dropDownUser.setItems(userIDs);
            dropDownUser.setVisibleRowCount(5);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
