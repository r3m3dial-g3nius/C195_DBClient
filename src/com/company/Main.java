package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import DAO.DBCountries;
import Helper.DBConnection;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainscreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("C195 PA");          //   --------------------   CHANGE THIS LATER  ----------------
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    public static void main(String[] args)
    {
        DBConnection.openConnection();      //  ------------------   start
        DBCountries.checkDateConversions();
        // launch(args);
        DBConnection.closeConnection();     //  ------------------   end
    }
}
