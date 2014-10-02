package com.fragment.fragment;

import android.app.Activity;

/**
 * Created by user on 9/20/14.
 */
public class Chat {
    public String sent, sender, time;

    public Chat(String sender, String sent, String time){
        this.sent = sent;
        this.sender = sender;
        this.time = time;
    }

}
