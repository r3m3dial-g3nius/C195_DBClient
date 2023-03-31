package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Helper.DBConnection;

import DAO.DBCountries;                     //  ------------------   TEST
import DAO.DBUsers;


/**
 * This is the Main class of the app.
 */
public class Main extends Application {

    /**
     * Opens and closes database connection
     *
     * @param args The arguments
     */
    public static void main(String[] args)
    {
        DBConnection.openConnection();      //  ------------------   start


        DBCountries.checkDateConversions(); //  ------------------   TEST
        System.out.println(DBUsers.getAllUsers()); //  ------------------   TEST
        System.out.println();


        // launch(args);
        DBConnection.closeConnection();     //  ------------------   end
    }


    /**
     * Set up initial app stage
     *
     * @param stage The stage to be set.
     * @throws Exception The exception thats thrown if there's an error.
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
