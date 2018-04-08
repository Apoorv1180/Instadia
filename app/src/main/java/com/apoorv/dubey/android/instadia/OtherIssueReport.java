package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.Preferences.PreferenceNorthPavallion;
import com.Preferences.PreferenceSouthPavallion;
import com.Preferences.PreferenceWorkArea;
import com.Preferences.PreferenceWorkAreaSpecification;

public class OtherIssueReport extends AppCompatActivity {

    PreferenceSouthPavallion preferenceSouthPavallion;
    PreferenceNorthPavallion preferenceNorthPavallion;
    PreferenceWorkArea preferenceWorkArea;
    PreferenceWorkAreaSpecification preferenceWorkAreaSpecification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_issue_report);
        preferenceWorkArea= new PreferenceWorkArea(this);
        preferenceWorkAreaSpecification=new PreferenceWorkAreaSpecification(this);
        preferenceNorthPavallion= new PreferenceNorthPavallion(this);
        preferenceSouthPavallion= new PreferenceSouthPavallion(this);
        Toast.makeText(getApplicationContext(),preferenceWorkArea.readPreferencesPavallion()+"\n"+
                preferenceNorthPavallion.readPreferencesNorthPavallionArea()+"\n"+
                preferenceWorkAreaSpecification.readPreferencesAreaType(),Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_home_btn) {
            Intent i = new Intent(OtherIssueReport.this,ProfileActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
