package com.lightcone.themesdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

/**
 * The class DisplayItems displays a set of buttons allowing different tasks to
 * be chosen. Short presses on the buttons launch the tasks. Long presses open a
 * dialog box giving a description of the task and the option to execute it from
 * there.
 *
 * @author Mike Guidry
 *
 */

public class DisplayItems extends AppCompatActivity implements OnClickListener,
        OnLongClickListener {

    private static final String TAG = "THEMES"; // Debugging tag
    private static final int numberTasks = 2;
    private static final String launcherTitle = "Task Description";
    private static final int launcherIcon = R.drawable.bugroid_small;
    private int buttonPressed;
    private String[] taskDescription = new String[numberTasks];
    private int currentTheme;
    private boolean isLight;

    /**
     * Method onCreate(Bundle savedInstanceState) is called when the activity is
     * first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Following options to change the Theme must precede setContentView().

        toggleTheme();
        setContentView(R.layout.displayitems);

        // Identify buttons in XML layout and attach click and long-click
        // listeners to each

        View button01 = findViewById(R.id.button01);
        button01.setOnClickListener(this);
        button01.setOnLongClickListener(this);
        View button02 = findViewById(R.id.button02);
        button02.setOnClickListener(this);
        button02.setOnLongClickListener(this);

        // Put task description strings in an array for later use

        taskDescription[0] = getString(R.string.task_description1);
        taskDescription[1] = getString(R.string.task_description2);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Override onClick(View v) to process clicks on buttons. These will call
     * launchTask() to execute an Intent launching a new class corresponding to
     * the task, with the case specified by the integer buttonPressed.
     */

    @Override
    public void onClick(View v) {
        buttonPressed = 0;
        switch (v.getId()) {
            case R.id.button01:
                buttonPressed = 1;
                break;

            case R.id.button02:
                buttonPressed = 2;
                break;
        }
        launchTask();
    }

    /**
     * Override onLongClick(View v) to process long-clicks on buttons. These
     * will launch a dialog describing the task, with an option to execute the
     * task from that dialog.
     */

    @Override
    public boolean onLongClick(View v) {
        buttonPressed = 0;
        switch (v.getId()) {
            case R.id.button01:
                buttonPressed = 1;
                break;

            case R.id.button02:
                buttonPressed = 2;
                break;

        }
        showTask(launcherTitle, taskDescription[buttonPressed - 1],
                launcherIcon, this);
        return true;
    }

    /**
     * Method showTask() creates a custom launch dialog popped up by a
     * long-press on a button. This dialog presents a summary of the task to the
     * user and has buttons to either launch the task or cancel the dialog.
     * Which task to present is controlled by the value of the int
     * buttonPressed, which is stored if a button is pressed or long-pressed.
     *
     * In this example we use an older approach to launching a dialog using the
     * AlertDialog class.  In MainActivity.java of this project we illustrate a
     * similar task using the more modern DialogFragment class. See
     *
     *    http://developer.android.com/guide/topics/ui/dialogs.html
     *
     * for further discussion.
     */

    private void showTask(String title, String message, int icon, Context context) {

        int alertTheme = R.style.LightCustom;
        if(currentTheme == 2){
            alertTheme = R.style.DarkCustom;
        }

        AlertDialog alertDialog = new AlertDialog.Builder(context, alertTheme).create();

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon(icon);

        int positive = AlertDialog.BUTTON_POSITIVE;
        int negative = AlertDialog.BUTTON_NEGATIVE;

        alertDialog.setButton(positive, "Select this Task",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        launchTask();
                    }
                });

        alertDialog.setButton(negative, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Can execute code here if desired.
                    }
                });

        alertDialog.show();
    }

    /**
     * Method launchTask() uses a switch statement to decide which task to
     * launch. It is invoked either directly by a press of a button for the
     * task, or indirectly by a long-press on the button, which launches the
     * method createLaunchDialog() describing the task and presents a button
     * that calls this method.
     */

    private void launchTask() {
        switch (buttonPressed) {
            case 1: // Launch task 1
                Intent i = new Intent(this, TaskActivity1.class);
                startActivity(i);
                break;
            case 2: // Launch task 2
                Intent j = new Intent(this, TaskActivity2.class);
                startActivity(j);
        }
    }

    // Method to check SharedPreferences and set the current theme

    private void toggleTheme(){

        // Following options to change the Theme must precede setContentView().

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String lister = sharedPref.getString("list_preference", "1");

        currentTheme = Integer.parseInt(lister);
        if(currentTheme == 2){
            isLight = false;
        } else {
            isLight = true;
        }

        if(isLight) {
            setTheme(R.style.LightCustom);
        } else {
            setTheme(R.style.DarkCustom);
        }
    }

}

