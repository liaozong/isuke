package com.example.liao.isuke.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liao.isuke.R;
import com.example.liao.isuke.bean.DeviceInfo;

import java.util.List;

import static com.example.liao.isuke.fragment.HomeFragment.isEdit;
import static com.example.liao.isuke.fragment.HomeFragment.mEditSharedNum;

/**
 * Created by liao on 2018/3/19.
 */

public class MainSharedAdapter extends BaseQuickAdapter<DeviceInfo, BaseViewHolder> {

    public MainSharedAdapter(@LayoutRes int layoutResId, @Nullable List<DeviceInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceInfo item) {
        //可链式调用赋值
        if (item.getDevice_name() != null && item.getDevice_name().length() != 0)
            helper.setText(R.id.mainlist_text, item.getDevice_name());
        else
            helper.setText(R.id.mainlist_text, item.getDevice_name());
        helper.setImageResource(R.id.mainlist_img, R.mipmap.ic_socket);

        helper.setVisible(R.id.edit, isEdit);
        if (mEditSharedNum.contains(helper.getLayoutPosition()))
            helper.setBackgroundRes(R.id.edit, R.mipmap.ic_choose3);
        else
            helper.setBackgroundRes(R.id.edit, R.mipmap.ic_choose2);
    }
}