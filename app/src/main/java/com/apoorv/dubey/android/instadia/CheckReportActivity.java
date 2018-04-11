package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.apoorv.dubey.android.Adapter.CustomAdapter;
import com.apoorv.dubey.android.model.SaveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckReportActivity extends AppCompatActivity {

    ArrayList<SaveData> saveDataArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    CustomAdapter mAdapter;
    ProgressBar progressBar;
    Button btnSHare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_report);

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference =    mFirebaseDatabase.getReference().child("master");

        btnSHare = findViewById(R.id.btn_share);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mAdapter = new CustomAdapter(saveDataArrayList,CheckReportActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Log.v("TAG",""+ childDataSnapshot.getKey()); //displays the key for the node
                    Log.v("TAG",""+ childDataSnapshot.child("stand").getValue());   //gives the value for given keyname

                    SaveData saveData =  childDataSnapshot.getValue(SaveData.class);
                    Log.v("TAG",""+ saveData.getStand()); //displays the key for the node

                    saveDataArrayList.add(saveData);
                    mAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





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
            Intent i = new Intent(CheckReportActivity.this,ProfileActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
