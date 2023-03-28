package com.company;

import helper.JDBC;

public class Main {

    public static void main(String[] args) {

        System.out.println("added gitignore");
        JDBC.openConnection();

        JDBC.closeConnection();
    }
}
