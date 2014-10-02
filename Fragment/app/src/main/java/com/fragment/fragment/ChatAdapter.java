package com.fragment.fragment;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 9/11/14.
 */
public class ChatAdapter extends ArrayAdapter<String> {
    //private List<Chat> chats = new ArrayList<Chat>();
    //private  int resource;
    //private Context context;

    public ChatAdapter(Context context, int resource, List<String> chats) {
        super(context, resource, chats);
        //super(context, R.layout.chat_item);
        //this.context = context;
        //this.resource = resource;
    }
  /*
    public void addChats(List<Chat> newChats){
        this.chats.addAll(newChats);
        notifyDataSetChanged();
    }

    public void addChat(Chat chat){
        this.chats.add(chat);
        notifyDataSetChanged();
    }
**/

}
