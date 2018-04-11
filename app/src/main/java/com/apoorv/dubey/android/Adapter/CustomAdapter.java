package com.apoorv.dubey.android.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apoorv.dubey.android.instadia.R;
import com.apoorv.dubey.android.model.SaveData;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private List<SaveData> saveDataList;
    Context context;



    public CustomAdapter(List<SaveData> saveDataList, Context context) {
        this.saveDataList = saveDataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_list_report_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SaveData saveData = saveDataList.get(position);
        holder.date.setText(saveData.getDate() +"---"+ saveData.getUserName() );
        holder.userName.setText(saveData.getStand() +"--->"+saveData.getWork_category()+"--->"+saveData.getSub_workCategory());
        holder.stand.setText(saveData.getFloor()+"--->"+saveData.getChairNumber()+"--->"+saveData.getHouseKeepingPercentage());
        holder.completionStatus.setText(saveData.getCompletionStatus());
        holder.issue.setText(saveData.getIssueDescription());
        if(holder.completionStatus.getText()=="PENDING"){
            holder.completionStatus.setBackgroundColor(context.getResources().getColor(R.color.colorPending));
        }
        else
            holder.completionStatus.setBackgroundColor(context.getResources().getColor(R.color.colorCompleted));

        holder.completionStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ADD UPDATIONS HERE
                holder.completionStatus.setText("COMPLETED");
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return saveDataList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText date,userName,stand,issue;
        public Button completionStatus;

        public MyViewHolder(View view) {
            super(view);
            date =  view.findViewById(R.id.name_and_date);
            userName =  view.findViewById(R.id.location);
            stand =  view.findViewById(R.id.area_block_number);
            issue=view.findViewById(R.id.issue);
            completionStatus=view.findViewById(R.id.completionStatus);
        }
    }

}