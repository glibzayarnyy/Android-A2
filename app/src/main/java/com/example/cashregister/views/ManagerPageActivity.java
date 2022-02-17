package com.example.cashregister.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cashregister.R;

public class ManagerPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_page);
        setTitle("Manager");
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((Button) findViewById(R.id.btn_histroy)).setOnClickListener(view -> {
            startActivity(new Intent(ManagerPageActivity.this, HistoryActivity.class));
        });

        ((Button) findViewById(R.id.btn_restock)).setOnClickListener(view -> {
            startActivity(new Intent(ManagerPageActivity.this, RestockActivity.class));
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}