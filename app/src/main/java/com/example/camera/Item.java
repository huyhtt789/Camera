package com.example.camera;


import android.graphics.Bitmap;

import java.io.Serializable;

public class Item implements Serializable {
    private String ngay;
    private String name;
    private Bitmap Image;
    private String path;
    private int position;
    private boolean isSelected;

    public Item(String ngay,Bitmap image){
        this.ngay=ngay;
        this.Image=image;
    }
    public Item(String ngay,Bitmap image,int position){
        this.ngay=ngay;
        this.Image=image;
        this.position=position;
    }
    public Item(){}

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public Bitmap getImage() {
        return Image;
    }

    public void setImage(Bitmap image) {
        Image = image;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
