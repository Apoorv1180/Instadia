package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.Preferences.PreferenceWorkArea;
import com.Preferences.PreferenceWorkAreaSpecification;

public class WorkSpecificationActivity extends AppCompatActivity {

    PreferenceWorkArea preferenceWorkArea;
    Button chairMaintainance;
    PreferenceWorkAreaSpecification preferenceWorkAreaSpecification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_specification);
        preferenceWorkArea = new PreferenceWorkArea(WorkSpecificationActivity.this);
        chairMaintainance=findViewById(R.id.chair_maintainance_button);
        preferenceWorkAreaSpecification= new PreferenceWorkAreaSpecification(WorkSpecificationActivity.this);
        if(preferenceWorkArea.readPreferencesPavallion().equalsIgnoreCase("ADMINISTRATION BLOCK") || preferenceWorkArea.readPreferencesPavallionOuterPerifery()){

            chairMaintainance.setVisibility(View.GONE);
        }
        else
            chairMaintainance.setVisibility(View.VISIBLE);
    }
    public void infrastructure_btn(View view){
        Intent intent = new Intent(WorkSpecificationActivity.this,InfrastructureActivity.class);
        preferenceWorkAreaSpecification.writePreferencesAreaType("INFRASTRUCTURE");
        startActivity(intent);
    }
    public void chair_maintainance_btn(View view){
        Intent intent = new Intent(WorkSpecificationActivity.this,ChairMaintainanceActivity.class);
        preferenceWorkAreaSpecification.writePreferencesAreaType("CHAIR MAINTAINANCE");
        startActivity(intent);
    }
    public void electricity_btn(View view){
        Intent intent = new Intent(WorkSpecificationActivity.this,ElectricityActivity.class);
        preferenceWorkAreaSpecification.writePreferencesAreaType("ELECTRICITY");
        startActivity(intent);
    }
    public void house_keeping_btn(View view){
        Intent intent = new Intent(WorkSpecificationActivity.this,HouseKeepingActivity.class);
        preferenceWorkAreaSpecification.writePreferencesAreaType("HOUSE KEEPING");
        startActivity(intent);
    }
    public void other_issue_btn(View view){
        Intent intent = new Intent(WorkSpecificationActivity.this,AnyOtherIssueActivity.class);
        preferenceWorkAreaSpecification.writePreferencesAreaType("OTHER ISSUES");
        startActivity(intent);
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
            Intent i = new Intent(WorkSpecificationActivity.this,ProfileActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
