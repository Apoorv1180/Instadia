package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class CheckReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_report);
    }
    public void infrastructure_btn(View view){
        Intent intent = new Intent(CheckReportActivity.this,InfraReport.class);
        startActivity(intent);
    }
    public void chair_maintainance_btn(View view){
        Intent intent = new Intent(CheckReportActivity.this,ChairMaintainanceReport.class);
        startActivity(intent);
    }
    public void electricity_btn(View view){
        Intent intent = new Intent(CheckReportActivity.this,ElectricityReport.class);
        startActivity(intent);
    }
    public void house_keeping_btn(View view){
        Intent intent = new Intent(CheckReportActivity.this,HouseKeepingReport.class);
        startActivity(intent);
    }
    public void specific_issue_btn(View view){

        Intent intent = new Intent(CheckReportActivity.this,OtherIssueReport.class);
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
            Intent i = new Intent(CheckReportActivity.this,ProfileActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
