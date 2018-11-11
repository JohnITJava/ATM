package com.griddynamics.entity;

import com.griddynamics.sql.SQLHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Class User describes behavior of it's object and contains methods for putting/getting money,
 * and checking balance.
 */

public class User extends Human{

    //TO DO It's needed to use BigDecimal for money.
    private Double balance;
    private List<Operation> operations;

    /**
     * @param userName is unique, not null.
     * @param password is not null.
     *
     * Constructor will be calling in DB when user will trying to login in system after checking entered data.
     * These data will be keeping temporary in runtime.
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.balance = SQLHandler.getUserBalance(userName);
        this.operations = new ArrayList<>();
        this.role = Role.User;
    }

    /**
     * @param money can not be 0 or negative. It will getting from gui interface.
     * @return true if operation is success.
     *
     * Method after checking sends request in DB. Then create Operation object
     * and adds it to temporary runtime list.
     */
    public boolean put(double money){
        if (money <= 0){
            return false;
        }
        this.balance += money;
        SQLHandler.addMoney(this.userName, money);
        operations.add(new Operation(this, "was put " + money));
        return true;
    }

    /**
     * @param money can not be <=0 and it should be less current user's balance. It will getting from gui interface.
     * @return true if operation is success.
     * Method after checking sends request in DB. Then create Operation object
     * and adds it to temporary runtime list.
     */
    public boolean take(double money){
        if (money <= balance && money > 0){
            balance -= money;
            SQLHandler.substructMoney(this.userName, money);
            operations.add(new Operation(this, "was taken " + money));
            return true;
        }
        return false;
    }

    /**
     * @return user's balance from DB and brings it in specific format
     * in order to display it then in GUI correctly.
     */
    public String checkBalance(){
        String formattedDouble = new DecimalFormat("#0.00").format(SQLHandler.getUserBalance(this.userName));
        return formattedDouble;
    }

}
