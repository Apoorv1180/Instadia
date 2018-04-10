package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout_btn) {
            FirebaseAuth.getInstance().signOut();
            Intent logoutIntent = new Intent(this,LoginActivity.class);
            startActivity(logoutIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
