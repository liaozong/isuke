package com.example.liao.isuke.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liao.isuke.R;
import com.example.liao.isuke.bean.MainListData;

import java.util.List;

/**
 * Created by liao on 2018/3/19.
 */

public class DeviceTypeAdapter extends BaseQuickAdapter<MainListData, BaseViewHolder> {

    public DeviceTypeAdapter(@LayoutRes int layoutResId, @Nullable List<MainListData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainListData item) {
        //可链式调用赋值


        helper.setImageResource(R.id.mainlist_img, R.mipmap.ic_socket)
        .setText(R.id.mainlist_text, item.getName());


    }
}