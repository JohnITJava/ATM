package com.omega.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DesktopController implements Initializable {
    @FXML
    Pane mainBox;


    public DesktopController() {
    }

    /**
     * Pre-initialization of various methods at boot time of this controller.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> ((Stage) mainBox.getScene().getWindow()).setOnCloseRequest(t -> {
            Platform.exit();
        }));
    }
}
