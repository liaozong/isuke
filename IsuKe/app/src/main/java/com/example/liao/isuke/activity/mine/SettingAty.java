package com.example.liao.isuke.activity.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.liao.isuke.R;
import com.example.liao.isuke.activity.login.LoginAty;
import com.example.liao.isuke.activity.login.StartAty;
import com.example.liao.isuke.activity.main.SharedUserAty;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.bean.AppUserInfo;
import com.example.liao.isuke.databinding.ActivitySettingBinding;
import com.example.liao.isuke.utils.DataCleanManagerUtils;
import com.example.liao.isuke.utils.LocationUtils;
import com.example.liao.isuke.utils.UIUtils;

import java.util.TimeZone;


/**
 * Created by liao on 2017/11/30.
 */

public class SettingAty extends BaseAty implements LocationListener {

    private ActivitySettingBinding mBinding;
    private PopupWindow popupWindow;

    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        UIUtils.setDaoHangLan(this, this);
    }

    protected void initData() {
        mBinding.titleTheme.setText(UIUtils.getString(R.string.setting));
        mBinding.setAppuser(BaseApplication.getUser());
        try {
            mBinding.cachenum.setText(DataCleanManagerUtils.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBinding.timeZone.setText( TimeZone.getDefault().getDisplayName());
        mBinding.language.setText( getResources().getConfiguration().locale.getLanguage());

        com.example.liao.isuke.location.Location.toLoaction(this);

    }

    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mBinding.clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mBinding.cachenum.setText(DataCleanManagerUtils.getTotalCacheSize(SettingAty.this));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                DataCleanManagerUtils.clearAllCache(SettingAty.this);
                UIUtils.toast(SettingAty.this, "清理完成");
            }
        });

        //        修改密码
        mBinding.changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingAty.this, ChangePwdAty.class));
            }
        });
        //        退出登录
        mBinding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExitPop();
            }
        });


    }

    //退出提示框
    private void showExitPop() {
        View contentview = getLayoutInflater().inflate(R.layout.pop_logout, null);
        View rootview = getLayoutInflater().inflate(R.layout.activity_setting, null);
        popupWindow = new PopupWindow(contentview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setWidth(UIUtils.dp2px(this, 250));
        popupWindow.setAnimationStyle(R.style.myanimation);
        //显示函数
        popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
        popupWindow.setAnimationStyle(R.style.myanimation);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        contentview.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        contentview.findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
                startActivity(new Intent(SettingAty.this, LoginAty.class));
            }
        });
        UIUtils.setBackgroundAlpha(0.5f, SettingAty.this);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                UIUtils.setBackgroundAlpha(1.0f, SettingAty.this);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    mBinding.area.setText("定位失败");
                    break;
                case 1:
                    String locationInfo = (String) msg.obj;
                    mBinding.area.setText(locationInfo);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 位置信息变化时触发
     */
    @Override
    public void onLocationChanged(final Location location) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //得到纬度
                double latitude = location.getLatitude();
                //得到经度
                double longitude = location.getLongitude();
                String locationInfo = LocationUtils.getLocality(getApplicationContext(),latitude,longitude);
                if (locationInfo == null) {
                    mHandler.sendEmptyMessage(0);
                } else {
                    Message message = mHandler.obtainMessage();
                    message.obj = locationInfo.toString();
                    message.what = 1;
                    mHandler.sendMessage(message);
                }
            }
        }).start();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
