package com.Telgram.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * User class \n
 *  properties are name , username , profilePicture \n
 *  each user has contactlist which users inside are distinguished with their usernames
 *
 */
public class User implements Serializable {
    public User() { }
    public User(String u) {
        this();
        this.Username = u;
    }
    public  User(String u,String n){
        this(u);
        this.Name=n;
    }

    /**
     *Temporary created Users use this constructor
     */
    public User (String n,String u,String p){
        this(u,n);
        this.Password = p;
    }

    public boolean OnlineStatus=false;
    public String Name;
    public String Username;
    public String Password;
    public String Bio;
    //Profile Pic

    public HashSet<String> Contacts = new HashSet<>();
    /**
     * String -> User's username
     */
    public HashMap<String, PM> ChatLog = new HashMap<>();

}
