package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.Preferences.PreferenceWorkArea;

public class WorkAreaActivity extends AppCompatActivity {

    Toolbar toolbar;
    PreferenceWorkArea preferenceWorkArea;
    private Boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_area);
        preferenceWorkArea = new PreferenceWorkArea(WorkAreaActivity.this);

    }



    public void north_pavallion_btn(View view){
        Log.i("in atatat","hhhh");
        preferenceWorkArea.writePreferencesPavallion("NORTH PAVILION");
        Intent intent = new Intent(WorkAreaActivity.this,NorthPavallionActivity.class);
        startActivity(intent);
    }
    public void south_pavallion_btn(View view){
        Intent intent = new Intent(WorkAreaActivity.this,SouthPavallionActivity.class);
        preferenceWorkArea.writePreferencesPavallion("SOUTH PAVILION");
        startActivity(intent);
    }
    public void east_gallery_btn(View view){
        Intent intent = new Intent(WorkAreaActivity.this,EastGalleryActivity.class);
        preferenceWorkArea.writePreferencesPavallion("EAST GALLERY");
        startActivity(intent);
    }
    public void west_gallery_btn(View view){
        Intent intent = new Intent(WorkAreaActivity.this,WestGalleryActivity.class);
        preferenceWorkArea.writePreferencesPavallion("WEST GALLERY");
        startActivity(intent);
    }
    public void admin_block_btn(View view){
        Intent intent = new Intent(WorkAreaActivity.this,AdminBlockActivity.class);
        preferenceWorkArea.writePreferencesPavallion("ADMINISTRATION BLOCK");
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
            Intent i = new Intent(WorkAreaActivity.this,ProfileActivity.class);
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
