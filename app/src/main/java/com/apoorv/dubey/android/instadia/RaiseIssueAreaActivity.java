package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private File file;
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
//        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                importantIssuesList = new ArrayList<>();
//                for (DataSnapshot importantIssue : dataSnapshot.getChildren()) {
//                    importantIssuesList.add(importantIssue.getValue(ImportantIssue.class));
//                }
//                importantIssueAdapter.setData(importantIssuesList);
//                mProgressBar.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//            // TODO: implement the ChildEventListener methods as documented above
//            // ...
//        });

    }

// TODO: Uncomment this for save imagecode
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
//            @Override
//            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
//                //Some error handling
//                Toast.makeText(getContext(), "Some error occurred,please try again!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
//                if (type == IMP_ISSUE_PIC_CODE) {
//                    file = imageFile;
//                    Uri uri = Uri.fromFile(imageFile);
//                    imgIssue.setImageURI(uri);
//                    imgIssue.setVisibility(View.VISIBLE);
//                }
//            }
//
//        });
//    }


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
//                mProgressBar.setVisibility(View.VISIBLE);
//                storageReference = firebaseStorage.getReference("images");
//                final ImportantIssue importantIssue = new ImportantIssue();
//                importantIssue.setId(String.valueOf(System.currentTimeMillis()));
//                importantIssue.setIssueDescription(edtIssue.getText().toString());
//                Uri uri = Uri.fromFile(file);
//                final StorageReference photoRef = storageReference.child(importantIssue.getId());
//                photoRef.putFile(uri).addOnSuccessListener(getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        importantIssue.setUrl(taskSnapshot.getDownloadUrl().toString());
//                        Toast.makeText(getContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                        writeData(importantIssue);
//                    }
//                });
                break;

            case R.id.btn_cancel:
                lnrAddIssue.setVisibility(View.GONE);
                break;

            case R.id.img_take_picture:
               //TODO take picture here
                break;
        }
    }
}
