package com.fragment.fragment;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;

import com.fragment.fragment.Dialogs.UsersDialog;

import java.util.zip.Inflater;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            UserTracker.username = "Unknown";
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MyFragment(), "myFragment")
                    .commit();
        }
    }

    public void getRoom(Bundle savedInstanceState){
        getFragmentManager().beginTransaction()
                .add(new MyFragment(), "chatFragment")
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.users_set) {
            UsersDialog.changeUser(this);
        }
        if (id == R.id.clear_feed) {
            UsersDialog.clearChats(this);
        }
        return super.onOptionsItemSelected(item);
    }

}
