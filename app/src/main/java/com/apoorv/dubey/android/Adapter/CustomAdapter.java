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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SaveData saveData = saveDataList.get(position);
        holder.date.setText(saveData.getDate());
        holder.userName.setText(saveData.getUserName());
        holder.stand.setText(saveData.getStand());
        holder.floor.setText(saveData.getFloor());
        holder.work_category.setText(saveData.getWork_category());
        holder.sub_workCategory.setText(saveData.getSub_workCategory());
        holder.pavallion.setText(saveData.getPavallion());
        holder.chairNumber.setText(saveData.getChairNumber());
        holder.houseKeepingPercentage.setText(saveData.getHouseKeepingPercentage());
        holder.issueDescription.setText(saveData.getIssueDescription());
        holder.completionStatus.setText(saveData.getCompletionStatus());
    }

    @Override
    public int getItemCount() {
        return saveDataList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText date,userName,stand,floor,work_category,sub_workCategory,pavallion,chairNumber,houseKeepingPercentage,issueDescription;
        public Button completionStatus;

        public MyViewHolder(View view) {
            super(view);
            date =  view.findViewById(R.id.date);
            userName =  view.findViewById(R.id.username);
            stand =  view.findViewById(R.id.stand);
            floor = view.findViewById(R.id.floor);
            work_category =  view.findViewById(R.id.work_category);
            sub_workCategory =  view.findViewById(R.id.sub_work_category);
            pavallion =  view.findViewById(R.id.pavallion);
            chairNumber =  view.findViewById(R.id.chair_number);
            houseKeepingPercentage = view.findViewById(R.id.housekeeping_percentage);
            issueDescription=view.findViewById(R.id.issue_description);
            completionStatus=view.findViewById(R.id.completionStatus);
        }
    }

}