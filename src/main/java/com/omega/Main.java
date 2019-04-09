package com.omega;

import com.omega.sql.SQLHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Starting class launch javafx application.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/desktop.fxml"));
        Parent root = (Pane) loader.load();
        root.setId("pane");
        root.getStylesheets().addAll(this.getClass().getResource("/stylesheet.css").toExternalForm());
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ТелеМедицина");
        primaryStage.show();
    }


    public static void main(String[] args) {
        SQLHandler.getInstance();
        launch(args);
    }
}


