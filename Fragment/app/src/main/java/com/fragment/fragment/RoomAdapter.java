package com.fragment.fragment;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 10/2/14.
 */
public class RoomAdapter extends ArrayAdapter<String> {

    public RoomAdapter(Context context, int resource, List<String> rooms){
        super(context, resource, rooms);
    }
}
