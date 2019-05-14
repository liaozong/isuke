package com.example.liao.isuke.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liao.isuke.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liao on 2018/3/19.
 */

public class RepeatAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public RepeatAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    private List<String> mCheckData = new ArrayList<>();

    public void setCheckData(List<String> mdata) {
        mCheckData.clear();
        mCheckData.addAll(mdata);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.name, item);

        //获取当前条目position
        int position = helper.getLayoutPosition();
        if (mCheckData.contains(position + ""))
            helper.setVisible(R.id.check, true);
        else
            helper.setVisible(R.id.check, false);
    }
}