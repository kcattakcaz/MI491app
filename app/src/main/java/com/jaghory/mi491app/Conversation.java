package com.jaghory.mi491app;

import java.util.ArrayList;

/**
 * Created by zach on 11/19/15.
 */
public class Conversation {
    private String id;
    private ArrayList<User> users;
    private ArrayList<Message> messages;

    public Conversation(){

    }

    public Message getByPosition(int id){
        return messages.get(id);
    }

    public String getTitle(){
        String title = "";
        for (int i = 0; i <users.size() ; i++) {
            title+=users.get(i).getDisplayName() + ",";
        }
        return title;
    }

    public Conversation(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsers(ArrayList<User> newUsers){
        this.users = newUsers;
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

