package com.griddynamics.controller;

import com.griddynamics.sql.SQLHandler;
import com.griddynamics.entity.Admin;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

/**
 * Controller describes functions of interface and interaction with admin on admin's interface.
 * It runs differ methods depending of admin's wishes
 */

public class AdminInterfaceController implements Initializable {
    @FXML
    VBox mainBox;

    @FXML
    Button checkBalBut, checkOperBut, logOffBut;

    @FXML
    ListView<String> clientsList;

    @FXML
    TextArea resultA;


    private ObservableList<String> clients;

    private Stage mStage;
    private Admin admin;

    public void setStage(Stage mStage) {
        this.mStage = mStage;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    /**
     * Pre-initialization of various methods at boot time of this controller
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /**
         * Correct closing application on cross button.
         */
        Platform.runLater(() -> ((Stage) mainBox.getScene().getWindow()).setOnCloseRequest(t -> {
            Platform.exit();
        }));

        /**
         * Pre-loading list of user's name on GUI
         */
        Platform.runLater(() -> {
            clients = FXCollections.observableArrayList();
            clients.addAll(admin.getAllUsersNames());
            clientsList.setItems(clients);
        });
    }

    /**
     * Method get from gui selected name and call method from admin to get user's balance
     */
    public void checkUserBal(){
        String userName = clientsList.getFocusModel().getFocusedItem();
        String balance = new DecimalFormat("#0.00").format(admin.getUserBalance(userName));
        resultA.setText(userName + " balance is " + balance);
    }

    /**
     * Method get from gui selected name and call method from admin to get last user's operations
     */
    public void checkUserOper(){
        String userName = clientsList.getFocusModel().getFocusedItem();
        resultA.setText(admin.getLastUserOper(userName).toString());
    }


    /**
     * Disconnect from DB
     * Log of from admin interface
     * And set authorization flag in false
     */
    public void logOffSystem() throws IOException {
        SQLHandler.disconnect();
        AuthController.setAuthorized(false);
        changeSceneToAuth();
    }

    /**
     * Method is using to load back starting authorization gui layer
     */
    //TO DO create single StageHandler, which will contain description of all layers
   public void changeSceneToAuth() throws IOException {
       Parent root = null;
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/auth.fxml"));
       root = (Pane) loader.load();
       root.setId("pane");

       Scene scene = new Scene(root, 400, 400);
       ((Stage) mainBox.getScene().getWindow()).setScene(scene);
    }

}