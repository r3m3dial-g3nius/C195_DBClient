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
        System.out.println("Adding new appointment");

        boolean isNewAppointment = true;

        DateTimeFormatter hourMinFormatter = DateTimeFormatter.ofPattern("HH:mm");      //  this is in convertStringTimeDate2UTCTimeStamp
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd");

        String title = textFieldTitle.getText();
        String description = textFieldDescription.getText();
        String location = textFieldLocation.getText();
        String contact = dropDownContact.getValue();
        String type = textFieldType.getText();
        int customerID = Integer.parseInt(dropDownCustomer.getValue());
        int userID = Integer.parseInt(dropDownUser.getValue());

        //  get customer from database
        Customer customer = DBCustomers.getCustomer(customerID);

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

        //   >>---------->  start/end time   <----------<<                                  *** String?
        String startTime = dropDownStartTime.getValue();
        String endTime = dropDownEndTime.getValue();

        //   >>---------->   start/end date   <----------<<                                  *** String?
        String startDate = datePickerStart.getValue().format(dateFormatter);
        String endDate = datePickerEnd.getValue().format(dateFormatter);

        //   >>---------->   convert to Timestamp   <----------<<
        Timestamp startTS = TimeTraveller.convertStringTimeDate2TimeStamp(startTime, startDate);
        Timestamp endTS = TimeTraveller.convertStringTimeDate2TimeStamp(endTime, endDate);

        //   -----------------------------   Test input data   ---------------------------------------
//        System.out.println(title);
//        System.out.println(description);
//        System.out.println(location);
//        System.out.println(contactID);
//        System.out.println(type);
//        System.out.println(startTime);
//        System.out.println(endTime);
//        System.out.println(startDate);
//        System.out.println(endDate);
//        System.out.println(customerID);
//        System.out.println(userID);


        //   >>---------->   convert Timestamp to LDT  <----------<<
        LocalDateTime userRequestedStartDT = startTS.toLocalDateTime();
        LocalDateTime userRequestedEndDT = endTS.toLocalDateTime();

        //   >>---------->   attach local time zone to ^^^ variables  <----------<<
        userRequestedStartDT = TimeTraveller.attachLocalTimeZone(userRequestedStartDT);
        userRequestedEndDT = TimeTraveller.attachLocalTimeZone(userRequestedEndDT);

        //  >>>----->   Confirm appointment start time is not before now()   <-----<<<
        if (startTS.before(Timestamp.valueOf(LocalDateTime.now())))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INVALID ENTRY");
            alert.setContentText("Chronological error. Appointment cannot be scheduled before current date/time.");
            alert.showAndWait();
            return;
        }

        //  >>>----->   Confirm end time is not before start time   <-----<<<
        else if (endTS.before(startTS) || endTS.equals(startTS))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INVALID ENTRY");
            alert.setContentText("Chronological error. End date/time must be after start date/time.");
            alert.showAndWait();
            return;
        }

        //  >>>----->   Confirm appointment time values are valid and within business hours   <-----<<<
        //  >>>----->   NOTE - This should never execute due to comboBoxes only allowing valid time entry   <-----<<<
        else if (!TimeTraveller.inBusinessHours(userRequestedStartDT, userRequestedEndDT))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INVALID ENTRY");
            alert.setContentText("Business hours are 8:00 AM EST to 10:00 PM EST.");
            alert.showAndWait();
            return;
        }

        //  >>>----->   Confirm appointment time values are Monday - Friday   <-----<<<
        //  >>>----->   Per instructor, business is operational 7 days/week   <-----<<<
