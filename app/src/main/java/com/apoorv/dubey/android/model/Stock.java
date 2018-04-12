package com.apoorv.dubey.android.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Stock {
    private String id;
    private String vendorName;
    private String date;
    private String item;
    private int inQuantity;
    private int outQuantity;

    public String getVendor() {
        return vendorName;
    }

    public void setVendor(String vendor) {
        this.vendorName = vendor;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getInQuantity() {
        return inQuantity;
    }

    public void setInQuantity(int inQuantity) {
        this.inQuantity = inQuantity;
    }

    public int getOutQuantity() {
        return outQuantity;
    }

    public void setOutQuantity(int outQuantity) {
        this.outQuantity = outQuantity;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

}