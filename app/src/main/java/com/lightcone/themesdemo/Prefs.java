package com.lightcone.themesdemo;

import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

// See the example of fragmented preferences at
//   http://developer.android.com/reference/android/preference/PreferenceActivity.html
// for documentation.

public class Prefs extends PreferenceActivity {

    static final String TAG = "THEMES";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    /**
     * Populate the preference screen with the top-level headers.
     */

    @Override
    public void onBuildHeaders(List<Header> target) {
            loadHeadersFromResource(R.xml.preference_headers, target);
    }

    // Required because of a vulnerability.  See
    // http://stackoverflow.com/questions/19973034/isvalidfragment-android-api-19

    protected boolean isValidFragment(String fragmentName) {
        return Prefs1Fragment.class.getName().equals(fragmentName) ||
                Prefs2Fragment.class.getName().equals(fragmentName);
    }

    // Fragment showing preferences corresponding to the first header.  See
    //   http://developer.android.com/reference/android/preference/PreferenceFragment.html
    // for documentation.

    public static class Prefs1Fragment extends PreferenceFragment  {

        public Prefs1Fragment(){

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Set default values from XML preference file. See
            //     http://developer.android.com/reference/android/preference
            //       /PreferenceManager.html#setDefaultValues(android.content.Context, int, boolean)
            // The first argument is the context of the shared preference, the second is the resource
            // ID of the preference defaults file, and the third argument is false if the defaults
            // file is to be read ONLY if this method has never been called in the past.

            PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences_initialize, false);

            // This method inflates the XML resource specified in the argument and adds the preference
            // hierarchy to the current preference hierarchy.

            addPreferencesFromResource(R.xml.fragmented_preferences);
        }

    }

    // Fragment to show preferences corresponding to the second header

    public static class Prefs2Fragment extends PreferenceFragment {

        public Prefs2Fragment(){

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Set default values
            PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences2_initialize, false);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.fragmented_preferences2);
        }
    }

}

