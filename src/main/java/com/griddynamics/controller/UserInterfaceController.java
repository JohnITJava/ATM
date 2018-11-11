package com.griddynamics.controller;

import com.griddynamics.sql.SQLHandler;
import com.griddynamics.entity.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller describes functions of interface and interaction with user on user's interface.
 * It runs differ methods depending of user's wishes
 */

public class UserInterfaceController implements Initializable {
    @FXML
    VBox mainBox;

    @FXML
    Label balanceLabel, result;

    @FXML
    TextField moneySumF;

    private Stage mStage;
    private User user;
    private String numberMatcher = "^-?\\d+$";

    public void setStage(Stage mStage) {
        this.mStage = mStage;
    }

    public void setUser(User user) {
        this.user = user;
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
         * Pre-loading user's balance on GUI
         */
        Platform.runLater((() -> balanceLabel.setText(user.checkBalance())));

        /**
         * Listener on text field, which controlled user's input in real time
         * and allowed enter just digits
         */
        moneySumF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (!newValue.matches(numberMatcher)) {
                    moneySumF.setText(oldValue);
                } else {
                    try {
                        moneySumF.setText(newValue);
                    } catch (NumberFormatException e) {
                        moneySumF.setText(oldValue);
                    }
                }
            }
        });
    }


    /**
     * Method gets text from field, converts in double, call put method from user
     * and than update info about balance on gui.
     */
    public void putMoney() {
        if (!moneySumF.getText().isEmpty()){
        if (isMoneyInputMultTo100()) {
            if (user.put(Double.parseDouble(moneySumF.getText()))) {
                balanceLabel.setText(user.checkBalance());
                result.setText("Success");
            } else result.setText("SMTH going wrong!");
        } else result.setText("Input is incorrect! Please, Enter summ that can be divide in 100");
        }
    }

    /**
     * Method gets text from field, converts in double, call take method from user
     * and than update info about balance on gui.
     */
    public void getMoney() {
        if (!moneySumF.getText().isEmpty()){
        if (isMoneyInputMultTo100()) {
            if (user.take(Double.parseDouble(moneySumF.getText()))) {
                balanceLabel.setText(user.checkBalance());
                result.setText("Success");
            } else result.setText("Not enough money");
        } else result.setText("Input is incorrect! Please, Enter summ that can be divide in 100");
        }
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
     * @return true if field can be divided in 100 without remainder of the division,
     * also it checks that input not equals 0.
     */
    public boolean isMoneyInputMultTo100() {
        if (Double.parseDouble(moneySumF.getText()) % 100 == 0 && Double.parseDouble(moneySumF.getText()) !=0) {
            return true;
        } else return false;
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
