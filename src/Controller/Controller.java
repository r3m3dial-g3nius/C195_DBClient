package Controller;

import DAO.DBCountries;
import Models.Country;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
import java.util.Optional;

/**
 * This class is a Controller template.
 *
 * Controls the XXXXX screen of the app.
 */
public class Controller implements Initializable {
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableView dataTable;

    /**
     * Initializes the XXXXXXX screen
     *
     * @param url the location
     * @param resourceBundle the resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // initialize screen
    }





//   -------------------------------------------   TESTING   ----------------------------------------------------
    public void showMe(ActionEvent actionEvent)
    {
        ObservableList<Country> countryList = DBCountries.getAllCountries();

        for(Country c : countryList)
        {
            System.out.println("Country ID : " + c.getCountryID() + " Country Name : " + c.getCountryName());
        }
    }
}
