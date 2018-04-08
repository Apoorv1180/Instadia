package com.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.apoorv.dubey.android.instadia.R;


/**
 * Created by himanshujain on 18/01/18.
 */

public class PreferenceNorthPavallion {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PreferenceNorthPavallion(Context context) {
        this.context = context;
        getSharedPreference();
    }

    public void getSharedPreference() {

        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_preferences_key_north_pavallion), Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public void writePreferencesNorthPavallionArea(String value) {

        editor.putString(context.getString(R.string.preferences_key_pavallion_area), value);
        editor.commit();
    }



    public boolean checkPreferenceNorthPavallion() {
        boolean status = false;
        if (sharedPreferences.getString(context.getString(R.string.my_preferences_key_north_pavallion), null)== null) {
            status = false;
        } else {
            status = true;
        }
        return status;
    }

    public String readPreferencesNorthPavallionArea() {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_preferences_key_north_pavallion), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.preferences_key_pavallion_area), null);
    }




    public void clearPreferencesCentreData() {
        sharedPreferences.edit().clear().commit();
    }
}
