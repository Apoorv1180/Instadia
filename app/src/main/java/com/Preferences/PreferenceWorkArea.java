package com.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.apoorv.dubey.android.instadia.R;


/**
 * Created by himanshujain on 18/01/18.
 */

public class PreferenceWorkArea {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PreferenceWorkArea(Context context) {
        this.context = context;
        getSharedPreference();
    }

    public void getSharedPreference() {

        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_preferences_key_work_area), Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public void writePreferencesPavallion(String value) {

        editor.putString(context.getString(R.string.preferences_key_pavallion), value);
        editor.commit();
    }

    public void writePreferencesuserName(String value) {

        editor.putString(context.getString(R.string.preferences_key_username), value);
        editor.commit();
    }

    public void writePreferencesPavallionOuterPerifery(boolean value) {

        editor.putBoolean(context.getString(R.string.preferences_key_pavallion_area), value);
        editor.commit();
    }



    public String readPreferencesPavallion() {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_preferences_key_work_area), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.preferences_key_pavallion), null);
    }

    public boolean readPreferencesPavallionOuterPerifery() {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_preferences_key_work_area), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(context.getString(R.string.preferences_key_pavallion_area), false);
    }

    public String readPreferencesuserName() {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_preferences_key_work_area), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.preferences_key_username), "Unknown");
    }




    public void clearPreferencesWorkArea() {
        sharedPreferences.edit().clear().commit();
    }
}
