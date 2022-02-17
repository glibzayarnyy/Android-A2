package com.example.cashregister.utils;

import com.example.cashregister.models.History;
import com.example.cashregister.models.Product;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Constants {

     public static final List<Product> defaultProducts = new ArrayList<Product>() {
        {
            add(new Product("Shoes", 10, 39.99));
            add(new Product("Hat", 50, 10.00));
            add(new Product("Belt", 80, 14.99));
            add(new Product("Gloves", 30, 19.00));
        }
    };

     public static String PRODUCT_LIST_KEY = "key_product_list";
     public static String HISTORY_LIST_KEY = "key_history_list";

    public static Type PRODUCT_LIST_TYPE = new TypeToken<List<Product>>() {}.getType();
    public static Type HISTORY_LIST_TYPE = new TypeToken<List<History>>() {}.getType();
}
