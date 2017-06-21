package com.example.varun.finalproject;

import java.util.Date;

/**
 * Created by varun on 10-06-2017.
 */

public class ChatMessage {


    private String mText;
    private String mUserName;
    private long mTime;

    public ChatMessage(String text, String userName) {
        this.mText = text;
        this.mUserName = userName;

        mTime = new Date().getTime();
    }

    public ChatMessage() {
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }
}

