package com.example.studyhelpapp;

import java.util.ArrayList;

/***
 * Object that represents a user that contains
 * a username and password.
 */
public class User {
    //Attributes
    private String username;
    private String password;


    //Constructor
    public User(String username, String password){
        this.username = username;
        this.password = password;

    }

    //Accessors
    public String getUsername(){return username;}
    public String getPassword(){return password;}


    //Mutators
    public void setUsername(String username){this.username = username;}
    public void setPassword(String password){this.password = password;}

}
