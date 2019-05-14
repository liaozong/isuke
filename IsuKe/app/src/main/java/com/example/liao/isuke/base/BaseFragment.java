package com.example.liao.isuke.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liao on 2017/5/28.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);

    }
    //初始化页面，选择实现  需要被复写的方法用 protected
    protected void init() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView( inflater, container);
    }

    //初始化视图，子类必须实现
    protected abstract View initView(LayoutInflater inflater,ViewGroup container);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        initData();

        initEvent();

        super.onActivityCreated(savedInstanceState);

    }


    protected void initData() {

    }

    protected void initEvent() {
    }
}
