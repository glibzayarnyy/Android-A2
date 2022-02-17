package com.example.cashregister.views;

import static com.example.cashregister.utils.Constants.HISTORY_LIST_KEY;
import static com.example.cashregister.utils.Constants.HISTORY_LIST_TYPE;
import static com.example.cashregister.utils.Constants.PRODUCT_LIST_KEY;
import static com.example.cashregister.utils.Constants.PRODUCT_LIST_TYPE;
import static com.example.cashregister.utils.Constants.defaultProducts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cashregister.R;
import com.example.cashregister.models.Product;
import com.example.cashregister.utils.ProductListAdapter;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import io.easyprefs.Prefs;

public class RestockActivity extends AppCompatActivity {

    Button btnOk, btnCancel;
    EditText etQty;
    ListView lvProducts;
    ProductListAdapter productListAdapter;
    int productIndex = -1;
    public List<Product> productList;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);
        setTitle("Restock");
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnOk = (Button) findViewById(R.id.btn_ok);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        etQty = (EditText) findViewById(R.id.et_qty);
        lvProducts = (ListView) findViewById(R.id.lv_items);
        gson = new Gson();
        loadData();
        btnCancel.setOnClickListener(view -> finish());

        btnOk.setOnClickListener(view -> {
            if(productIndex == -1) {
                Toast.makeText(this, "Please select a product first !", Toast.LENGTH_SHORT).show();
            } else {
                if(!etQty.getText().toString().isEmpty()) {
                    try {
                        int qty = Integer.parseInt(etQty.getText().toString());
                        productList.get(productIndex).setQuantity(productList.get(productIndex).getQuantity() + qty);
                        saveData();
                        productListAdapter.notifyDataSetChanged();
                    } catch (NumberFormatException nex){
                        Toast.makeText(this, "invalid input !", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "something went wrong. try again", Toast.LENGTH_SHORT).show();
                    }
                    resetPage();
                } else Toast.makeText(this, "all fields are REQUIRED !", Toast.LENGTH_SHORT).show();
            }
        });

        lvProducts.setOnItemClickListener((adapterView, view, i, l) -> productIndex = i);

        productListAdapter = new ProductListAdapter(this, productList);
        lvProducts.setAdapter(productListAdapter);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void saveData() {
        Prefs.write()
                .content(PRODUCT_LIST_KEY, new Gson().toJson(productList, PRODUCT_LIST_TYPE))
                .commit();
    }

    private void loadData() {
        if(!Prefs.has().key(PRODUCT_LIST_KEY))
            productList = defaultProducts;
        else
            try {
                productList = gson.fromJson(Prefs.read().content(PRODUCT_LIST_KEY, ""), PRODUCT_LIST_TYPE);
            } catch (Exception e) {
                productList = defaultProducts;
                Toast.makeText(this, "Error while loading products. Loaded default values", Toast.LENGTH_SHORT).show();
            }

    }

    private void resetPage() {
        productIndex = -1;
        etQty.setText(null);
    }
}