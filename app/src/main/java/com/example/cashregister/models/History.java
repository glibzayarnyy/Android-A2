package com.example.cashregister.models;

import java.io.Serializable;
import java.util.Date;

public class History implements Serializable {
    String name;
    int quantity;
    double totalPrice;
    Date purchaseDate;

    public History() {
    }

    public History(String name, int quantity, double totalPrice, Date purchaseDate) {
        this.name = name;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.purchaseDate = purchaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
