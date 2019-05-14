package com.example.liao.isuke.activity.mine;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.bean.AppUserInfo;
import com.example.liao.isuke.bean.UserInfo;
import com.example.liao.isuke.databinding.ActivityUserinfoBinding;
import com.example.liao.isuke.utils.UIUtils;

/**
 * Created by liao on 2018/3/19.
 */

public class UserInfoAty extends BaseAty {

    private ActivityUserinfoBinding mBinding;
    UserInfo userInfo ;

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_userinfo);
        //UIUtils.setDaoHangLan(this, this);
    }

    @Override
    protected void initData() {
        mBinding.titleTheme.setText(UIUtils.getString(R.string.userinfo));
        mBinding.titleTheme.setTextColor(getResources().getColor(R.color.baise));
        if(BaseApplication.getUser()!=null) {
            AppUserInfo user = BaseApplication.getUser();
            Log.e("UserInfo", user.toString());
            userInfo = new UserInfo(user.getApp_user_id(), user.getPhone(), user.getNickname());
            mBinding.setUser(userInfo);
        }
    }

    @Override
    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBinding.llUserame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoAty.this,SetNickNameAty.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(BaseApplication.getUser()!=null) {
            AppUserInfo user = BaseApplication.getUser();
            Log.e("UserInfo", user.toString());
            userInfo = new UserInfo(user.getApp_user_id(), user.getPhone(), user.getNickname());
            mBinding.setUser(userInfo);
        }
    }


}
