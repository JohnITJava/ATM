package com.griddynamics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Starting class launch javafx application.
 */
public class Main extends Application {

    /**
     * Loader get specify description from auth.fxml that describe
     * what functional elements will be located on it's level of layer
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/auth.fxml"));

        Parent root = (Pane) loader.load();
        root.setId("pane");
        root.getStylesheets().addAll(this.getClass().getResource("/stylesheet.css").toExternalForm());

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("GridATM");
        primaryStage.getIcons().add(new Image("/atm.jpg"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


