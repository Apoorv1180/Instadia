package com.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.apoorv.dubey.android.instadia.R;


/**
 * Created by himanshujain on 18/01/18.
 */

public class PreferenceWorkAreaSpecification {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PreferenceWorkAreaSpecification(Context context) {
        this.context = context;
        getSharedPreference();
    }

    public void getSharedPreference() {

        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_preferences_key_work_area_specification), Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public void writePreferencesAreaType(String value) {

        editor.putString(context.getString(R.string.preferences_key_area_type), value);
        editor.commit();
    }


    public String readPreferencesAreaType() {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_preferences_key_work_area_specification), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.preferences_key_area_type), null);
    }

    public void clearPreferencesWorkAreaSpecification() {
        sharedPreferences.edit().clear().commit();
    }
}
