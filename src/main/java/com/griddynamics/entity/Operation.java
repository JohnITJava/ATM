package com.griddynamics.entity;

import com.griddynamics.sql.SQLHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Operation class describes behavior of it's object. It's needed to create operation in specific format
 * and temporary keep it in list in runtime.
 */

public class Operation {
    private List<String> operations = new ArrayList<>();
    private String operation;
    private DateFormat dateFormat;
    private Date date;
    private User user;

    public Operation() {
    }

    /**
     * This constructor will calling when user put or take money.
     * It saves data, time and operation in string. Then directly save formatted string in DB
     * also saving user's id.
     */
    public Operation(User user, String string) {
        this.user = user;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.operation = (dateFormat.format(date)) + " " + string;
        SQLHandler.insertOperation(user.id, operation);
    }

    /**
     * @return strings of operation in column
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < operations.size(); i++) {
            sb.append(operations.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }

    public void addOperationToList(String operation){
        operations.add(operation);
    }

}