//        else if (!TimeTraveller.isMondayThruFriday(userRequestedStartDT, userRequestedEndDT))
//        {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("INVALID ENTRY");
//            alert.setContentText("Business week is Monday Through Friday.");
//            alert.showAndWait();
//            return;
//        }

        //  >>>----->   Confirm proposed appointment times do not overlap existing customer appointments   <-----<<<
        else if (TimeTraveller.isOverlappingTimes(isNewAppointment, customer, userRequestedStartDT, userRequestedEndDT) == 1)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SCHEDULING CONFLICT");
            alert.setContentText("Requested appointment start time overlaps existing appointment.");
            alert.showAndWait();
            return;
        }
        else if (TimeTraveller.isOverlappingTimes(isNewAppointment, customer, userRequestedStartDT, userRequestedEndDT) == 2)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SCHEDULING CONFLICT");
            alert.setContentText("Requested appointment end time overlaps existing appointment.");
            alert.showAndWait();
            return;
        }
        else if (TimeTraveller.isOverlappingTimes(isNewAppointment, customer, userRequestedStartDT, userRequestedEndDT) == 3)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SCHEDULING CONFLICT");
            alert.setContentText("Appointment already scheduled between the requested start/end time.");
            alert.showAndWait();
            return;
        }

        //   >>----->   no overlapping appointments found   <-----<<
        else if (TimeTraveller.isOverlappingTimes(isNewAppointment, customer, userRequestedStartDT, userRequestedEndDT) == 4)
        {
            System.out.println("No chronological errors or scheduling conflicts detected. Adding appointment.");

            //  SQL Cols for reference VVV
            //  Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID

            //   >>----->   ADD the new appointment to database (add values to database)   <-----<<
            DBAppointments.addNewAppointment(title, description, location, type, startTS, endTS, customerID, userID, contactID);

            //  >>----->   reload screen after adding new appointment      <-----<<
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/Views/Appointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();                 //  ----------------   Center Screen
            stage.show();
        }

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

            //   >>---------->   populate dropDownContact   <----------
            ObservableList<Contact> allContacts = DBContacts.getAllContacts();
            ObservableList<String> contactNames = FXCollections.observableArrayList();

            //  >>---------->   LAMBDA expression #1  <----------<<
            allContacts.forEach(contact -> contactNames.add(contact.getContactName()));

            dropDownContact.setItems(contactNames);
            dropDownContact.setVisibleRowCount(5);


            //   >>---------->   populate dropDownCustomer   <----------<<
            ObservableList<Customer> allCustomers = DBCustomers.getAllCustomers();
            ObservableList<String> customerIDs = FXCollections.observableArrayList();

            //  >>-------->   LAMBDA expression #2  <--------<<
            allCustomers.forEach(customer -> customerIDs.add(String.valueOf(customer.getCustomerID())));

            dropDownCustomer.setItems(customerIDs);
            dropDownCustomer.setVisibleRowCount(5);


            //   >>---------->   populate dropDownUser   <----------<<
            ObservableList<User> allUsers = DBUsers.getAllUsers();
            ObservableList<String> userIDs = FXCollections.observableArrayList();

            //  >>-------->   LAMBDA expression #3  <--------<<
            allUsers.forEach(user -> userIDs.add(String.valueOf(user.getUserID())));

            dropDownUser.setItems(userIDs);
            dropDownUser.setVisibleRowCount(5);


            //   >>---------->   populate dropDownStart   <----------<<
            //   >>---------->   appointment time range is 8am to 10pm EST   <----------<<
            ObservableList<String> startTimes = FXCollections.observableArrayList();

            LocalDateTime earliestStartEST = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0));     // ---   8:00 am earliest start time
            earliestStartEST = TimeTraveller.attachESTTimeZone(earliestStartEST);                                   // ---   set timezone to EST

            LocalDateTime earliestStartLocalTime = TimeTraveller.convertESTToLocalTimeZone(earliestStartEST);       // ---   set variable to LDT equivalent

            LocalDateTime latestStartEST = LocalDateTime.of(LocalDate.now(), LocalTime.of(21, 45));     // ---   9:45 pm latest start time
            latestStartEST = TimeTraveller.attachESTTimeZone(latestStartEST);                                       // ---   set timezone to EST

            LocalDateTime latestStartLocalTime = TimeTraveller.convertESTToLocalTimeZone(latestStartEST);           // ---   set variable to LDT equivalent

            //   >>---------->  add start times to list   <----------<<
            while (earliestStartLocalTime.isBefore(latestStartLocalTime.plusMinutes(1)))
            {
                startTimes.add(earliestStartLocalTime.toLocalTime().toString());
                earliestStartLocalTime = earliestStartLocalTime.plusMinutes(15);
            }

            //   >>---------->  set items in dropdown start time combobox   <----------<<
            dropDownStartTime.setItems(startTimes);


            //   >>---------->   populate dropDownEnd   <----------<<
            //   >>---------->   appointment time range is 8am to 10pm EST   <----------<<
            ObservableList<String> endTimes = FXCollections.observableArrayList();

            LocalDateTime earliestEndEST = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 15));      // ---   8:15 am latest start time
            earliestEndEST = TimeTraveller.attachESTTimeZone(earliestEndEST);                                       // ---   set timezone to EST

            LocalDateTime earliestEndLocalTime = TimeTraveller.convertESTToLocalTimeZone(earliestEndEST);           // ---   set variable to local time equivalent

            LocalDateTime latestEndEST = LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 0));        // ---   10:00 pm latest end time
            latestEndEST = TimeTraveller.attachESTTimeZone(latestEndEST);                                           // ---   set timezone to EST

            LocalDateTime latestEndLocalTime = TimeTraveller.convertESTToLocalTimeZone(latestEndEST);               // ---   set variable to local time equivalent

            //   >>---------->  add end times to list   <----------<<
            while (earliestEndLocalTime.isBefore(latestEndLocalTime.plusMinutes(1)))
            {
                endTimes.add(earliestEndLocalTime.toLocalTime().toString());
                earliestEndLocalTime = earliestEndLocalTime.plusMinutes(15);
            }

            //   >>---------->  set items in dropdown end time combobox   <----------<<
            dropDownEndTime.setItems(endTimes);

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
