package com.example.cybercop;

public class message {

    private String msgFrom;
    private String message;

    public message(String from, String msg)
    {
        msgFrom=from;
        message=msg;
    }

    public String getMsgFrom() {
        return msgFrom;
    }

    public String getMessage() {
        return message;
    }
}
