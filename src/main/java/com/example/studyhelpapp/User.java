package com.example.studyhelpapp;


/***
 * Object that represents a user that contains
 * a username and password.
 */
public class User {
    //Attributes
    private String username;
    private String password;
    private int point;


    //Constructor
    public User(String username, String password, int point){
        this.username = username;
        this.password = password;
        this.point = point;

    }

    //Accessors
    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public int getPoint(){return point;}


    //Mutators
    public void setUsername(String username){this.username = username;}
    public void setPassword(String password){this.password = password;}
    public void setPoint(int point){this.point = point;}

}
