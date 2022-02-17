package com.example.cashregister.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cashregister.R;
import com.example.cashregister.models.History;

import java.util.List;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder>{


    private List<History> historyList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public HistoryRecyclerAdapter(Context context, List<History> historyList) {
        this.mInflater = LayoutInflater.from(context);
        this.historyList = historyList;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        History history = historyList.get(position);
        holder.tvTitle.setText(history.getName());
        holder.tvRight.setText(String.format("%.2f", history.getTotalPrice()));
        holder.tvSubtitle.setText(String.valueOf(history.getQuantity()));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return historyList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvSubtitle, tvRight;

        ViewHolder(View holder) {
            super(holder);
            tvTitle = (TextView) holder.findViewById(R.id.li_title);
            tvSubtitle = (TextView) holder.findViewById(R.id.li_subtitle);
            tvRight = (TextView) holder.findViewById(R.id.li_right);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    History getItem(int index) {
        return historyList.get(index);
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}