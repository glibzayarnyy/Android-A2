package com.example.cashregister.utils;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cashregister.R;
import com.example.cashregister.models.Product;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class ProductListAdapter extends BaseAdapter {
    Context context;
    List<Product> productList;
    public ProductListAdapter(Context context, List<Product> items) {
        this.context = context;
        this.productList = items;
    }
    /*private view holder class*/
    private class ViewHolder {
        TextView tvTitle, tvSubtitle, tvRight;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.product_list_item, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.li_title);
            holder.tvSubtitle = (TextView) convertView.findViewById(R.id.li_subtitle);
            holder.tvRight = (TextView) convertView.findViewById(R.id.li_right);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        holder.tvTitle.setText(product.getName());
        holder.tvSubtitle.setText(String.format("%.2f", product.getPrice()));
        holder.tvRight.setText(String.valueOf(product.getQuantity()));
        return convertView;
    }
    @Override
    public int getCount() {
        return productList.size();
    }
    @Override
    public Product getItem(int position) {
        return productList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return productList.indexOf(getItem(position));
    }
}