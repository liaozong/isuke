package com.example.liao.isuke.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liao.isuke.bean.SharedDeviceList;
import java.util.List;

/**
 * Created by liao on 2018/3/19.
 */

public class AddSceneAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    public AddSceneAdapter(@LayoutRes int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        //可链式调用赋值
    /*    if (item.getAlias() != null && item.getAlias().length() != 0)
            helper.setText(R.id.mainlist_text, item.getAlias());
        else
            helper.setText(R.id.mainlist_text, item.getDevice_name());
        helper.setImageResource(R.id.mainlist_img, R.mipmap.ic_socket);

        helper.setVisible(R.id.edit, isEdit);
        if (mEditSharedNum.contains(helper.getLayoutPosition()))
            helper.setBackgroundRes(R.id.edit, R.mipmap.ic_choose3);
        else
            helper.setBackgroundRes(R.id.edit, R.mipmap.ic_choose2);*/
    }
}