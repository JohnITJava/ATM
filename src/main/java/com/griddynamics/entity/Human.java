package com.griddynamics.entity;

/**
 * Human class is abstract and describes basic behavior for it's children.
 * All children should have userName, password, role and id.
 * UserName and id are unique and not Null.
 */

public abstract class Human {
    protected String userName;
    protected String password;
    public Role role;
    protected int id;

    public enum Role{
        User(0), Admin(1);

        private int roleInt;
        Role(int roleInt) {
            this.roleInt = roleInt;
        }
    }


    /**
     * @param id is unique and setting from DB.
     */
    public void setId(int id) {
        this.id = id;
    }

}
