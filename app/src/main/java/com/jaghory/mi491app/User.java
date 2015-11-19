package com.jaghory.mi491app;

import java.util.ArrayList;

/**
 * Created by zach on 11/19/15.
 */
public class User {

    private String username;
    private String displayName;
    private ArrayList<Conversation> conversations; //only filled for the user that is signed in


    public User(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }

    public ArrayList<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(ArrayList<Conversation> conversations) {
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


}
