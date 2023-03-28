package com.company;

import helper.JDBC;

public class Main {

    public static void main(String[] args) {

        JDBC.openConnection();
        JDBC.closeConnection();
    }
}
