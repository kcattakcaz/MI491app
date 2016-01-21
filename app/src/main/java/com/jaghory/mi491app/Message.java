package com.jaghory.mi491app;

/**
 * Created by zach on 11/19/15.
 */
public class Message {
    private String type; // text / image / file
    private String sender; //just the plain text username of the sender
    private Integer timestamp; //server's timestamp or something probably in UTC?
    private String content; //Just text for text, URL for image/file

    public Message(){

    }


    public Message(String mType,String mSender,Integer mTimestamp,String mContent){
        this.type = mType;
        this.sender = mSender;
        this.timestamp = mTimestamp;
        this.content = mContent;

    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
