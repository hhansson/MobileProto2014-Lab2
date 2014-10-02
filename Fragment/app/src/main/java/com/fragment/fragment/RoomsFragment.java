package com.fragment.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 10/2/14.
 */
public class RoomsFragment extends Fragment {

    public RoomsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.all_rooms, container, false);
        final ListView roomsList = (ListView) rootView.findViewById(R.id.all_rooms_list);
        final ArrayList<String> chatRooms = new ArrayList<String>();
        chatRooms.add("Chatroom 0");
        chatRooms.add("Chatroom 1");
        chatRooms.add("Chatroom 2");
        RoomAdapter roomAdapter = new RoomAdapter(getActivity(), R.layout.room_item, chatRooms);
        roomsList.setAdapter(roomAdapter);

        roomsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity activity = new MainActivity();
                activity.getRoom(savedInstanceState);
            }
        });

        return rootView;

    }

}
