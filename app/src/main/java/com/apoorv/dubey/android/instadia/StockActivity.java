package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import android.widget.Toast;

import com.apoorv.dubey.android.Adapter.StocksDataAdapter;
import com.apoorv.dubey.android.CSVUtils.CSVWriters;
import com.apoorv.dubey.android.CSVUtils.JsonFlattener;
import com.apoorv.dubey.android.Dialogs.CustomDialogForStocks;
import com.apoorv.dubey.android.model.Stock;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class StockActivity extends AppCompatActivity implements CustomDialogForStocks.DialogSaveClickedListener, View.OnClickListener {
    private Button btnAddData,btnShare;
    private StocksDataAdapter stocksDataAdapter;
    private RecyclerView recyclerView;
    private List<Stock> stockDataList;
    private File path;
    private String name;
    private DatabaseReference globaRef;
    private ProgressBar mProgressBar;
    private Boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        btnAddData =  findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        btnShare=findViewById(R.id.btn_share);
        btnAddData.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        stocksDataAdapter = new StocksDataAdapter(this,new ArrayList<Stock>());
        stocksDataAdapter.setData(new ArrayList<Stock>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL,false));
        recyclerView.setAdapter(stocksDataAdapter);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        // TODO: For reference as to how to store and show
        viewData();
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
            Intent i = new Intent(StockActivity.this, ProfileActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogSaveButtonClicked(Stock stock) {
       //TODO Save data to firebase from here
        writeData(stock);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_add:
                CustomDialogForStocks customDialog = new CustomDialogForStocks(this,this);
                customDialog.setCancelable(false);
                customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                customDialog.show();
                break;
            case R.id.btn_share:
                generateCSV();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_CC, new String[] {"apoorv111221cse@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "InstaDia_Stock CSV");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi,please find the attachment.");
                File file = new File(path, name);
                if (!file.exists() || !file.canRead()) {
                    return;
                }
                Uri uri = FileProvider.getUriForFile(StockActivity.this, getApplicationContext().getPackageName() + ".provider", file);
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
                break;
        }
    }
    private void viewData() {
        mProgressBar.setVisibility(View.VISIBLE);
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("stock");
        Log.i("DATABASE",mDatabase.toString());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stockDataList = new ArrayList<>();
                for (DataSnapshot stock: dataSnapshot.getChildren()) {
                    stockDataList.add(stock.getValue(Stock.class));
                }
                stocksDataAdapter.setData(stockDataList);
                Log.i("DATABASE","in this loop");
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("DATABASE","in the error loop");

            }

        });

    }
    private void generateCSV() {
        Gson gson = new Gson();
        String reqJson =  "";
        if (stockDataList.size() != 0)
        {
            reqJson= gson.toJson(stockDataList);
        }
        else
        {
            reqJson= gson.toJson(stockDataList);
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
            name = System.currentTimeMillis()+"Instadia_Stock.csv";
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

    private void writeData(Stock stock) {
        mProgressBar.setVisibility(View.VISIBLE);
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://instadia-c84f4.firebaseio.com/stock");
        mDatabase.child(String.valueOf(System.currentTimeMillis())).setValue(stock);
        Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);

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
