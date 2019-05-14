package com.example.liao.isuke.bean;

import android.widget.ImageView;

/**
 * Created by liao on 2018/3/20.
 */

public class MainListData {
    public MainListData( int imageView,String name){
        this.imageView = imageView;
        this.name =name;
    }
    int imageView;
    String name;
    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
