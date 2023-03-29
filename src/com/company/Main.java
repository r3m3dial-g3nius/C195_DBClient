package com.company;

import Database.DBConnection;

public class Main {

    public static void main(String[] args) {

        DBConnection.openConnection();

        DBConnection.closeConnection();
    }
}
