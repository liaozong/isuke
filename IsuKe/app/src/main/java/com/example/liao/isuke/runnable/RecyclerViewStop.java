package com.example.liao.isuke.runnable;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by liao on 2018/3/19.
 */

public class RecyclerViewStop implements Runnable {
    SwipeRefreshLayout swipeLayout;

    public RecyclerViewStop(SwipeRefreshLayout swipeLayout) {
        this.swipeLayout = swipeLayout;
    }

    @Override
    public void run() {
        swipeLayout.setRefreshing(false);
    }
}
