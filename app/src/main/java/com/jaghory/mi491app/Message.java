package com.jaghory.mi491app;

/**
 * Created by zach on 11/19/15.
 */
public class Message {
    public enum MessageType{
        TEXT,IMAGE,FILE
    }

    private String id;
    private MessageType type; //image/file not going to be implemented (stretch goal)
    private String sender; //just the plain text username of the sender
    private Integer timestamp; //server's timestamp or something probably in UTC?
    private String content; //Just text for text, URL for image/file

    public Message(){

    }


    public Message(MessageType mType,String mId,String mSender,Integer mTimestamp,String mContent){
        this.type = mType;
        this.id = mId;
        this.sender = mSender;
        this.timestamp = mTimestamp;
        this.content = mContent;

    }


    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
