package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.Preferences.PreferenceEastGallery;
import com.Preferences.PreferenceWorkArea;

public class EastGalleryActivity extends AppCompatActivity {

    PreferenceEastGallery preferenceEastGallery;
    PreferenceWorkArea preferenceWorkArea;
    private Boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_east_gallery);
        preferenceEastGallery = new PreferenceEastGallery(EastGalleryActivity.this);
        preferenceWorkArea= new PreferenceWorkArea(EastGalleryActivity.this);
    }

    public void work_specification_btn(View view){
        switch (view.getId() ){

            case R.id.east_outer_periphery_button :
                preferenceEastGallery.writePreferencesEastGalleryArea("OUTER PERIPHERY");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(true);
                break;
            case R.id.east_ground_floor_button :
                preferenceEastGallery.writePreferencesEastGalleryArea("GROUND FLOOR");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);
                break;

            case R.id.east_first_floor_button :
                preferenceEastGallery.writePreferencesEastGalleryArea("FIRST FLOOR");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);
                break;

            case R.id.east_second_floor_button :
                preferenceEastGallery.writePreferencesEastGalleryArea("SECOND FLOOR");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);
                break;

        }
        Intent intent = new Intent(EastGalleryActivity.this,WorkSpecificationActivity.class);
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
            Intent i = new Intent(EastGalleryActivity.this,ProfileActivity.class);
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
