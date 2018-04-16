package com.apoorv.dubey.android.instadia;

import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class SingleReportViewItem extends AppCompatActivity {

    ImageView imageView;
    TextView tvusername, tvdate, tvstand, tvproblemTYpe, tvproblemPlace, tvDescription;
    URL url;
    Uri uri;
    ProgressBar mProgressBar;
    String finalUri = " ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_report_view_item);
        tvusername = findViewById(R.id.username);
        tvdate = findViewById(R.id.date);
        tvstand = findViewById(R.id.stand);
        tvproblemPlace = findViewById(R.id.problem_place);
        tvproblemTYpe = findViewById(R.id.problem_type);
        tvDescription = findViewById(R.id.description);
        imageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);

        tvusername.setText(getIntent().getStringExtra("userName"));
        tvdate.setText(getIntent().getStringExtra("date"));
        tvstand.setText(getIntent().getStringExtra("stand"));
        tvproblemPlace.setText(getIntent().getStringExtra("work_category"));
        tvproblemTYpe.setText(getIntent().getStringExtra("floor")
                +"\n"+
                getIntent().getStringExtra("work_sub_category")
                + "\n"
                + getIntent().getStringExtra("pavilion") +"  "+ getIntent().getStringExtra("chair")
                + "\n"
                + getIntent().getStringExtra("houseKeepingPercentage"));
        tvDescription.setText(getIntent().getStringExtra("issuedescription"));
              Log.d("YOYO",getIntent().getStringExtra("photouri"));
        if (!getIntent().getStringExtra("photouri").isEmpty()) {
            String path = getIntent().getStringExtra("photouri");
                         Picasso.get().load(path).placeholder(ContextCompat.getDrawable(getApplicationContext(),R.drawable.app_icon_2)).into(imageView);

        } else
            imageView.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.app_icon_2));
    }
}
