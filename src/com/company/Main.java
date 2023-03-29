package com.company;

import helper.DBConnection;

public class Main {

    public static void main(String[] args) {

        DBConnection.openConnection();

        DBConnection.closeConnection();
    }
}
