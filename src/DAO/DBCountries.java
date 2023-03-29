package DAO;

import Database.DBConnection;
import Models.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DBCountries {

    public static ObservableList<Countries> getAllCountries() {

        ObservableList<Countries> countryList = FXCollections.observableArrayList();    // here is where you left off

        return countryList;
    }

    public static void checkDateConversion() {
        System.out.println("CREATE DATE TEST");
    }
}
