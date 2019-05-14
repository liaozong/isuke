package com.example.liao.isuke.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liao.isuke.R;
import com.example.liao.isuke.bean.ShareUser;

import java.util.List;

/**
 * Created by liao on 2018/3/19.
 */

public class SharedUserAdapter extends BaseQuickAdapter<ShareUser, BaseViewHolder> {

    public SharedUserAdapter(@LayoutRes int layoutResId, @Nullable List<ShareUser> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShareUser item) {
        String name;
        if (item.getFriend_alias() != null && item.getFriend_alias().length() != 0)
            name = item.getFriend_alias();
        else
            name = item.getNickname();
        helper.setText(R.id.name, name)
                .setText(R.id.phone, item.getPhone());
//        .setBackgroundRes(R.id.shareduserAvatar,item.getAvatar());

        //获取当前条目position
        //int position = helper.getLayoutPosition();
    }
}