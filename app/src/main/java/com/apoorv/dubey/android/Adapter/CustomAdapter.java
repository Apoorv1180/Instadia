package com.apoorv.dubey.android.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apoorv.dubey.android.instadia.R;
import com.apoorv.dubey.android.instadia.SingleReportViewItem;
import com.apoorv.dubey.android.model.ImportantIssue;
import com.apoorv.dubey.android.model.SaveData;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private List<SaveData> saveDataList=new ArrayList<>();
    Context context;
    EditData editData;




    public CustomAdapter(List<SaveData> saveDataList, Context context,EditData editData) {
        this.saveDataList = saveDataList;
        this.context = context;
        this.editData = editData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_list_report_item, parent, false);

        return new MyViewHolder(itemView,context,saveDataList);
    }

    public void setData(List<SaveData> importantIssues) {
        this.saveDataList = importantIssues;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        SaveData saveData = saveDataList.get(position);
        holder.date.setText(saveData.getStand() + "  "+ saveData.getFloor() +"  "+saveData.getPavallion()+"  "+saveData.getChairNumber());
        holder.userName.setText(saveData.getWork_category());
        holder.stand.setText(saveData.getSub_workCategory());
        holder.completionStatus.setText(saveData.getCompletionStatus());
        holder.issue.setText(saveData.getIssueDescription());
        if(saveData.isPending()){
            holder.completionStatus.setBackground(context.getResources().getDrawable(R.drawable.button_bg_red));
        }
        else
            holder.completionStatus.setBackground(context.getResources().getDrawable(R.drawable.button_bg_green));
//
//        holder.completionStatus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO ADD UPDATIONS HERE
//                holder.completionStatus.setText("COMPLETED");
//                saveDataList.get(position).setCompletionStatus("COMPLETED");
//                saveDataList.get(position).setPending(false);
//                notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return saveDataList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView date,userName,stand,issue;
        public Button completionStatus,btnDelete;
        List<SaveData> saveDataArrayList= new ArrayList<>();
        Context context;

        public MyViewHolder(View view, Context context, List<SaveData> saveDataArrayList) {
            super(view);
            view.setOnClickListener(this);
            this.context = context;
            this.saveDataArrayList=saveDataArrayList;
            date =  view.findViewById(R.id.name_and_date);
            userName =  view.findViewById(R.id.location);
            stand= view.findViewById(R.id.area_block_number);
            issue=view.findViewById(R.id.issue);
            completionStatus=view.findViewById(R.id.completionStatus);
            btnDelete = view.findViewById(R.id.btn_delete);
            completionStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!completionStatus.getText().toString().equals("COMPLETED"))
                    {
                        SaveData saveData = saveDataList.get(getAdapterPosition());
                        saveData.setPending(false);
                        editData.editUserData(saveData);
                        completionStatus.setText("COMPLETED");
                        saveData.setCompletionStatus("COMPLETED");
                        notifyDataSetChanged();

                    }
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                       editData.deleteUserData(getAdapterPosition());
                       notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            SaveData saveData = this.saveDataArrayList.get(position);
            Intent intent = new Intent(this.context,SingleReportViewItem.class);
            intent.putExtra("date",saveData.getDate());
            intent.putExtra("userName",saveData.getUserName());
            intent.putExtra("stand",saveData.getStand());
            intent.putExtra("floor",saveData.getFloor());
            intent.putExtra("work_category",saveData.getWork_category());
            intent.putExtra("work_sub_category",saveData.getSub_workCategory());
            intent.putExtra("pavilion",saveData.getPavallion());
            intent.putExtra("houseKeepingPercentage",saveData.getHouseKeepingPercentage());
            intent.putExtra("issuedescription",saveData.getIssueDescription());
            intent.putExtra("completionstatus",saveData.getCompletionStatus());
            intent.putExtra("photouri",saveData.getPhotoUri());
            this.context.startActivity(intent);

        }
    }
    public interface EditData
    {
        void editUserData(SaveData data);
        void deleteUserData(int pos);
    }

}