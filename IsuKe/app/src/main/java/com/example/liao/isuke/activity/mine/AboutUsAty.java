package com.example.liao.isuke.activity.mine;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.databinding.ActivityAboutusBinding;
import com.example.liao.isuke.utils.UIUtils;

/**
 * Created by liao on 2018/3/19.
 */

public class AboutUsAty extends BaseAty {

    private ActivityAboutusBinding mBinding;

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_aboutus);
        UIUtils.setDaoHangLan(this, this);

    }

    @Override
    protected void initData() {
        mBinding.titleTheme.setText(UIUtils.getString(R.string.aboutus));
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
