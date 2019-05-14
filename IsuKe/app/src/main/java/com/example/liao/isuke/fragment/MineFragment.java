package com.example.liao.isuke.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liao.isuke.R;
import com.example.liao.isuke.activity.mine.AboutUsAty;
import com.example.liao.isuke.activity.mine.AdviceAty;
import com.example.liao.isuke.activity.mine.HelpAty;
import com.example.liao.isuke.activity.mine.MessageAty;
import com.example.liao.isuke.activity.mine.SettingAty;
import com.example.liao.isuke.activity.mine.UserInfoAty;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseFragment;
import com.example.liao.isuke.base.LoadingPager;
import com.example.liao.isuke.base.MyBaseFragment;
import com.example.liao.isuke.bean.AppUserInfo;
import com.example.liao.isuke.bean.UserInfo;
import com.example.liao.isuke.databinding.FragmentMineBinding;
import com.example.liao.isuke.utils.UIUtils;


/**
 * Created by liao on 2017/5/28.
 * 个人中心Fragment
 */
public class MineFragment extends BaseFragment {


    private FragmentMineBinding mBinding;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);
        return mBinding.getRoot();
    }

    @Override
    protected void initData() {

    mBinding.titleTheme.setText(UIUtils.getString(R.string.person_center));
        if(BaseApplication.getUser()!=null) {
            AppUserInfo user = BaseApplication.getUser();
            Log.e("UserInfo", user.toString());
            UserInfo userInfo = new UserInfo(user.getApp_user_id(), user.getPhone(), user.getNickname());
            mBinding.setUser(userInfo);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(BaseApplication.getUser()!=null) {
            AppUserInfo user = BaseApplication.getUser();
            Log.e("UserInfo", user.toString());
            UserInfo userInfo = new UserInfo(user.getApp_user_id(), user.getPhone(), user.getNickname());
            mBinding.setUser(userInfo);
        }

    }

    @Override
    protected void initEvent() {

        //        个人信息
        mBinding.mineInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserInfoAty.class));
            }
        });
        //        消息中心
        mBinding.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MessageAty.class));
            }
        });
        //        第三方
        mBinding.third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //        常见问题
        mBinding.problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), HelpAty.class));
            }
        });
        //        意见反馈
        mBinding.idea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AdviceAty.class));
            }
        });
        //        关于我们
        mBinding.aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AboutUsAty.class));
            }
        });
        //        设置
        mBinding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingAty.class));
            }
        });
    }

}
