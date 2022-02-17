package com.example.cashregister.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cashregister.R;

public class HistoryDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        setTitle("History Details");
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvProduct = (TextView) findViewById(R.id.tv_product);
        TextView tvPrice = (TextView) findViewById(R.id.tv_price);
        TextView tvDate = (TextView) findViewById(R.id.tv_date);
        Intent intent = getIntent();
        String product = intent.getStringExtra("product");
        String price = intent.getStringExtra("price");
        String purchaseDate = intent.getStringExtra("purchase_date");

        tvProduct.setText("Product: "+product);
        tvPrice.setText("Price: "+price);
        tvDate.setText("Purchase Date: "+purchaseDate);

    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}