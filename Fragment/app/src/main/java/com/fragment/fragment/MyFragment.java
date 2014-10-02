package com.fragment.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.fragment.fragment.DB.HandlerDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 9/11/14.
 */
public class MyFragment extends Fragment {

    public MyFragment() {
    }

    public ArrayList<String> chatToString(ArrayList<Chat> allEntries){
        ArrayList<String> arrayStrings = new ArrayList<String>();
        int size = allEntries.size();
        for (int i=0; i<size; i++){
            arrayStrings.add(allEntries.get(i).sender+ ": " + allEntries.get(i).sent);
        }
        return arrayStrings;
    }


    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final HandlerDatabase handler = new HandlerDatabase(getActivity());
        handler.open();
        final View rootView = inflater.inflate(R.layout.fragment, container, false);
        final Button myButton = (Button) rootView.findViewById(R.id.new_but);
        final ListView myListView = (ListView) rootView.findViewById(R.id.my_list_view);
        //final ArrayList<Chat> allChats = handler.getAllMessages();
        //UserTracker.all_messages = chatToString(allChats);
        UserTracker.all_messages = new ArrayList<String>();
        final HashMap<String, String> dataSend= new HashMap<String, String>();
        final Firebase firebase = new Firebase("https://mobileproto2014.firebaseio.com/chatroom/0");
        //final Firebase firebase = new Firebase("https://radiant-inferno-7595.firebaseio.com/chatapp");

        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> newPost = (Map<String, String>) dataSnapshot.getValue();
                String user = newPost.get("name");
                String message = newPost.get("message");
                String timestamp = newPost.get("timestamp");
                if (!user.equals(UserTracker.username)) {
                    //handler.addMessageToDatabase(user, message, timestamp);
                    UserTracker.all_messages.add(user + " (" + timestamp +"): " + message);
                    ChatAdapter adaptList = new ChatAdapter(getActivity(), R.layout.chat_item, UserTracker.all_messages);
                    myListView.setAdapter(adaptList);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        ChatAdapter chatListAdapt = new ChatAdapter(getActivity(), R.layout.chat_item, UserTracker.all_messages);
        myListView.setAdapter(chatListAdapt);
        //myListView.setSelection(chatListAdapt.getCount() - 1);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editChat(position, inflater, container);
            }
        });

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newMessage = (EditText) rootView.findViewById(R.id.add_text);
                String newText = newMessage.getText().toString();
                long milliSeconds = System.currentTimeMillis();
                SimpleDateFormat dater = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(milliSeconds);
                String date = dater.format(calendar.getTime());
                if (newText.equals("")){
                    return;
                }
                dataSend.put("name", UserTracker.username);
                dataSend.put("message", newText);
                dataSend.put("timestamp", date);
                firebase.push().setValue(dataSend);
                //handler.addMessageToDatabase(UserTracker.username, newText, date);
                UserTracker.all_messages.add(UserTracker.username + " (" + date + "): " + newText);
                newMessage.setText("");
                ChatAdapter chatListAdapt = new ChatAdapter(getActivity(), R.layout.chat_item, UserTracker.all_messages);
                myListView.setAdapter(chatListAdapt);
                //myListView.setSelection(chatListAdapt.getCount() - 1);
            }
        });

        myListView.setAdapter(new ChatAdapter(getActivity(), R.layout.chat_item, UserTracker.all_messages));
        return rootView;
    }

    public void editChat (final int id, LayoutInflater inflater, ViewGroup container) {
        final HandlerDatabase handler = new HandlerDatabase(getActivity());
        handler.open();
        final EditText textEdit = new EditText(getActivity());
        final View rootView = inflater.inflate(R.layout.fragment, container, false);
        final ListView myListView = (ListView) rootView.findViewById(R.id.my_list_view);
        AlertDialog.Builder editor = new AlertDialog.Builder(getActivity());
                editor.create();
                editor.setTitle("Edit Chat");
                editor.setView(textEdit)
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long milliSeconds = System.currentTimeMillis();
                        SimpleDateFormat dater = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(milliSeconds);
                        String date = dater.format(calendar.getTime());
                        handler.updateChat(id, textEdit.getText().toString(), date);
                        ArrayList<Chat> allChats = handler.getAllMessages();
                        UserTracker.all_messages = chatToString(allChats);
                        ChatAdapter adaptFeed = new ChatAdapter(getActivity(), R.layout.chat_item, UserTracker.all_messages);
                        myListView.setAdapter(adaptFeed);
                        myListView.setSelection(adaptFeed.getCount() - 1);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

}
