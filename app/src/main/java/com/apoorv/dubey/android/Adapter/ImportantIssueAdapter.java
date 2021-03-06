package com.apoorv.dubey.android.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.apoorv.dubey.android.instadia.R;
import com.apoorv.dubey.android.instadia.RaiseIssueAreaActivity;
import com.apoorv.dubey.android.model.ImportantIssue;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImportantIssueAdapter extends RecyclerView.Adapter<ImportantIssueAdapter.ViewHolder> {

    private List<ImportantIssue> objects = new ArrayList<ImportantIssue>();
    EditData editData;

    private Context context;
    private LayoutInflater layoutInflater;

    public ImportantIssueAdapter(Context context, List<ImportantIssue> objects,EditData editData) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
        this.editData = editData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.imp_issue_recycler_view, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImportantIssue importantIssue = objects.get(position);
        initializeViews(importantIssue, holder);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void setData(List<ImportantIssue> importantIssues) {
        this.objects = importantIssues;
        notifyDataSetChanged();
    }

    private void initializeViews(ImportantIssue importantIssue, ViewHolder holder) {
        if (!importantIssue.getUrl().isEmpty()) {
            Picasso.get().load(importantIssue.getUrl()).placeholder(R.drawable.ic_launcher_background).into(holder.imgIssue);
        }

        holder.tvIssueDescription.setText(importantIssue.getIssueDescription());
        if (importantIssue.isPending()){
            holder.btnMarkComplete.setBackground(context.getResources().getDrawable(R.drawable.button_bg_red));
            holder.btnMarkComplete.setText("PENDING");
        } else {
            holder.btnMarkComplete.setBackground(context.getResources().getDrawable(R.drawable.button_bg_green));
            holder.btnMarkComplete.setText("Completed");
        }

    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIssueDescription;
        private ImageView imgIssue;
        private Button btnDelete;
        private Button btnMarkComplete;

        public ViewHolder(View view) {
            super(view);
            tvIssueDescription = view.findViewById(R.id.tv_issue_description);
            imgIssue = view.findViewById(R.id.img_issue);
            btnDelete = view.findViewById(R.id.btn_delete);
            btnMarkComplete = view.findViewById(R.id.btn_mark_completed);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editData.deleteUserData(getAdapterPosition());
                }
            });
            btnMarkComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!btnMarkComplete.getText().toString().equals("COMPLETED"))
                    {
                        ImportantIssue importantIssue = objects.get(getAdapterPosition());
                        importantIssue.setPending(false);
                        editData.editUserData(importantIssue);

                    }
                }
            });
        }
    }
    public interface EditData
    {
        void editUserData(ImportantIssue data);
        void deleteUserData(int pos);
    }
}
