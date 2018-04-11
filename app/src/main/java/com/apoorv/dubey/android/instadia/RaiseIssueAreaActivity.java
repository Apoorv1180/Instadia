package com.apoorv.dubey.android.instadia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.apoorv.dubey.android.Adapter.ImportantIssueAdapter;
import com.apoorv.dubey.android.model.ImportantIssue;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class RaiseIssueAreaActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnAddData;
    private Button btnCancel;
    private ImportantIssueAdapter importantIssueAdapter;
    private RecyclerView recyclerView;
    private List<ImportantIssue> importantIssuesList;
    private DatabaseReference globaRef;
    private ProgressBar mProgressBar;
    private LinearLayout lnrAddIssue;
    public ImageButton imgTakePicture;
    public ImageView imgIssue;
    private EditText edtIssue;
    private Button btnSave;
    private StorageReference mStorageReference;
    private FirebaseStorage firebaseStorage;
    File finalFile;
    private Context context;
    Uri downloadUri,tempUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_issue_area);
        btnAddData = findViewById(R.id.btn_add_issue);
        recyclerView = findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progress_bar);
        lnrAddIssue = findViewById(R.id.lnr_add_issue);
        imgTakePicture = findViewById(R.id.img_take_picture);
        imgIssue = findViewById(R.id.img_issue);
        btnSave = findViewById(R.id.btn_save);
        edtIssue = findViewById(R.id.edt_issue_description);
        btnCancel = findViewById(R.id.btn_cancel);
        imgTakePicture.setOnClickListener(this);
        context=RaiseIssueAreaActivity.this;
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        mProgressBar.setVisibility(View.VISIBLE);
        btnAddData.setOnClickListener(this);
        firebaseStorage = FirebaseStorage.getInstance();
        importantIssueAdapter = new ImportantIssueAdapter(this, new ArrayList<ImportantIssue>());
        importantIssueAdapter.setData(new ArrayList<ImportantIssue>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
        recyclerView.setAdapter(importantIssueAdapter);

        //TODO For reference as to how to show and save
//        globaRef = FirebaseDatabase.getInstance().getReferenceFromUrl(baseUrl + Constants.importantIssue);
//        Query myTopPostsQuery = globaRef;

//            // TODO: implement the ChildEventListener methods as documented above
//            // ...
//        });

    }

// TODO: Uncomment this for save imagecode
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            mProgressBar.setVisibility(View.VISIBLE);
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            imgIssue.setImageBitmap(bp);
            tempUri = getImageUri(getApplicationContext(), bp);
            Log.i("URI",tempUri.toString());
            finalFile = new File(getRealPathFromURI(tempUri));
            imgIssue.setImageURI(Uri.fromFile(finalFile));


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
            Intent i = new Intent(RaiseIssueAreaActivity.this,ProfileActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_issue:
                lnrAddIssue.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_save:
                //TODO add save to firebase call here
                mProgressBar.setVisibility(View.VISIBLE);
                mStorageReference = mStorageReference.child("Photos");
                final ImportantIssue importantIssue = new ImportantIssue();
                importantIssue.setId(String.valueOf(System.currentTimeMillis()));
                importantIssue.setIssueDescription(edtIssue.getText().toString());
                Uri uri = Uri.fromFile(finalFile);
                final StorageReference photoRef = mStorageReference.child(importantIssue.getId());
                photoRef.putFile(uri).addOnSuccessListener((Activity)context, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        importantIssue.setUrl(taskSnapshot.getDownloadUrl().toString());
                        Toast.makeText(context, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        writeData(importantIssue);
                    }
                });

                /*StorageReference filepath = mStorageReference.child("Photos").child(getBookingTimestamp());

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
        });*/
                break;

            case R.id.btn_cancel:
                lnrAddIssue.setVisibility(View.GONE);
                break;

            case R.id.img_take_picture:
               //TODO take picture here
                openCamera();
                break;
        }
    }

    private void writeData(ImportantIssue importantIssue) {


        mProgressBar.setVisibility(View.VISIBLE);
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://instadia-c84f4.firebaseio.com/master/ImportantIssue");
        mDatabase.child(String.valueOf(System.currentTimeMillis())).setValue(importantIssue);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                importantIssuesList = new ArrayList<>();
                for (DataSnapshot importantIssue : dataSnapshot.getChildren()) {
                    importantIssuesList.add(importantIssue.getValue(ImportantIssue.class));
                }
                importantIssueAdapter.setData(importantIssuesList);
                mProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Data Could Not be Uploaded", Toast.LENGTH_SHORT).show();

            }


        });
    }
    private void openCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    private String getBookingTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        dateFormat.setTimeZone(TimeZone.getDefault());
        String defaultTimezone = TimeZone.getDefault().getID();
        Date dateObj = new Date();
        return dateFormat.format(dateObj);
    }

}
