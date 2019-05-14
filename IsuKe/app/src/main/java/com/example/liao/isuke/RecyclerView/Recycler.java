package com.example.liao.isuke.RecyclerView;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * Created by liao on 2018/3/19.
 */

public class Recycler {
    public static void setRecycler(Context context, RecyclerView recyclerView, BaseQuickAdapter adapter, SwipeRefreshLayout swipeLayout) {
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
     /*   //创建横向布局管理
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);*/
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        swipelayout.setSwipeLayout(swipeLayout);
    }

    public static void setGrid3Recycler(RecyclerView recyclerView, BaseQuickAdapter adapter, SwipeRefreshLayout swipeLayout) {
        //创建横向布局管理
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        swipelayout.setSwipeLayout(swipeLayout);
    }

    public static void setGrid3Recycler(RecyclerView recyclerView, BaseQuickAdapter adapter) {
        //创建横向布局管理
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    public static void setGrid2Recycler(RecyclerView recyclerView, BaseQuickAdapter adapter, SwipeRefreshLayout swipeLayout) {
        //创建横向布局管理
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        swipelayout.setSwipeLayout(swipeLayout);
    }

    public static void setRecycler(Context context, RecyclerView recyclerView, BaseQuickAdapter adapter) {
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
