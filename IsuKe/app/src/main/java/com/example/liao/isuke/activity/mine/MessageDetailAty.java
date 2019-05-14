package com.example.liao.isuke.activity.mine;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.databinding.ActivityMsgDetailBinding;
import com.example.liao.isuke.utils.UIUtils;

public class MessageDetailAty extends BaseAty {
    private Handler mHandler;
    ActivityMsgDetailBinding mBinding;

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_msg_detail);
        UIUtils.setDaoHangLan(this, this);
        mHandler = BaseApplication.getmHandler();
    }


    @Override
    protected void initData() {
        super.initData();
        mBinding.titleTheme.setText(UIUtils.getString(R.string.msg_detail));
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
