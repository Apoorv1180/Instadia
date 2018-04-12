package com.apoorv.dubey.android.instadia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class AnyOtherIssueActivity extends AppCompatActivity {

    private ImageView other_issue_image_view;
    PreferenceWorkArea preferenceWorkArea;
    PreferenceNorthPavallion preferenceNorthPavallion;
    PreferenceSouthPavallion preferenceSouthPavallion;
    PreferenceEastGallery preferenceEastGallery;
    PreferenceWestGallery preferenceWestGallery;
    PreferenceAdminBlock preferenceAdminBlock;
    PreferenceWorkAreaSpecification preferenceWorkAreaSpecification;
    Uri imageUri;
    private StorageReference mStorageReference;
    Uri downloadUri;
    private ProgressBar mProgressBar;
    private Boolean doubleBackToExitPressedOnce = false;

    //issue description
    EditText issueDescription;


    SaveData saveData;

    Button otherIssueSaveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_any_other_issue);
        preferenceWorkArea= new PreferenceWorkArea(this);
        preferenceEastGallery= new PreferenceEastGallery(this);
        preferenceWestGallery= new PreferenceWestGallery(this);
        preferenceAdminBlock= new PreferenceAdminBlock(this);
        preferenceWorkAreaSpecification=new PreferenceWorkAreaSpecification(this);
        preferenceNorthPavallion= new PreferenceNorthPavallion(this);
        preferenceSouthPavallion= new PreferenceSouthPavallion(this);
        issueDescription=findViewById(R.id.other_issue_comment_edit_text);
        otherIssueSaveButton=findViewById(R.id.other_issue_save_button);
        other_issue_image_view= (ImageView) findViewById(R.id.other_issue_image_view);
        mStorageReference= FirebaseStorage.getInstance().getReference();
        mProgressBar = findViewById(R.id.progressBar);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        other_issue_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });





        final String NullValues = "NA";
        otherIssueSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveData = new SaveData();
                saveData.setChairNumber(NullValues);
                saveData.setHouseKeepingPercentage(NullValues);
                saveData.setDate(getBookingTimestamp());
                String userName = getUser();
                if(!TextUtils.isEmpty(userName)) {
                    saveData.setUserName(userName);
                }
                else saveData.setUserName(NullValues);
                saveData.setPavallion(NullValues);
                saveData.setCompletionStatus("PENDING");
                if(!TextUtils.isEmpty(preferenceWorkArea.readPreferencesPavallion())) {
                    saveData.setStand(preferenceWorkArea.readPreferencesPavallion());
                }else saveData.setStand(NullValues);
                saveData.setFloor(checkFloor());
                saveData.setWork_category(preferenceWorkAreaSpecification.readPreferencesAreaType());
                saveData.setSub_workCategory(NullValues);
                if(issueDescription.getText()!=null){
                    saveData.setIssueDescription(issueDescription.getText().toString());

                }else saveData.setIssueDescription(NullValues);
                if(!TextUtils.isEmpty(downloadUri.toString()))
                saveData.setPhotoUri(downloadUri.toString());
                else saveData.setPhotoUri(NullValues);
                writeData();

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
            other_issue_image_view.setImageBitmap(bp);
            Uri tempUri = getImageUri(getApplicationContext(), bp);
            Log.i("URI",tempUri.toString());
            File finalFile = new File(getRealPathFromURI(tempUri));
            other_issue_image_view.setImageURI(Uri.fromFile(finalFile));
            StorageReference filepath = mStorageReference.child("Photos").child(getBookingTimestamp());

            filepath.putFile(tempUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgressBar.setVisibility(View.GONE);

                    downloadUri = taskSnapshot.getDownloadUrl();
                    Log.i("DownLoad uri",downloadUri.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"File Cannot Be Uploaded!",Toast.LENGTH_SHORT).show();
                }
            });


        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
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
            Intent i = new Intent(AnyOtherIssueActivity.this,ProfileActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private String checkFloor() {
        String floor ="";
        Log.i("TAG",preferenceWorkArea.readPreferencesPavallion().toString()+ preferenceNorthPavallion.readPreferencesNorthPavallionArea().toString());
        switch (preferenceWorkArea.readPreferencesPavallion()){
            case  "NORTH PAVALLION":
                floor= preferenceNorthPavallion.readPreferencesNorthPavallionArea();
                break;
            case  "SOUTH PAVALLION":
                floor= preferenceSouthPavallion.readPreferencesPavallionArea();
                break;
            case  "EAST GALLERY":
                floor= preferenceEastGallery.readPreferencesEastGalleryArea();
                break;
            case "WEST GALLERY":
                floor= preferenceWestGallery.readPreferencesWestGalleryArea();
                break;
            case "ADMINISTRATION BLOCK":
                floor= preferenceAdminBlock.readPreferencesAdminBlockArea();
                break;

        }
        return floor;
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

    private String getBookingTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        dateFormat.setTimeZone(TimeZone.getDefault());
        String defaultTimezone = TimeZone.getDefault().getID();
        Date dateObj = new Date();
        return dateFormat.format(dateObj);
    }


    private void writeData() {
        mProgressBar.setVisibility(View.VISIBLE);
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://instadia-c84f4.firebaseio.com/master");
        mDatabase.child(String.valueOf(System.currentTimeMillis())).setValue(saveData);
        Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);
        startActivity(new Intent(this, ProfileActivity.class));

    }
        @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            }
        }
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
