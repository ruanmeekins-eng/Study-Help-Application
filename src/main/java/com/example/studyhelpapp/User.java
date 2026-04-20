package com.example.studyhelpapp;

import java.util.ArrayList;

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
