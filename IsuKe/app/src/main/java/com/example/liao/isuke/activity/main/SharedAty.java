package com.example.liao.isuke.activity.main;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.databinding.ActivityShareBinding;
import com.example.liao.isuke.utils.UIUtils;

public class SharedAty extends BaseAty {

    private ActivityShareBinding mBinding;

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_share);
        UIUtils.setDaoHangLan(this,this);
    }

    @Override
    protected void initData() {
        mBinding.titleTheme.setText(UIUtils.getString(R.string.shared));
    }

    @Override
    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
