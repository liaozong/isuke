package com.example.liao.isuke.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.liao.isuke.R;
import com.example.liao.isuke.factory.FragmentFactory;
import com.example.liao.isuke.utils.UIUtils;

/**
 * Created by Administrator on 2018/12/18.
 */

public class MainPagerAdapter  extends FragmentStatePagerAdapter {
    private String[] mMainTitle;
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        mMainTitle = UIUtils.getStringArr(R.array.main_arr);
    }

    @Override
    public Fragment getItem(int position) {//该方法返回当前的Fragment，交给相关联的Activity。
        return FragmentFactory.createFragment(position);
    }

    @Override
    public int getCount() {
        if (mMainTitle != null)
            return mMainTitle.length;
        return 0;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mMainTitle[position];
    }
}
