package com.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.apoorv.dubey.android.instadia.R;


/**
 * Created by himanshujain on 18/01/18.
 */

public class PreferenceAdminBlock {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PreferenceAdminBlock(Context context) {
        this.context = context;
        getSharedPreference();
    }

    public void getSharedPreference() {

        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_preferences_key_admin_block), Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public void writePreferencesAdminBlockArea(String value) {

        editor.putString(context.getString(R.string.preferences_key_pavallion_area), value);
        editor.commit();
    }



    public boolean checkPreferenceAdminBlock() {
        boolean status = false;
        if (sharedPreferences.getString(context.getString(R.string.my_preferences_key_admin_block), null)== null) {
            status = false;
        } else {
            status = true;
        }
        return status;
    }

    public String readPreferencesAdminBlockArea() {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_preferences_key_admin_block), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.preferences_key_pavallion_area), null);
    }




    public void clearPreferencesAdminBlock() {
        sharedPreferences.edit().clear().commit();
    }
}
