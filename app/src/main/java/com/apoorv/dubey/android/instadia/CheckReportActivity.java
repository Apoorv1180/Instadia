package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.os.Handler;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.apoorv.dubey.android.Adapter.CustomAdapter;
import com.apoorv.dubey.android.CSVUtils.CSVWriters;
import com.apoorv.dubey.android.CSVUtils.JsonFlattener;
import com.apoorv.dubey.android.model.ImportantIssue;
import com.apoorv.dubey.android.model.SaveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckReportActivity extends AppCompatActivity implements CustomAdapter.EditData,AdapterView.OnItemSelectedListener {

    ArrayList<SaveData> saveDataArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    CustomAdapter mAdapter,customAdapter,customAdapter1;
    ProgressBar progressBar;
    Button btnSHare;
    private Boolean doubleBackToExitPressedOnce = false;
    private File path;
    private String name;
    Spinner spinner;
    ArrayList<SaveData> saveDataArrayList1 = new ArrayList<>();
    ArrayList<SaveData> saveDataArrayList2 = new ArrayList<>();
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference databaseReference;
    boolean flag =false;

    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_report);

         mFirebaseDatabase = FirebaseDatabase.getInstance();
         databaseReference =    mFirebaseDatabase.getReference().child("master");
        spinner = findViewById(R.id.search_spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.search_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        btnSHare = findViewById(R.id.btn_share);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mAdapter = new CustomAdapter(saveDataArrayList,CheckReportActivity.this, (CustomAdapter.EditData) CheckReportActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        progressBar.setVisibility(View.VISIBLE);
        Log.i("LOOP3","iNTHIS LOOP");
        btnSHare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateCSV();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_CC, new String[] {"apoorv111221cse@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "InstaDia CSV");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi,please find the attachment.");
                File file = new File(path, name);
                if (!file.exists() || !file.canRead()) {
                    return;
                }
                Uri uri = FileProvider.getUriForFile(CheckReportActivity.this, getApplicationContext().getPackageName() + ".provider", file);
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                  saveDataArrayList = new ArrayList<>();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                   // progressBar.setVisibility(View.GONE);
                    SaveData saveData =  childDataSnapshot.getValue(SaveData.class);
                    if (saveData.isPending())
                    {
                        saveDataArrayList.add(saveData);
                    }
                    mAdapter.setData(saveDataArrayList);

                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void generateCSV() {
        Gson gson = new Gson();
        String reqJson =  "";
        if (saveDataArrayList1.size() != 0)
        {
            reqJson= gson.toJson(saveDataArrayList1);
        }
        else
        {
            reqJson= gson.toJson(saveDataArrayList);
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
            name = System.currentTimeMillis()+"MyFusion.csv";
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

    @Override
    public void editUserData(SaveData data) {
        //TODO savedatahere
        Log.i("SAVE1","SAVE1");
        writeData(data);
    }

    @Override
    public void deleteUserData(int pos) {
        deleteData(saveDataArrayList.get(pos));
    }

    private void deleteData(SaveData data) {
        DatabaseReference mDatabase;
        Log.i("DELETE","DELETE");
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://instadia-c84f4.firebaseio.com/master/"+data.getId());
        mDatabase.removeValue();
        customAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        text = item;

        customAdapter=new CustomAdapter(saveDataArrayList1,this,(CustomAdapter.EditData) CheckReportActivity.this);
        Log.i("item", item);

        saveDataArrayList1 = new ArrayList<>();
        if (item.equals("--Select Search Criteria--")) {
         saveDataArrayList1.addAll(saveDataArrayList);
        }
        else {
            for(int i=0;i<saveDataArrayList.size();i++) {
                if (saveDataArrayList.get(i).getWork_category().contains(item)) {
                    flag = true;
                    if (!saveDataArrayList1.contains(saveDataArrayList.get(i)))
                    {
                        saveDataArrayList1.add(saveDataArrayList.get(i));
                    }

                }
            }
        }


      mAdapter.setData(saveDataArrayList1);
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i("LOOP","iNTHIS LOOP");
          //  mAdapter.notifyDataSetChanged();
           // recyclerView.setAdapter(mAdapter);
    }

    private void writeData(SaveData data) {
        Log.i("SAVE1","SAVE1");
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://instadia-c84f4.firebaseio.com/master/"+data.getId());
        data.setCompletionStatus("COMPLETED");
        data.setDoneBy(data.getDoneBy());
        data.setPending(false);
        mDatabase.setValue(data);
        Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
     //   startActivity(new Intent(this,ProfileActivity.class));

    }
}
