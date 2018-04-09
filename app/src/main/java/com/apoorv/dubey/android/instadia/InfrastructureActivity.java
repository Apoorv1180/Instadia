package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.Preferences.PreferenceAdminBlock;
import com.Preferences.PreferenceEastGallery;
import com.Preferences.PreferenceNorthPavallion;
import com.Preferences.PreferenceSouthPavallion;
import com.Preferences.PreferenceWestGallery;
import com.Preferences.PreferenceWorkArea;
import com.Preferences.PreferenceWorkAreaSpecification;
import com.apoorv.dubey.android.model.SaveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class InfrastructureActivity extends AppCompatActivity {
    PreferenceWorkArea preferenceWorkArea;
    PreferenceNorthPavallion preferenceNorthPavallion;
    PreferenceSouthPavallion preferenceSouthPavallion;
    PreferenceEastGallery preferenceEastGallery;
    PreferenceWestGallery preferenceWestGallery;
    PreferenceAdminBlock preferenceAdminBlock;
    PreferenceWorkAreaSpecification preferenceWorkAreaSpecification;

    //radio group and button
    //workdone

    RadioGroup radioGroup;
    RadioButton radioWorkType;


    //issue description
    EditText issueDescription;

    int selectedId=0;

    //photo
    ImageView imageView;
    SaveData saveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infrastructure);
        preferenceWorkAreaSpecification=new PreferenceWorkAreaSpecification(this);
        preferenceNorthPavallion= new PreferenceNorthPavallion(this);
        preferenceSouthPavallion= new PreferenceSouthPavallion(this);
        preferenceAdminBlock= new PreferenceAdminBlock(this);
        preferenceEastGallery= new PreferenceEastGallery(this);
        preferenceWestGallery= new PreferenceWestGallery(this);
        preferenceWorkArea= new PreferenceWorkArea(this);
        imageView = findViewById(R.id.infra_image_view);
        issueDescription=findViewById(R.id.infra_comment_edit_text);
        radioGroup = findViewById(R.id.infra_radio_group);
        selectedId = radioGroup.getCheckedRadioButtonId();
        radioWorkType = findViewById(selectedId);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioWorkType= findViewById(checkedId);
            }
        });
        saveData = new SaveData();
        String userName = getUser();
        saveData.setUserName(userName);
        saveData.setStand(preferenceWorkArea.readPreferencesPavallion());
        saveData.setFloor(checkFloor());
        saveData.setWork_category(preferenceWorkAreaSpecification.readPreferencesAreaType());
        saveData.setSub_workCategory(radioWorkType.getText().toString());
        if(issueDescription.getText()!=null){
            saveData.setIssueDescription(issueDescription.getText().toString());

        }else saveData.setIssueDescription("UNKNOWN");

        writeData();
    }

    private String checkFloor() {
        switch (preferenceWorkArea.readPreferencesPavallion()){
            case  "NORTH GALLERY":
                return preferenceNorthPavallion.readPreferencesNorthPavallionArea();
            case  "SOUTH GALLERY":
                return preferenceSouthPavallion.readPreferencesPavallionArea();
            case  "EAST GALLERY":
                return preferenceEastGallery.readPreferencesEastGalleryArea();
            case "WEST GALLERY":
                return preferenceWestGallery.readPreferencesWestGalleryArea();
            case "ADMINISTRATION BLOCK":
                return preferenceAdminBlock.readPreferencesAdminBlockArea();

        }
        return "UNKNOWN";
    }

    private String getUser() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            if((user.getDisplayName() == null) || (user.getDisplayName().isEmpty())) {
    return "Unknown";

            }
            else{
                String name = user.getDisplayName();
                return name;

            }
            }
        else
            return "Unknown";
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
            Intent i = new Intent(InfrastructureActivity.this,ProfileActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private String getBookingTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        dateFormat.setTimeZone(TimeZone.getDefault());
        String defaultTimezone = TimeZone.getDefault().getID();
        Date dateObj = new Date();
        return dateFormat.format(dateObj);
    }


    private void writeData() {
       // DatabaseReference myRef = FirebaseDatabase.getInstance().getReferenceFromUrl();
     //   myRef.setValue(data);
     //   Toast.makeText(getActivity(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://instadia-c84f4.firebaseio.com/");


        Map<String, SaveData> writeData = new HashMap<>();
        writeData.put(String.valueOf(System.currentTimeMillis()),saveData);
        mDatabase.setValue(writeData);
    }
}
