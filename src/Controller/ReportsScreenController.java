package Controller;

import DAO.DBAppointments;
import DAO.DBContacts;
import Models.Appointment;
import Models.Contact;
import Models.ReportByDivision;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is controls the Reports Screen.
 *
 * Controls the Reports screen of the app.
 */
public class ReportsScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<String> appointmentsMonthTypeTableView;

    @FXML
    private TableColumn<?, ?> colMonth;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colCount;

    @FXML
    private TableView<ReportByDivision> customerLocationsTableView;

    @FXML
    private TableColumn<?, ?> colDSP;

    @FXML
    private TableColumn<?, ?> colDSPCount;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<?, ?> columnAppointmentID;

    @FXML
    private TableColumn<?, ?> columnCustomer;

    @FXML
    private TableColumn<?, ?> columnDescription;

    @FXML
    private TableColumn<?, ?> columnEnd;

    @FXML
    private TableColumn<?, ?> columnStart;

    @FXML
    private TableColumn<?, ?> columnTitle;

    @FXML
    private TableColumn<?, ?> columnType;

    @FXML
    private ComboBox<String> dropDownContact;

    @FXML
    private Button mainMenuButton;

    @FXML
    void onActionFilterContacts(ActionEvent event) {
        String contactFilter = dropDownContact.getValue();

        if (contactFilter.equals("All Contacts"))
        {
            appointmentTableView.setItems(DBAppointments.getAllAppointments());
        }

        else
        {
            appointmentTableView.setItems(DBAppointments.getFilteredAppointments(null, contactFilter));
        }

    }

    /**
     * Navigates user back to Main Menu Screen
     * @param event Clicking the Main Menu button
     * @throws IOException
     */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();                 //  ----------------   Center Screen
        stage.show();

    }


    /**
     * Initializes the Reports screen
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
            ObservableList<Appointment> allAppointments = DBAppointments.getAllAppointments();

            ObservableList<String> contactNames = FXCollections.observableArrayList();
            ObservableList<String> appointmentMonths = FXCollections.observableArrayList();

            //   --->   Create All Contacts option in contact filter drop down   <---
            contactNames.add("All Contacts");

            //  --->   LAMBDA expression #1  <---
            allContacts.forEach(contact -> contactNames.add(contact.getContactName()));

            dropDownContact.setItems(contactNames);
            dropDownContact.setValue("All Contacts");
            dropDownContact.setVisibleRowCount(5);

            //  --->   Populate appointmentTableView and columns  <---
            appointmentTableView.setItems(DBAppointments.getAllAppointments());

            columnAppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            columnTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            columnDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            columnType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            columnStart.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
            columnEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
            columnCustomer.setCellValueFactory(new PropertyValueFactory<>("customerID"));

            //  --->   Populate appointmentsMonthTypeTableView and columns  <---
//            appointmentsMonthTypeTableView.setItems(DBAppointments.***);

//            colMonth.setCellValueFactory(new PropertyValueFactory<>(""));
//            colType.setCellValueFactory(new PropertyValueFactory<>(""));
//            colCount.setCellValueFactory(new PropertyValueFactory<>(""));


            //  --->   Populate customerLocationsTableView and columns  <---
            customerLocationsTableView.setItems(DBAppointments.getCustomersByDivision());

            colDSP.setCellValueFactory(new PropertyValueFactory<>("division"));
            colDSPCount.setCellValueFactory(new PropertyValueFactory<>("count"));




        }

        catch (Exception e)
        {
            e.printStackTrace();
        }





        }



}
