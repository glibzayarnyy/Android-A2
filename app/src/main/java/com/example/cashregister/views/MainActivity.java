package com.example.cashregister.views;

import static com.example.cashregister.utils.Constants.HISTORY_LIST_KEY;
import static com.example.cashregister.utils.Constants.HISTORY_LIST_TYPE;
import static com.example.cashregister.utils.Constants.PRODUCT_LIST_KEY;
import static com.example.cashregister.utils.Constants.PRODUCT_LIST_TYPE;
import static com.example.cashregister.utils.Constants.defaultProducts;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cashregister.GlobalClass;
import com.example.cashregister.R;
import com.example.cashregister.models.History;
import com.example.cashregister.models.Product;
import com.example.cashregister.utils.Constants;
import com.example.cashregister.utils.ProductListAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.easyprefs.Prefs;


public class MainActivity extends AppCompatActivity {

    ListView lvProducts;
    ProductListAdapter productListAdapter;
    TextView tvItemName, tvQuantity, tvTotalPrice;
    ScrollView scrollView;
    NumberPicker numberPicker;
    Product product = null;
    int productIndex = -1;
    Button btnManager, btnBuy;
    public List<Product> productList;
    public List<History> historyList;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = (ScrollView)findViewById(R.id.scroll_view);
        tvItemName = (TextView) findViewById(R.id.tv_item_name);
        tvQuantity = (TextView) findViewById(R.id.tv_qty);
        tvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        numberPicker = (NumberPicker) findViewById(R.id.np_qty);
        numberPicker.setMaxValue(100);
        numberPicker.setMinValue(0);
        numberPicker.setValue(0);
        btnManager = (Button) findViewById(R.id.btn_Manager);
        btnBuy = (Button) findViewById(R.id.btn_buy);
        lvProducts = (ListView) findViewById(R.id.lv_items);
        gson = new Gson();
        loadData();


        productListAdapter = new ProductListAdapter(this, productList);
        lvProducts.setAdapter(productListAdapter);

        lvProducts.setOnItemClickListener((adapterView, view, index, l) -> {
            product = productList.get(index);
            if(product != null) {
                productIndex = index;
                tvItemName.setText(product.getName());
                scrollView.post(() -> scrollView.scrollTo(0, 0));
                tvTotalPrice.setText(String.valueOf(numberPicker.getValue() * product.getPrice()));
            }
        });

        numberPicker.setOnValueChangedListener((numberPicker, oldValue, newValue) -> {
            tvQuantity.setText(String.valueOf(newValue));
            tvTotalPrice.setText(String.valueOf(newValue * (product == null ? 0 : product.getPrice())));
        });

        btnManager.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, ManagerPageActivity.class);
            startActivity(i);
        });

        btnBuy.setOnClickListener(view -> {
            if(product != null && productIndex > -1 && numberPicker.getValue() > 0) {
                if(product.getQuantity() < numberPicker.getValue()) {
                    Toast.makeText(this, "No enough quantity in stock !", Toast.LENGTH_SHORT).show();
                } else {
                    historyList.add(new History(product.getName(), numberPicker.getValue(), numberPicker.getValue() * product.getPrice(), Calendar.getInstance().getTime()));
                    productList.get(productIndex).reduceQuantity(numberPicker.getValue());
                    saveData();
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("ThankYou for your purchase");
                    alert.setMessage("Your purchase is "+numberPicker.getValue()+" "+product.getName()+" for "+numberPicker.getValue() * product.getPrice());
                    alert.show();
                    resetScreen();

                }
            } else Toast.makeText(this, "All fields are required !!!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        productListAdapter.notifyDataSetChanged();
    }

    private void resetScreen() {
        product = null;
        productIndex = -1;
        tvItemName.setText(null);
        numberPicker.setValue(0);
        tvQuantity.setText(null);
        tvTotalPrice.setText(null);
        productListAdapter.notifyDataSetChanged();
    }

    private void saveData() {
        Prefs.write()
                .content(PRODUCT_LIST_KEY, new Gson().toJson(productList, PRODUCT_LIST_TYPE))
                .content(HISTORY_LIST_KEY, new Gson().toJson(historyList, HISTORY_LIST_TYPE))
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