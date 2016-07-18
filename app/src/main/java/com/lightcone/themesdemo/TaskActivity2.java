package com.lightcone.themesdemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

/**
 * The class is TaskActivity2 is invoked whenever Task2 is chosen from the button menu in
 * the class DisplayItems.
 *
 */

public class TaskActivity2 extends AppCompatActivity {

    private int currentTheme;
    private boolean isLight;

    /** onCreate is called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Following options to change the Theme must precede setContentView().

        toggleTheme();

        setContentView(R.layout.taskactivity2);
    }

    /** onPause is called when the activity is going to background. */

    @Override
    public void onPause() {
        super.onPause();
    }

    /** onResume is called when the activity is going to foreground. */

    @Override
    public void onResume(){
        super.onResume();
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
