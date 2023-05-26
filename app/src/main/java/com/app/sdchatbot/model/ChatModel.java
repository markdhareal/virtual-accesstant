package com.app.sdchatbot.model;

public class ChatModel {

    String message;
    int sender;

    public ChatModel(String message, int sender)
    {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }
}
