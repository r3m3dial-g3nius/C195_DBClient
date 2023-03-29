package Controller;

import DAO.DBCountries;
import Models.Country;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableView dataTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    public void showMe(ActionEvent actionEvent)
    {
        ObservableList<Country> countryList = DBCountries.getAllCountries();

        for(Country c : countryList)
        {
            System.out.println("Country ID : " + c.getCountryID() + " Country Name : " + c.getCountryName());
        }
    }
}
