package Controller;

import DAO.DBAppointments;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
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

    /**
     * fires when user selects customer id from dropDownCustomer, updates textFieldCustomerName with name of customer
     * @param actionEvent
     */
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

    /**
     * fires when user selects user id from dropDownUser, updates textFieldUserName with name of user
     * @param actionEvent
     */
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


    /**
     * fires when user presses Add button, pulls new appointment data from user input, calls
     * DBAppointments.addNewAppointment to insert appointment into appointments table in database
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionAddNewAppointment(ActionEvent event) throws SQLException, IOException {
        System.out.println("Adding new customer");

        DateTimeFormatter hourMinFormatter = DateTimeFormatter.ofPattern("HH:mm");      //  this is in convertStringTimeDate2UTCTimeStamp
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd");

        String title = textFieldTitle.getText();
        String description = textFieldDescription.getText();
        String location = textFieldLocation.getText();
        String contact = dropDownContact.getValue();
        String type = textFieldType.getText();
        int customerID = Integer.parseInt(dropDownCustomer.getValue());
        int userID = Integer.parseInt(dropDownUser.getValue());

        //  get contact ID from contact string
        ObservableList<Contact> allContacts = DBContacts.getAllContacts();
        int contactID = 0;

        for (Contact c : allContacts)
        {
            if (c.getContactName().equals(contact))
            {
                contactID = c.getContactID();
            }
        }

        //   -----   start/end time   -----                                  *** String?
        String startTime = dropDownStartTime.getValue();
        String endTime = dropDownEndTime.getValue();

        //   -----   start/end date   -----                                  *** String?
        String startDate = datePickerStart.getValue().format(dateFormatter);
        String endDate = datePickerEnd.getValue().format(dateFormatter);

        //   -----   convert to Timestamp   -----
        Timestamp startTS = TimeTraveller.convertStringTimeDate2TimeStamp(startTime, startDate);
        Timestamp endTS = TimeTraveller.convertStringTimeDate2TimeStamp(endTime, endDate);

        //   -----------------------------   Test input data   ---------------------------------------
        System.out.println(title);
        System.out.println(description);
        System.out.println(location);
        System.out.println(contactID);
        System.out.println(type);
        System.out.println(startTime);
        System.out.println(endTime);
        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(customerID);
        System.out.println(userID);

        //  SQL Cols for reference VVV
        //  Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID

        DBAppointments.addNewAppointment(title, description, location, type, startTS, endTS, customerID, userID, contactID);

        //  reload screen after adding new appointment
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();

    }

    /**
     * fires when Cancel button is pressed, returns user to Appointments screen
     *
     * @param event
     * @throws IOException
     */
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
     * lambda #1 - populates Observable list contactNames with String values of contact name
     * lambda #2 - populates Observable list customerIDs with String values of customer ID numbers.
     * lambda #3 - populates Observable list userIDs with String values of user ID numbers.
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


            //   ---------->   populate dropDownStart   <----------
            //   ---------->   appointment time range is 8am to 10pm EST   <----------
            ObservableList<String> startTimes = FXCollections.observableArrayList();

            LocalDateTime earliestStartEST = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0));   // ---   8:00 am
            LocalDateTime appointmentEarliestStart = TimeTraveller.timeZoneFormatter(earliestStartEST, ZoneId.of("America/New_York"));

            LocalDateTime latestStartEST = LocalDateTime.of(LocalDate.now(), LocalTime.of(21, 45));   // ---   9:45 pm
            LocalDateTime appointmentLatestStart = TimeTraveller.timeZoneFormatter(latestStartEST, ZoneId.of("America/New_York"));

            while (appointmentEarliestStart.isBefore(appointmentLatestStart.plusMinutes(1)))
            {
                startTimes.add(appointmentEarliestStart.toLocalTime().toString());
                appointmentEarliestStart = appointmentEarliestStart.plusMinutes(15);
            }

            dropDownStartTime.setItems(startTimes);


            //   ---------->   populate dropDownEnd   <----------
            //   ---------->   appointment time range is 8am to 10pm EST   <----------
            ObservableList<String> endTimes = FXCollections.observableArrayList();

            LocalDateTime earliestEndEST = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 15));   // ---   8:15 am
            LocalDateTime appointmentEarliestEnd = TimeTraveller.timeZoneFormatter(earliestEndEST, ZoneId.of("America/New_York"));

            LocalDateTime latestEndEST = LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 00));   // ---   9:45 pm
            LocalDateTime appointmentLatestEnd = TimeTraveller.timeZoneFormatter(latestEndEST, ZoneId.of("America/New_York"));

            while (appointmentEarliestEnd.isBefore(appointmentLatestEnd.plusMinutes(1)))
            {
                endTimes.add(appointmentEarliestEnd.toLocalTime().toString());
                appointmentEarliestEnd = appointmentEarliestEnd.plusMinutes(15);
            }

            dropDownEndTime.setItems(endTimes);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
