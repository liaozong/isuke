package com.example.liao.isuke.RecyclerView;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.liao.isuke.R;

/**
 * Created by liao on 2018/3/19.
 */

public class swipelayout {
    public static void setSwipeLayout(SwipeRefreshLayout swipeLayout){
        swipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        swipeLayout.setColorSchemeResources(R.color.caldroid_holo_blue_dark, R.color.caldroid_holo_blue_dark, R.color.caldroid_holo_blue_dark, R.color.caldroid_holo_blue_dark);
        swipeLayout.setProgressBackgroundColor(R.color.bantouming);
    }

}
