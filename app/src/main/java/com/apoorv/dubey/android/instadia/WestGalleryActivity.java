package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.Preferences.PreferenceWestGallery;
import com.Preferences.PreferenceWorkArea;

public class WestGalleryActivity extends AppCompatActivity {

    PreferenceWorkArea preferenceWorkArea;
    PreferenceWestGallery preferenceWestGallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_west_gallery);
        preferenceWorkArea = new PreferenceWorkArea(WestGalleryActivity.this);
        preferenceWestGallery = new PreferenceWestGallery(WestGalleryActivity.this);
    }

    public void work_specification_btn(View view){

        switch (view.getId() ){

            case R.id.west_outer_periphery_button :
                preferenceWestGallery.writePreferencesWestGalleryArea("OUTER PERIPHERY");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);
                break;
            case R.id.west_ground_floor_button :
                preferenceWestGallery.writePreferencesWestGalleryArea("GROUND FLOOR");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);

                break;

            case R.id.west_first_floor_button :
                preferenceWestGallery.writePreferencesWestGalleryArea("FIRST FLOOR");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);

                break;

            case R.id.west_second_floor_button :
                preferenceWestGallery.writePreferencesWestGalleryArea("SECOND FLOOR");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);

                break;


        }
        Intent intent = new Intent(WestGalleryActivity.this,WorkSpecificationActivity.class);
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
            Intent i = new Intent(WestGalleryActivity.this,ProfileActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
