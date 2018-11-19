package com.griddynamics.controller;

import com.griddynamics.sql.SQLHandler;
import com.griddynamics.entity.Admin;
import com.griddynamics.entity.Human;
import com.griddynamics.entity.User;
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

/**
 * Controller describes functions of interface and interaction with human on common interface.
 * It runs differ gui interfaces depending of human's role, when human is logging in.
 */
public class AuthController implements Initializable {
    @FXML
    VBox mainBox;

    @FXML
    TextField logF;

    @FXML
    Label result;

    @FXML
    Button regBut, logBut;

    @FXML
    PasswordField pasF;

    private String name;
    private String pass;

    private Human human;

    private static boolean authorized;

    public static void setAuthorized(boolean logIn) {
        authorized = logIn;
    }

    private Stage mStage;


    public Human getHuman() {
        return human;
    }

    public void setStage(Stage mStage) {
        this.mStage = mStage;
    }

    public AuthController() {
    }

    /**
     * Pre-initialization of various methods at boot time of this controller.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> ((Stage) mainBox.getScene().getWindow()).setOnCloseRequest(t -> {
            Platform.exit();
        }));


        regBut.setDisable(true);
        result.setWrapText(true);
    }


    /**
     * Method by login button getting text's from login field and password field,
     * runs checking combination in DB. In success we get Human object to determinate role
     * and further switching to right gui interface.
     */
    public void tryToLogin() throws IOException {

        name = logF.getText();
        pass = pasF.getText();
        logF.clear();
        pasF.clear();

        if (SQLHandler.tryToLogin(name, pass)) {
            authorized = true;
            human = SQLHandler.getHumanFromDB(name, pass);
            result.setText("Success");
        } else {
            result.setText("Wrong combination of login and password");
        }

        if (authorized) {
            if (human.role == Human.Role.User) {
                changeSceneToUser();
            } else {
                changeSceneToAdmin();
            }
        }

    }

    /**
     * Method runs user gui interface, loads it's controller and parameters,
     * throws current Human object to next layer.
     */
    public void changeSceneToUser() throws IOException {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/userInterface.fxml"));

        root = (Pane) loader.load();
        root.setId("pane");
        root.getStylesheets().addAll(this.getClass().getResource("/stylesheet.css").toExternalForm());

        Scene scene = new Scene(root, 400, 400);
        ((Stage) mainBox.getScene().getWindow()).setScene(scene);

        UserInterfaceController userInterfaceController = loader.getController();
        userInterfaceController.setUser((User)human);
    }

    /**
     * Method runs admin gui interface, loads it's controller and parameters,
     * throws current Human object to next layer.
     */
    public void changeSceneToAdmin() throws IOException {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminInterface.fxml"));

        root = (Pane) loader.load();
        root.setId("adminPane");
        root.getStylesheets().addAll(this.getClass().getResource("/stylesheet.css").toExternalForm());

        Scene scene = new Scene(root, 400, 400);
        ((Stage) mainBox.getScene().getWindow()).setScene(scene);

        AdminInterfaceController adminInterfaceController = loader.getController();
        adminInterfaceController.setAdmin((Admin) human);

    }

}
