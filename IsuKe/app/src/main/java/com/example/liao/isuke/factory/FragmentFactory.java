package com.example.liao.isuke.factory;


import com.example.liao.isuke.base.BaseFragment;
import com.example.liao.isuke.base.MyBaseFragment;
import com.example.liao.isuke.fragment.HomeFragment;
import com.example.liao.isuke.fragment.MineFragment;
import com.example.liao.isuke.fragment.SceneFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liao on 2017/5/28.
 * 工厂类，用于创建fragment
 */

public class FragmentFactory {

    /* <string-array name="main_arr">
        <item>首页</item>
        <item>理财</item>
        <item>我的</item>
    </string-array>*/
    public static final int HOME = 0;
    public static final int MONEY = 1;
    public static final int MINE = 2;


    public static Map<Integer, BaseFragment> fragmentMaps = new HashMap<>();

    public static BaseFragment createFragment(int position) {

        BaseFragment fragment = null;

        //缓存fragment,便于操作
        if (fragmentMaps.containsKey(position)) {
            return fragmentMaps.get(position);
        }
        switch (position) {
            case HOME:
                fragment = new HomeFragment();
                break;
            case MONEY:
                fragment = new SceneFragment();
                break;
            case MINE:
                fragment = new MineFragment();
                break;

            default:
                break;
        }
        fragmentMaps.put(position, fragment);
        return fragment;
    }
}
