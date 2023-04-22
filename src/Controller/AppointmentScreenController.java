package Controller;

import DAO.DBAppointments;
import DAO.DBContacts;
import DAO.DBCountries;
import Models.Appointment;
import Models.Contact;
import Models.Country;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is a Controller template.
 *
 * Controls the Appointment screen of the app.
 */
public class AppointmentScreenController implements Initializable {

    Stage stage;
    Parent scene;
    static Appointment selectedAppointment;


    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<?, ?> columnAppointmentID;

    @FXML
    private TableColumn<?, ?> columnContact;

    @FXML
    private TableColumn<?, ?> columnCustomer;

    @FXML
    private TableColumn<?, ?> columnDescription;

    @FXML
    private TableColumn<?, ?> columnEnd;

    @FXML
    private TableColumn<?, ?> columnLocation;

    @FXML
    private TableColumn<?, ?> columnStart;

    @FXML
    private TableColumn<?, ?> columnTitle;

    @FXML
    private TableColumn<?, ?> columnType;

    @FXML
    private TableColumn<?, ?> columnUser;

    @FXML
    private ComboBox<String> dropDownContact;

    @FXML
    private ComboBox<String> dropDownTime;

    @FXML
    private Button applyButton;

    @FXML
    private Button mainMenuButton;

    @FXML
    private Button addAppointmentButton;

    @FXML
    private Button modifyAppointmentButton;

    @FXML
    private Button deleteAppointmentButton;

//    @FXML
//    void onActionShowAll(ActionEvent event) {
//        appointmentTableView.setItems(DBAppointments.getAllAppointments());
//    }

    @FXML
    void onActionApplyFilters(ActionEvent event) {
        String timeFilter = dropDownTime.getValue();
        String contactFilter = dropDownContact.getValue();

        //  ----------------   test drop down inputs  -----------------------
//        System.out.println(timeFilter);
//        System.out.println(contactFilter);

        appointmentTableView.setItems(DBAppointments.getFilteredAppointments(timeFilter, contactFilter));

    }

    @FXML
    void onActionResetFilter(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();

    }

    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();

    }

    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException
    {
        System.out.println("Add appointment selected");


        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();

    }

    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException
    {
        System.out.println("Modify appointment selected");

        selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

        //   confirm there is an appointment selected
        if (selectedAppointment == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select an Appointment");
            alert.showAndWait();
            return;
        }

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/ModifyAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();

    }

    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws IOException
    {
        System.out.println("Delete appointment selected");

        //   confirm there is an appointment selected
        if (selectedAppointment == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select an Appointment");
            alert.showAndWait();
            return;
        }

        //   code to delete appointment

        System.out.println("Deleting appointment with " + selectedAppointment.getCustomerName(selectedAppointment.getCustomerID()));
    }

    /**
     * Initializes the Appointment screen
     *
     * @param url the location
     * @param resourceBundle the resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // initialize screen
        try {
            ObservableList<Contact> allContacts = DBContacts.getAllContacts();
            ObservableList<String> contactNames = FXCollections.observableArrayList();

            //  --->   LAMBDA expression #1  <---
            allContacts.forEach(contact -> contactNames.add(contact.getContactName()));
            dropDownContact.setItems(contactNames);
            dropDownContact.setVisibleRowCount(5);

            //  --->   Populate dropDownTime  <---
            ObservableList<String> timeFilters = FXCollections.observableArrayList();
//            timeFilters.add("All");
            timeFilters.add("Current Week");
            timeFilters.add("Current Month");
            dropDownTime.setItems(timeFilters);

            //  --->   Populate appointmentTableView and columns  <---
            appointmentTableView.setItems(DBAppointments.getAllAppointments());

            columnAppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            columnTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            columnDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            columnLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            columnType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            columnStart.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
            columnEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
            columnCustomer.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            columnUser.setCellValueFactory(new PropertyValueFactory<>("userID"));
            columnContact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}
