package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
    public void work_btn(View view){

        Intent intent = new Intent(ProfileActivity.this,WorkAreaActivity.class);
        startActivity(intent);
    }

   /* public void stock_btn(View view){
        Intent intent = new Intent(ProfileActivity.this,StockAreaActivity.class);
        startActivity(intent);
    }*/
    public void raise_issue_btn(View view){
        Intent intent = new Intent(ProfileActivity.this,RaiseIssueAreaActivity.class);
        startActivity(intent);
    }


    public void stock_btn(View view){
        Intent intent = new Intent(ProfileActivity.this,StockActivity.class);
        startActivity(intent);
    }
    public void report_btn(View view){
        Intent intent = new Intent(ProfileActivity.this,CheckReportActivity.class);
        startActivity(intent);
    }
    public void important_btn(View view){
        Intent intent = new Intent(ProfileActivity.this,StockActivity.class);
        startActivity(intent);
    }

}
