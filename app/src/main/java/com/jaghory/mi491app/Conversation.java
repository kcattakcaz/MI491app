package com.jaghory.mi491app;

import java.util.ArrayList;

/**
 * Created by zach on 11/19/15.
 */
public class Conversation {
    private String id;
    private ArrayList<User> users;
    private ArrayList<Message> messages;

    public Conversation(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public Message getMessageById(String id){
        return null;
    }
}
