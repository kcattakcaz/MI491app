package com.jaghory.mi491app;

import java.util.Map;

/**
 * Created by zach on 11/19/15.
 */
public class User {

    private String username;
    private String displayName;
    private String phoneNumber;
    private Map<String, String> conversations; //only filled for the user that is signed in

    public User(){

    }


    public User(String username, String displayName, String phoneNumber) {
        this.username = username;
        this.displayName = displayName;
        this.phoneNumber = phoneNumber;
    }

    public Map<String, String> getConversations() {
        return conversations;
    }

    public void setConversations(Map<String, String> conversations) {
        this.conversations = conversations;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
