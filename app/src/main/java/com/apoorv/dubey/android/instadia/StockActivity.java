package com.apoorv.dubey.android.instadia;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.apoorv.dubey.android.Adapter.StocksDataAdapter;
import com.apoorv.dubey.android.Dialogs.CustomDialogForStocks;
import com.apoorv.dubey.android.model.Stock;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class StockActivity extends AppCompatActivity implements CustomDialogForStocks.DialogSaveClickedListener, View.OnClickListener {
    private Button btnAddData;
    private StocksDataAdapter stocksDataAdapter;
    private RecyclerView recyclerView;
    private List<Stock> stockDataList;
    private DatabaseReference globaRef;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        btnAddData =  findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        btnAddData.setOnClickListener(this);
        stocksDataAdapter = new StocksDataAdapter(this,new ArrayList<Stock>());
        stocksDataAdapter.setData(new ArrayList<Stock>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL,false));
        recyclerView.setAdapter(stocksDataAdapter);

        // TODO: For reference as to how to store and show
/*        globaRef = FirebaseDatabase.getInstance().getReferenceFromUrl(baseUrl + Constants.stocks);
        Query myTopPostsQuery = globaRef;
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stockDataList = new ArrayList<>();
                for (DataSnapshot stock: dataSnapshot.getChildren()) {
                    stockDataList.add(stock.getValue(Stock.class));
                }
                stocksDataAdapter.setData(stockDataList);
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            // ...
        });*/

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
        }
    }
}
