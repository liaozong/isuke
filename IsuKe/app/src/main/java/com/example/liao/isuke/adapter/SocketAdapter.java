package com.example.liao.isuke.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liao.isuke.R;
import com.example.liao.isuke.bean.DeviceDetail;
import com.example.liao.isuke.utils.UIUtils;

import java.util.List;

/**
 * Created by liao on 2018/3/19.
 */

public class SocketAdapter extends BaseQuickAdapter<DeviceDetail, BaseViewHolder> {

    public SocketAdapter(@LayoutRes int layoutResId, @Nullable List<DeviceDetail> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceDetail item) {
        //可链式调用赋值
        helper.addOnClickListener(R.id.socket)
                .addOnClickListener(R.id.socket_name_edit)
        .setText(R.id.socket_name,item.getDevice_sub_alias());
        String status = item.getDevice_sub_status() + "";
        if (status.equals("0")) {
            helper.setBackgroundRes(R.id.socket, R.mipmap.ic_3normal_random);
            helper.setText(R.id.socket_state, UIUtils.getString(R.string.closed));
        } else {
            helper.setBackgroundRes(R.id.socket, R.mipmap.ic_3random01);
            helper.setText(R.id.socket_state, UIUtils.getString(R.string.opened));
        }
        ;
    /*    if (item.getAlias() != null && item.getAlias().length() != 0)
            helper.setText(R.id.mainlist_text, item.getAlias());
        else
            helper.setText(R.id.mainlist_text, item.getDevice_name());
        helper.setImageResource(R.id.mainlist_img, R.mipmap.ic_socket);

        helper.setVisible(R.id.edit, isEdit);
        if (mEditHomeNum.contains(helper.getLayoutPosition()))
            helper.setBackgroundRes(R.id.edit, R.mipmap.ic_choose3);
        else
            helper.setBackgroundRes(R.id.edit, R.mipmap.ic_choose2);*/
    }
}