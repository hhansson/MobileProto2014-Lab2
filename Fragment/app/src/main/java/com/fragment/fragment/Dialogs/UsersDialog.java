package com.fragment.fragment.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fragment.fragment.DB.HandlerDatabase;
import com.fragment.fragment.MainActivity;
import com.fragment.fragment.MyFragment;
import com.fragment.fragment.R;
import com.fragment.fragment.UserTracker;

import java.util.ArrayList;
import java.util.zip.Inflater;

import static android.view.View.generateViewId;
import static android.view.View.inflate;
import static com.fragment.fragment.MainActivity.*;

/**
 * Created by user on 9/19/14.
 */
public class UsersDialog extends DialogFragment {

    public static void changeUser (final Activity activity) {
        final EditText user_one = new EditText(activity);
        //LayoutInflater inflater = activity.getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Change Users");
                builder.setMessage("Enter username below");
                builder.setView(user_one);
                builder.setPositiveButton(R.string.set_users, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserTracker.username = user_one.getText().toString();
                    }
                })
                .setNegativeButton(R.string.cancel_users, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).show();
    }
    public static void clearChats(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Clear Messages");
        builder.setMessage("Are you sure you would like to clear your message feed?");
        builder.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HandlerDatabase handler = new HandlerDatabase(activity);
                handler.open();
                handler.deleteChats();
                UserTracker.all_messages = new ArrayList<String>();
                Fragment current = activity.getFragmentManager().findFragmentByTag("myFragment");
                FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
                transaction.detach(current);
                transaction.attach(current);
                transaction.commit();
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
