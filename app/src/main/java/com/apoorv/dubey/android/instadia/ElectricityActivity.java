package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.Preferences.PreferenceNorthPavallion;
import com.Preferences.PreferenceSouthPavallion;
import com.Preferences.PreferenceWorkArea;
import com.Preferences.PreferenceWorkAreaSpecification;

public class ElectricityActivity extends AppCompatActivity {

    PreferenceWorkArea preferenceWorkArea;
    PreferenceWorkAreaSpecification preferenceWorkAreaSpecification;
    PreferenceNorthPavallion preferenceNorthPavallion;
    PreferenceSouthPavallion preferenceSouthPavallion;

    private Button electricity_camera_btn;
    private ImageView electricity_image_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity);
        preferenceWorkArea= new PreferenceWorkArea(this);
        preferenceWorkAreaSpecification=new PreferenceWorkAreaSpecification(this);
        preferenceNorthPavallion= new PreferenceNorthPavallion(this);
        preferenceSouthPavallion= new PreferenceSouthPavallion(this);
        Toast.makeText(getApplicationContext(),preferenceWorkArea.readPreferencesPavallion()+"\n"+
        preferenceNorthPavallion.readPreferencesNorthPavallionArea()+"\n"+
        preferenceWorkAreaSpecification.readPreferencesAreaType(),Toast.LENGTH_LONG).show();

        electricity_camera_btn = (Button) findViewById(R.id.other_issue_camera_btn);

        electricity_image_view= (ImageView) findViewById(R.id.other_issue_image_view);


        electricity_camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

    }
    private void openCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            electricity_image_view.setImageBitmap(bp);
        }
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
            Intent i = new Intent(ElectricityActivity.this,ProfileActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
