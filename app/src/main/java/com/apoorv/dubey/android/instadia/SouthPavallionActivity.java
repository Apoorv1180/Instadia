package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.Preferences.PreferenceSouthPavallion;
import com.Preferences.PreferenceWorkArea;

public class SouthPavallionActivity extends AppCompatActivity {

    PreferenceSouthPavallion preferenceSouthPavallion;
    PreferenceWorkArea preferenceWorkArea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_south_pavallion);
        preferenceSouthPavallion = new PreferenceSouthPavallion(SouthPavallionActivity.this);
        preferenceWorkArea = new PreferenceWorkArea(SouthPavallionActivity.this);
    }

    public void work_specification_btn(View view){
        switch (view.getId() ){

            case R.id.south_outer_periphery_button :
                preferenceSouthPavallion.writePreferencesPavallionArea("OUTER PERIPHERY");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);
                break;
            case R.id.south_ground_floor_button :
                preferenceSouthPavallion.writePreferencesPavallionArea("GROUND FLOOR");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);

                break;

            case R.id.south_first_floor_button :
                preferenceSouthPavallion.writePreferencesPavallionArea("FIRST FLOOR");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);

                break;

            case R.id.south_second_floor_button :
                preferenceSouthPavallion.writePreferencesPavallionArea("SECOND FLOOR");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);

                break;

            case R.id.south_corporation_boxes_button :
                preferenceSouthPavallion.writePreferencesPavallionArea("CORPORATE BOX");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);

                break;
            case R.id.south_third_floor_button :
                preferenceSouthPavallion.writePreferencesPavallionArea("THIRD FLOOR");
                preferenceWorkArea.writePreferencesPavallionOuterPerifery(false);

                break;

        }
        Intent intent = new Intent(SouthPavallionActivity.this,WorkSpecificationActivity.class);
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
            Intent i = new Intent(SouthPavallionActivity.this,ProfileActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
