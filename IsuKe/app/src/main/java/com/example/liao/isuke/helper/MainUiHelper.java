package com.example.liao.isuke.helper;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.liao.isuke.R;
import com.example.liao.isuke.adapter.MainPagerAdapter;
import com.example.liao.isuke.base.BaseFragment;
import com.example.liao.isuke.factory.FragmentFactory;
import com.example.liao.isuke.utils.UIUtils;
import com.example.liao.isuke.view.BottomBar;
import com.example.liao.isuke.view.BottomBarTab;
import com.example.liao.isuke.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */

public class MainUiHelper {
    private Activity context;

    public MainUiHelper(Activity context) {
        this.context = context;
    }

    private List<View> mViews = new ArrayList<View>();

    public void init(CustomViewPager viewPager, FragmentManager fm, BottomBar bottomBar) {
        initLayout();
        initViewpager(viewPager, fm);
        initButtonBar(bottomBar);
    }

    private void initLayout() {
        LayoutInflater mLayoutInflater = LayoutInflater.from(context);
        View tab01 = mLayoutInflater.inflate(R.layout.fragment_home, null);
        View tab02 = mLayoutInflater.inflate(R.layout.fragment_scene, null);
        View tab03 = mLayoutInflater.inflate(R.layout.fragment_mine, null);

        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);
    }

    private final int First = 0;
    private CustomViewPager mViewPager;

    private void initViewpager(CustomViewPager viewPager, FragmentManager fm) {
        this.mViewPager = viewPager;
        MainPagerAdapter mPagerAdapter = new MainPagerAdapter(fm);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setScanScroll(false);


        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                handOver(First);
                mViewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void handOver(int position) {
        BaseFragment myBaseFragment = FragmentFactory.fragmentMaps.get(position);
        if (myBaseFragment != null) {
//            LoadingPager loadingPager = myBaseFragment.getLoadingPager();  //加载数据
//            loadingPager.toLoadData();
        }
    }

    private void initButtonBar(BottomBar bottomBar) {
        bottomBar.addItem(new BottomBarTab(context, R.mipmap.ic_01equipment_click, UIUtils.getString(R.string.device_center)));
        bottomBar.addItem(new BottomBarTab(context, R.mipmap.ic_02mode_click, UIUtils.getString(R.string.scene_mode)));
        bottomBar.addItem(new BottomBarTab(context, R.mipmap.ic_03user_click, UIUtils.getString(R.string.person_center)));

        bottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                mViewPager.setCurrentItem(position);
                handOver(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    public void clear() {
        FragmentFactory.fragmentMaps.clear();
    }


}
