package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.Preferences.PreferenceAdminBlock;
import com.Preferences.PreferenceWorkArea;

public class AdminBlockActivity extends AppCompatActivity {

    PreferenceAdminBlock preferenceAdminBlock;
    PreferenceWorkArea preferenceWorkArea;
    private Boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_block);
        preferenceAdminBlock = new PreferenceAdminBlock(AdminBlockActivity.this);
        preferenceWorkArea = new PreferenceWorkArea(    AdminBlockActivity.this);
    }
    public void work_specification_btn(View view){
        switch (view.getId() ){


            case R.id.ground_floor_button :
                preferenceAdminBlock.writePreferencesAdminBlockArea("GROUND FLOOR");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);
                break;

            case R.id.admin_first_floor_button :
                preferenceAdminBlock.writePreferencesAdminBlockArea("FIRST FLOOR");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);

                break;

            case R.id.admin_second_floor_button :
                preferenceAdminBlock.writePreferencesAdminBlockArea("SECOND FLOOR");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);

                break;

            case R.id.admin_third_floor_button :
                preferenceAdminBlock.writePreferencesAdminBlockArea("THIRD FLOOR");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);

                break;


        }
        Intent intent = new Intent(AdminBlockActivity.this,WorkSpecificationActivity.class);
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
            Intent i = new Intent(AdminBlockActivity.this,ProfileActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {
        /*
         */

        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 3000);
    }
}
