package com.example.cashregister.views;

import static com.example.cashregister.utils.Constants.HISTORY_LIST_KEY;
import static com.example.cashregister.utils.Constants.HISTORY_LIST_TYPE;
import static com.example.cashregister.utils.Constants.PRODUCT_LIST_KEY;
import static com.example.cashregister.utils.Constants.PRODUCT_LIST_TYPE;
import static com.example.cashregister.utils.Constants.defaultProducts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cashregister.GlobalClass;
import com.example.cashregister.R;
import com.example.cashregister.models.History;
import com.example.cashregister.models.Product;
import com.example.cashregister.utils.HistoryRecyclerAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.easyprefs.Prefs;

public class HistoryActivity extends AppCompatActivity implements HistoryRecyclerAdapter.ItemClickListener {

    RecyclerView recyclerView;
    HistoryRecyclerAdapter adapter;
    List<History> historyList;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setTitle("History");
        gson = new Gson();
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadData();
        adapter = new HistoryRecyclerAdapter(this, historyList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
       History history =  historyList.get(position);
        Intent i = new Intent(HistoryActivity.this, HistoryDetailsActivity.class);
        i.putExtra("product", history.getName());
        i.putExtra("price",  String.format("%.2f", history.getTotalPrice()));
        i.putExtra("purchase_date", history.getPurchaseDate().toString());
        startActivity(i);
    }


    private void loadData() {
        if(!Prefs.has().key(HISTORY_LIST_KEY))
            historyList = new ArrayList<>();
        else
            try {
                historyList = gson.fromJson(Prefs.read().content(HISTORY_LIST_KEY, ""), HISTORY_LIST_TYPE);
            } catch (Exception e) {
                historyList = new ArrayList<>();
                Toast.makeText(this, "Error while loading history.", Toast.LENGTH_SHORT).show();
            }
    }
}