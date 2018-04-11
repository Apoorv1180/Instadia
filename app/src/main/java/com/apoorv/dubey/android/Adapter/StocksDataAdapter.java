package com.apoorv.dubey.android.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apoorv.dubey.android.instadia.R;
import com.apoorv.dubey.android.model.Stock;

import java.util.ArrayList;
import java.util.List;

public class StocksDataAdapter extends RecyclerView.Adapter<StocksDataAdapter.ViewHolder> {

    private List<Stock> objects = new ArrayList<Stock>();

    private Context context;
    private LayoutInflater layoutInflater;

    public StocksDataAdapter(Context context, List<Stock> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stocks_recycler_view, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Stock stock = objects.get(position);
        initializeViews(stock,holder);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return  objects.size();
    }

    public void setData(List<Stock> stocks)
    {
        this.objects = stocks;
        notifyDataSetChanged();
    }

    private void initializeViews(Stock stock, ViewHolder holder) {
        holder.tvName.setText(stock.getVendor());
        holder.tvItem.setText(stock.getItem());
        holder.tvInQuantity.setText(String.valueOf(stock.getInQuantity()));
        holder.tvOutQuantity.setText(String.valueOf(stock.getOutQuantity()));
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvItem;
        private TextView tvInQuantity;
        private TextView tvOutQuantity;

        public ViewHolder(View view) {
            super(view);
            tvName =  view.findViewById(R.id.tv_name);
            tvItem =  view.findViewById(R.id.tv_item);
            tvInQuantity =  view.findViewById(R.id.tv_in_quantity);
            tvOutQuantity =  view.findViewById(R.id.tv_out_quantity);
        }
    }
}
