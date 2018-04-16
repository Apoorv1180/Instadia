package com.apoorv.dubey.android.instadia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
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
import com.apoorv.dubey.android.CSVUtils.CSVWriters;
import com.apoorv.dubey.android.CSVUtils.JsonFlattener;
import com.apoorv.dubey.android.model.ImportantIssue;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class RaiseIssueAreaActivity extends AppCompatActivity implements View.OnClickListener,ImportantIssueAdapter.EditData {
    public Button imgTakePicture;
    public ImageView imgIssue;
    File finalFile;
    Uri downloadUri, tempUri;
    private String finalDownLoadUrl = "";
    private Button btnAddData,btnShare;
    private Button btnCancel;
    private ImportantIssueAdapter importantIssueAdapter;
    private RecyclerView recyclerView;
    private List<ImportantIssue> importantIssuesList;
    private DatabaseReference globaRef;
    private ProgressBar mProgressBar;
    private LinearLayout lnrAddIssue;
    private EditText edtIssue;
    private Button btnSave;
    private File path;
    private String name;
    private StorageReference mStorageReference;


    private FirebaseStorage firebaseStorage;
    private Context context;
    ImportantIssue importantIssue;
    private Boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_issue_area);
        btnAddData = findViewById(R.id.btn_add_issue);
        btnShare=findViewById(R.id.btn_share_issue);
        recyclerView = findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progress_bar);
        lnrAddIssue = findViewById(R.id.lnr_add_issue);
        imgIssue = findViewById(R.id.img_take_picture);
        btnSave = findViewById(R.id.btn_save);
        edtIssue = findViewById(R.id.edt_issue_description);
        btnCancel = findViewById(R.id.btn_cancel);
       // imgTakePicture.setOnClickListener(this);
        context = RaiseIssueAreaActivity.this;
        btnSave.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        mProgressBar.setVisibility(View.VISIBLE);
        btnAddData.setOnClickListener(this);
        firebaseStorage = FirebaseStorage.getInstance();
        importantIssueAdapter = new ImportantIssueAdapter(this, new ArrayList<ImportantIssue>(),this);
        importantIssueAdapter.setData(new ArrayList<ImportantIssue>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
        recyclerView.setAdapter(importantIssueAdapter);
        importantIssue = new ImportantIssue();
        imgIssue.setOnClickListener(this);
        mStorageReference= FirebaseStorage.getInstance().getReference();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        //TODO For reference as to how to show and save
        viewData();

    }

    // TODO: Uncomment this for save imagecode
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            mProgressBar.setVisibility(View.VISIBLE);
            btnSave.setEnabled(false);
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            imgIssue.setImageBitmap(bp);
            tempUri = getImageUri(getApplicationContext(), bp);
            Log.i("URI",tempUri.toString());
            File finalFile = new File(getRealPathFromURI(tempUri));
            imgIssue.setImageURI(Uri.fromFile(finalFile));
            StorageReference filepath = mStorageReference.child("Photos").child(String.valueOf(System.currentTimeMillis()));

            filepath.putFile(tempUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgressBar.setVisibility(View.GONE);
                    downloadUri = taskSnapshot.getDownloadUrl();
                    Log.i("DownLoad uri",downloadUri.toString());
                    finalDownLoadUrl = downloadUri.toString();
                    importantIssue = new ImportantIssue();
                    importantIssue.setId(String.valueOf(System.currentTimeMillis()));
                    importantIssue.setIssueDescription(edtIssue.getText().toString());

                    btnSave.setEnabled(true);
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
            Intent i = new Intent(RaiseIssueAreaActivity.this, ProfileActivity.class);
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
                writeData();
                break;

            case R.id.btn_cancel:
                lnrAddIssue.setVisibility(View.GONE);
                break;

            case R.id.img_take_picture:
                //TODO take picture here
                openCamera();
                break;
            case R.id.btn_share_issue:
                generateCSV();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_CC, new String[] {"apoorv111221cse@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "InstaDia_Important_Issue CSV");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi,please find the attachment.");
                File file = new File(path, name);
                if (!file.exists() || !file.canRead()) {
                    return;
                }
                Uri uri = FileProvider.getUriForFile(RaiseIssueAreaActivity.this, getApplicationContext().getPackageName() + ".provider", file);
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
                break;
        }
    }

    private void generateCSV() {
        Gson gson = new Gson();
        String reqJson =  "";
        if (importantIssuesList.size() != 0)
        {
            reqJson= gson.toJson(importantIssuesList);
        }
        else
        {
            reqJson= gson.toJson(importantIssuesList);
        }

        JsonFlattener parser = new JsonFlattener();
        CSVWriters writer = new CSVWriters();

        List<Map<String, String>> flatJson = null;
        //to this path add a new directory path and create new App dir (InstroList) in /documents Dir

        try {
            flatJson = parser.parseJson(reqJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String output = writer.writeAsCSV(flatJson,"");
            path =
                    Environment.getExternalStoragePublicDirectory
                            (
                                    //Environment.DIRECTORY_PICTURES
                                    Environment.DIRECTORY_DCIM + "/CSV/"
                            );
            name = System.currentTimeMillis()+"InstaDia_Important_Issue.csv";
            writeToFile(output,path,name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void writeToFile(String data,File path,String name)
    {
        // Get the directory for the user's public pictures directory.
        // Make sure the path directory exists.
        if(!path.exists())
        {
            // Make it, if it doesn't exit
            path.mkdirs();
        }

        final File file = new File(path,name);

        // Save your stream, don't forget to flush() it before closing it.

        try
        {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void openCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    private void writeData() {
        mProgressBar.setVisibility(View.VISIBLE);
        importantIssue = new ImportantIssue();
        importantIssue.setId(String.valueOf(System.currentTimeMillis()));
        importantIssue.setIssueDescription(edtIssue.getText().toString());
        importantIssue.setUrl(finalDownLoadUrl);
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://instadia-c84f4.firebaseio.com/ImportantIssue/"+importantIssue.getId());
        mDatabase.setValue(importantIssue);
        Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);
        lnrAddIssue.setVisibility(View.GONE);
        edtIssue.setText(null);
        imgIssue.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.app_icon_2));
        imgIssue.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.app_icon_2));
        imgIssue.setImageURI(Uri.parse(""));

    }
    private void writeData(ImportantIssue issue) {
        mProgressBar.setVisibility(View.VISIBLE);
        DatabaseReference mDatabase;
        issue.setDoneBy(issue.getDoneBy());
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://instadia-c84f4.firebaseio.com/ImportantIssue/"+issue.getId());
        mDatabase.setValue(issue);
        Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);

    }
    private String getBookingTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        dateFormat.setTimeZone(TimeZone.getDefault());
        String defaultTimezone = TimeZone.getDefault().getID();
        Date dateObj = new Date();
        return dateFormat.format(dateObj);
    }

    private void viewData() {
        mProgressBar.setVisibility(View.VISIBLE);
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://instadia-c84f4.firebaseio.com/ImportantIssue/");
        mDatabase.orderByChild("isPending").equalTo(true);
        Log.i("DATABASE",mDatabase.toString());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                importantIssuesList = new ArrayList<>();
                for (DataSnapshot issue: dataSnapshot.getChildren()) {
                    ImportantIssue tempIssue =  issue.getValue(ImportantIssue.class);
                    if (tempIssue.isPending())
                    {
                        importantIssuesList.add(tempIssue);
                    }
                }
                importantIssueAdapter.setData(importantIssuesList);
                Log.i("DATABASE","in this loop");
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("DATABASE","in the error loop");

            }
            });

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

    @Override
    public void editUserData(ImportantIssue data) {
              writeData(data);
    }

    @Override
    public void deleteUserData(int pos) {
        deleteData(importantIssuesList.get(pos));
    }

    private void deleteData(ImportantIssue issue) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://instadia-c84f4.firebaseio.com/ImportantIssue/"+issue.getId());
        mDatabase.removeValue();

    }
}
