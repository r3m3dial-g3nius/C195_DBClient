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
import java.sql.SQLException;
import java.util.Optional;
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

    /**
     * Applies user selected filters to data displayed in tableview
     * @param event
     */
    @FXML
    void onActionApplyFilters(ActionEvent event) {
        String timeFilter = dropDownTime.getValue();
        String contactFilter = dropDownContact.getValue();

        //  ----------------   test drop down inputs  -----------------------
//        System.out.println(timeFilter);
//        System.out.println(contactFilter);

        appointmentTableView.setItems(DBAppointments.getFilteredAppointments(timeFilter, contactFilter));

    }

    /**
     * fires when user presses Reset button...reloads the Appointment screen without filters
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionResetFilter(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();

    }

    /**
     * fires when user presses Main Menu button, returns user to Main Menu
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
     * fires when user selects Add button; loads Add Appointment screen
     * @param event
     * @throws IOException
     */
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

    /**
     * fires when user presses Modify button; loads Modify Appointment screen
     * @param event
     * @throws IOException
     */
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

    /**
     * fires when user pressed Delete button; deletes selected Appointment from appointments table in database
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws IOException, SQLException {
        System.out.println("Delete appointment selected");
        selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();


        try {
            //   confirm there is an appointment selected
            if (selectedAppointment == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Please select an Appointment");
                alert.showAndWait();
                return;
            }

            //   confirm deletion
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("CONFIRM DELETE");
                alert.setContentText("Are you sure you want to delete?");
                Optional<ButtonType> confirmation = alert.showAndWait();

                if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                    DBAppointments.deleteAppointment(selectedAppointment.getAppointmentID());

                    ObservableList<Appointment> allAppointments = DBAppointments.getAllAppointments();
                    appointmentTableView.setItems(allAppointments);

                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("");
                    alert2.setContentText("Appointment ID #" + selectedAppointment.getAppointmentID() +
                            " (" + selectedAppointment.getAppointmentType() + ") with " +
                            selectedAppointment.getCustomerName(selectedAppointment.getCustomerID()) + " has been deleted.");
                    alert2.showAndWait();
                    return;

                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

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
