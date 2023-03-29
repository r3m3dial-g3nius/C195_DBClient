package com.company;

import Controller.Controller;
import DAO.DBCountries;
import Database.DBConnection;

public class Main {

    public static void main(String[] args)
    {
        DBConnection.openConnection();      //  ------------------   start
        DBCountries.checkDateConversions();
        // launch(args);
        DBConnection.closeConnection();     //  ------------------   end
    }
}
