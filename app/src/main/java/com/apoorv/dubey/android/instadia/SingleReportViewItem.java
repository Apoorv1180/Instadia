package com.apoorv.dubey.android.instadia;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleReportViewItem extends AppCompatActivity {

    ImageView imageView;
    TextView tvusername,tvdate,tvstand,tvproblemTYpe,tvproblemPlace,tvDescription;
    //intent.putExtra("date",saveData.getDate());
    //            intent.putExtra("userName",saveData.getUserName());
    //            intent.putExtra("stand",saveData.getStand());
    //            intent.putExtra("floor",saveData.getFloor());
    //            intent.putExtra("work_category",saveData.getWork_category());
    //            intent.putExtra("work_sub_category",saveData.getSub_workCategory());
    //            intent.putExtra("pavilion",saveData.getPavallion());
    //            intent.putExtra("houseKeepingPercentage",saveData.getHouseKeepingPercentage());
    //            intent.putExtra("issuedescription",saveData.getIssueDescription());
    //            intent.putExtra("completionstatus",saveData.getCompletionStatus());
    //            intent.putExtra("photouri",saveData.getPhotoUri());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_report_view_item);
        tvusername= findViewById(R.id.username);
        tvdate=findViewById(R.id.date);
        tvstand=findViewById(R.id.stand);
        tvproblemPlace=findViewById(R.id.problem_place);
        tvproblemTYpe=findViewById(R.id.problem_type);
        tvDescription=findViewById(R.id.description);
        imageView=findViewById(R.id.image_view);


        tvusername.setText(getIntent().getStringExtra("userName"));
        tvdate.setText(getIntent().getStringExtra("date"));
        tvstand.setText(getIntent().getStringExtra("stand"));
        tvproblemPlace.setText(getIntent().getStringExtra("work_category"));
        tvproblemTYpe.setText(getIntent().getStringExtra("work_sub_category")
                +"\n"
                +getIntent().getStringExtra("pavilion")
                +"\n"
                +getIntent().getStringExtra("houseKeepingPercentage"));
        tvDescription.setText(getIntent().getStringExtra("issuedescription"));
        Uri uri = Uri.parse(getIntent().getStringExtra("photouri"));
        imageView.setImageURI(uri);

    }
}
