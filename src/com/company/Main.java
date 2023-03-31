package com.company;

import DAO.DBUsers;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Helper.DBConnection;

import DAO.DBCountries;                     //  ------------------   TEST


public class Main extends Application {

    public static void main(String[] args)
    {
        DBConnection.openConnection();      //  ------------------   start
        DBCountries.checkDateConversions(); //  ------------------   TEST
        System.out.println(DBUsers.getAllUsers()); //  ------------------   TEST
        System.out.println();
        // launch(args);
        DBConnection.closeConnection();     //  ------------------   end
    }


    /** Set up initial app stage
     *
     * @param stage This is the stage to set.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../Views/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Client Scheduler");          //   --------------------   CHANGE THIS LATER  ----------------
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
