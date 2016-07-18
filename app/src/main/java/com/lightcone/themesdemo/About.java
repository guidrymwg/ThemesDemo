package com.lightcone.themesdemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * The class About defines and "About" information screen for the project.
 *
 * @author Mike Guidry
 *
 */

public class About extends Activity {

    private int currentTheme;
    private boolean isLight;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Theme change must precede setContentView().

        toggleTheme();

        setContentView(R.layout.about);
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

        // The custom dialog themes DialogNoTitle and DialogNoTitleDark are defined in
        // res/values/style.xml

        if(isLight) {
            //setTheme(R.style.DialogNoTitle);
            setTheme(R.style.MyDialogLight);
        } else {
            //setTheme(R.style.DialogNoTitleDark);
            setTheme(R.style.MyDialogDark);
        }
    }
}
