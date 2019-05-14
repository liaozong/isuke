package com.example.liao.isuke.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;


import com.example.liao.isuke.MainActivity;
import com.example.liao.isuke.R;
import com.example.liao.isuke.bean.UserInfo;
import com.example.liao.isuke.utils.UIUtils;

import java.util.LinkedList;

/**
 * Created by liao on 2017/12/12.
 */
public class BaseAty extends FragmentActivity {
    public static LinkedList<BaseAty> allActivitys = new LinkedList<BaseAty>();
    private long mPreClickTime;
    public final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        allActivitys.add(this);

        inits(savedInstanceState);
        init();
        initView();
        initData();
        initDatas(savedInstanceState);
        initEvent();
    }

    protected void inits(Bundle savedInstanceState) {
    }

    protected void initDatas(Bundle savedInstanceState) {

    }

    protected void init() {

    }

    protected void initView() {

    }

    protected void initData() {

    }

    protected void initEvent() {

    }

    public BaseAty mTopActivity;

    public BaseAty getTopActivity() {
        return mTopActivity;
    }

    @Override
    protected void onResume() {
        mTopActivity = this;
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        allActivitys.remove(this);
        super.onDestroy();

    }

    /**
     * 完成退出
     */
    public void exit() {
        for (BaseAty activity : allActivitys) {
            activity.finish();
        }

    }

    public void exitNoMain() {
        for (BaseAty activity : allActivitys) {
            if (this instanceof MainActivity) {

            } else
                activity.finish();
        }

    }

    @Override
    public void onBackPressed() {
        // 是否是主要
        if (this instanceof MainActivity) {// 主页
            if (System.currentTimeMillis() - mPreClickTime > 2000) {// 两次按下的时间间隔大于2s钟
                UIUtils.toast(UIUtils.getString(R.string.pre_exit));
                mPreClickTime = System.currentTimeMillis();
                return;
            } else {// 用户点击速度非常快
                exit();
                System.exit(0);//结束整个com.suren.isuke进程（DEAD状态）
            }
        } else {
            super.onBackPressed();
        }
    }

}
