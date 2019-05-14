package com.example.liao.isuke.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.example.liao.isuke.bean.AppUserInfo;
import com.example.liao.isuke.utils.NetUtils;
import com.example.liao.isuke.utils.PreferenceUtil;

public class BaseApplication extends Application {
    private static BaseApplication sInstance;
    public static long i;
    public static Context mContext;
    private static Handler mHandler;

    public static AppUserInfo getUser() {
        return user;
    }

    public static void setUser(AppUserInfo user) {
        BaseApplication.user = user;
    }

    private static AppUserInfo user;
    public static long getMainThreadId() {
        return i;
    }


    public static Context getmContext() {
        return mContext;
    }


    public static Handler getmHandler() {
        return mHandler;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();//application的上下文
        i = android.os.Process.myTid();//主线程id
        mHandler = new Handler();//在主线程new一个handler

        sInstance = this;


        PreferenceUtil.init(this);//初始化键值对存储

        NetUtils.registerNetworkChangedReceiver();
    }


    public static BaseApplication getInstance() {
        return sInstance;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


}