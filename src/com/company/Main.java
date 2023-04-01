package com.company;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import Helper.DBConnection;

import DAO.DBCountries;                     //  ------------------   TEST
import DAO.DBUsers;


/**
 * This is the Main class of the app.
 */
public class Main extends Application {

    /**
     * Set up initial app primaryStage
     *
     * @param primaryStage The primaryStage to be set.
     * @throws Exception The exception thats thrown if there's an error.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Views/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Client Scheduler");          //   --------------------   CHANGE THIS LATER  ----------------
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();                      //   center on screen
        primaryStage.show();
    }


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


        launch(args);
        DBConnection.closeConnection();     //  ------------------   end
    }

}
