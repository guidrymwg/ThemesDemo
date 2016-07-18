package com.lightcone.themesdemo;

import java.util.Locale;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;
import android.widget.Toast;


/**
 * The class MainActivity is the initial activity for the project ThemesDemo, which gives a
 * demonstration of using styles and themes, defining custom styles, changing the theme at
 * runtime, and persistent storage of settings using SharedPreferences with fragments.
 */

public class MainActivity extends FragmentActivity implements OnClickListener, OnLongClickListener{

    public static final String TAG="THEMES";
    private boolean isLight;
    private boolean isChecked;
    private TextView tv;
    private int currentTheme;
    private int oldTheme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        isChecked = sharedPref.getBoolean("caps_pref", false);
        String lister = sharedPref.getString("list_preference", "1");
        oldTheme = Integer.parseInt(lister);

        // Following options to change the Theme must precede setContentView().

        toggleTheme();

        setContentView(R.layout.activity_main);

        // Identify buttons and attach click and long-click listeners to each
        View createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(this);
        createButton.setOnLongClickListener(this);
        View errorDemoButton = findViewById(R.id.errorDemoButton);
        errorDemoButton.setOnClickListener(this);
        errorDemoButton.setOnLongClickListener(this);

        // TextView for name
        tv = (TextView) findViewById(R.id.TextView1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
        Log.i(TAG, "onResume()");
        toggleTheme();
    }

    // Method to check SharedPreferences and set the current theme

    private void toggleTheme(){

        // Following options to change the Theme must precede setContentView().

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        isChecked = sharedPref.getBoolean("caps_pref", false);
        String lister = sharedPref.getString("list_preference", "1");
        String myName = sharedPref.getString("edittext_preference", "");

        currentTheme = Integer.parseInt(lister);
        if(currentTheme == 2){
            isLight = false;
        } else {
            isLight = true;
        }

        // Convert name to all upper case if that preference checked
        String temp = "Hello "+myName;
        if(isChecked) {
            // Strings in Java (Android) are immutable, so must return new value
            temp = temp.toUpperCase(Locale.US);
        }
        if(tv != null) tv.setText(temp);

        Log.i(TAG, "MainActivity:  isLight="+isLight +" lister="+lister+" Name="+myName
                +" isChecked="+isChecked);

        if(isLight) {
            setTheme(R.style.HoloLightCustom);
        } else {
            setTheme(R.style.HoloCustom);
        }

        // If theme has changed, force a restart of MainActivity to get the new theme
        // to display for it. That this is required may be a known bug in Android.  See
        //
        //    https://code.google.com/p/android/issues/detail?id=4394
        //
        // for further discussion.

        if(oldTheme != currentTheme){

            oldTheme = currentTheme;

            Intent k = new Intent(this, MainActivity.class);

            // Following flag clears the activity with old theme from the stack so an exit from the
            // activity with new theme will not take you back to the version with the old theme.

            k.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(k);
        }
    }

    /** Process clicks on the buttons */

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.createButton:		// Open tasks list screen
                Intent j = new Intent(this, DisplayItems.class);
                startActivity(j);
                break;
            case R.id.errorDemoButton:		// Demo the custom error dialog
                showErrorAlert("ERROR",
                        "Demo of error dialog.  A custom error message will be displayed in this field.",
                        R.drawable.sad_smiley_blue, this);
                break;
        }
    }

    /** Process long-clicks.  Presently popping up "tooltip" description of button. */

    @Override
    public boolean onLongClick(View v) {
        switch(v.getId()){
            case R.id.createButton:
                Toast.makeText(this, "Press `Create an Item' to open list of tasks.",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.errorDemoButton:
                Toast.makeText(this, "Press `Demo an Error' to demo custom error dialog.",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    /** Method to exit application. Not essential, since the Android Back button
     * accomplishes the same task.
     */

    public void finishUp(){
        finish();
    }

    /** Handle events from the options menu in action bar and its overflow menu.  */

    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){

            case (R.id.help):
                // Actions for Help page
                Intent i = new Intent(this, Help.class);
                startActivity(i);
                return true;

            case(R.id.about):
                // Actions for About page
                Intent k = new Intent(this, About.class);
                startActivity(k);
                return true;

            case(R.id.prefs):
                // Actions for preferences page
                Log.i(TAG,"Settings");
                Intent j = new Intent(this, Prefs.class);
                startActivity(j);
                return true;

            // Exit: not really needed because back button serves same function,
            // but we include as illustration since some users may be more
            // comfortable with an explicit quit button.

            case (R.id.quit):
                finishUp();
                return true;

        }
        return false;
    }


    /** Display example error dialog with a custom message using the inner
     * class AlertDialog. */

    private void showErrorAlert(String title, String message, int icon, Context context){

        int theme = R.style.MyDialogDark; //AlertDialog.THEME_HOLO_DARK;
        if(isLight) theme = R.style.MyDialogLight; //AlertDialog.THEME_HOLO_LIGHT;

        makeDialog(context, title, message, true, true, theme, icon, null);
    }


    // General-purpose method to create dialogs. Creates instances of the class AlertFragment.
    // To omit iconID, showCancel, showOK, or okClass options, set the following values:
    //
    //     iconID = -1            // Omit icon
    //     showCancel = false     // Omit cancel button
    //     showOK = false         // Omit OK button
    //     okClass = null         // Omit class to launch with OK
    //
    // for the corresponding argument.

    public void makeDialog(Context context, String title, String message, boolean showCancel,
                           boolean showOK, int theme, int iconID, Class<?> okClass){

        AlertFragment.context = context;
        AlertFragment.iconID = iconID;
        AlertFragment.title = title;
        AlertFragment.message = message;
        AlertFragment.theme = theme;
        AlertFragment.showOK = showOK;
        AlertFragment.showCancel = showCancel;
        AlertFragment.okClass = okClass;

        DialogFragment fragment = new AlertFragment();
        fragment.show(getSupportFragmentManager(), "Dialog");
    }


    /**
     * A class to create generic alert dialog fragments. To call from another
     * class, set the values of the static variables and then instantiate and show.
     * Example of usage:
     *
     *      AlertFragment.context = context;
     AlertFragment.iconID = iconID;
     AlertFragment.title = title;
     AlertFragment.message = message;
     AlertFragment.theme = theme;
     AlertFragment.showOK = showOK;
     AlertFragment.showCancel = showCancel;
     AlertFragment.okClass = okClass;

     DialogFragment fragment = new AlertFragment();
     fragment.show(getSupportFragmentManager(), "Dialog");
     */

    public static class AlertFragment extends DialogFragment {

        public static Context context;
        public static String message;
        public static String title = null;
        public static int iconID = -1;
        public static int theme = -1;
        public static boolean showOK;
        public static boolean showCancel;
        public static Class<?> okClass = null;
        public static int buttonPressed;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the Builder class to construct the dialog.  Use the
            // form of the builder constructor that allows a theme to be set.

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), theme);

            if(title != null) builder.setTitle(title);
            if(iconID != -1) builder.setIcon(iconID);
            builder.setMessage(message);
            if(showOK && okClass != null){
                builder.setPositiveButton("Select this task", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(context, okClass);
                        startActivity(i);
                    }
                });
            }
            if(showCancel){
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Default is to cancel the dialog window.
                    }
                });
            }
            // Create the AlertDialog object and return it
            return builder.create();
        }

    }

}

