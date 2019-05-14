package com.example.liao.isuke.RecyclerView;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liao.isuke.R;
import com.example.liao.isuke.bean.MessageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liao on 2018/3/19.
 */

/**
 * Object——数据实体类型，BaseViewHolder——支持扩展的ViewHolder
 */
public class MyAdapter extends BaseQuickAdapter<MessageInfo, BaseViewHolder> {
    List<MessageInfo> msgList = new ArrayList<>();

    /**
     * 构造函数
     *
     * @param layoutResId 用于展示数据的item布局
     * @param data        用于展示到item布局的data
     */
    public MyAdapter(@LayoutRes int layoutResId, @Nullable List<MessageInfo> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, MessageInfo item) {
        //可以直接使用helper对象点.相关方法通过传入viewId和数据进行
        helper.setText(R.id.tv_title, item.getMessage_title())
//                    .setText(R.id.tv_date, "tv_date")
        ;

    }
}