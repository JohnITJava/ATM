package com.griddynamics.entity;

import com.griddynamics.sql.SQLHandler;

import java.util.List;

/**
 * Class Admin describes behavior of it's object and contains methods for getting list of all clients,
 * getting balance of specific user, and getting last three operations of specific user.
 * All information are getting directly from DB.
 */
public class Admin extends Human {
    private Role role;

    public Admin(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.role = Role.Admin;
    }


    /**
     * @return list of all user's names from DB.
     */
    public List<String> getAllUsersNames(){
        return SQLHandler.getAllUsersNamesDB();
    }


    /**
     * @param name will getting from GUI.
     * @return balance directly from DB by name.
     */
    public double getUserBalance(String name){
        return SQLHandler.getUserBalance(name);
    }

    /**
     * @param name will getting from GUI
     * @return Operation object by name from DB.
     */
    public Operation getLastUserOper(String name){
        return SQLHandler.getLastUserOper(name);
    }



}
