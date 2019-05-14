package com.example.liao.isuke.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liao.isuke.R;
import com.example.liao.isuke.bean.MainListData;
import com.example.liao.isuke.fragment.SceneFragment;

import java.util.List;
import static com.example.liao.isuke.fragment.SceneFragment.mEditSceneNum;

/**
 * Created by liao on 2018/3/19.
 */

public class SceneAdapter extends BaseQuickAdapter<MainListData, BaseViewHolder> {

    public SceneAdapter(@LayoutRes int layoutResId, @Nullable List<MainListData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainListData item) {
        //可链式调用赋值

        helper.setText(R.id.mainlist_text, item.getName());
        if (item.getName().equals("回家模式"))
            helper.setImageResource(R.id.mainlist_img, R.mipmap.ic_home);
        else if (item.getName().equals("离家模式"))
            helper.setImageResource(R.id.mainlist_img, R.mipmap.ic_leave);
        else
            helper.setImageResource(R.id.mainlist_img, R.mipmap.ic_cup);

        helper.setVisible(R.id.edit, SceneFragment.isEdit);
        if (mEditSceneNum.contains(helper.getLayoutPosition()))
            helper.setBackgroundRes(R.id.edit, R.mipmap.ic_choose3);
        else
            helper.setBackgroundRes(R.id.edit, R.mipmap.ic_choose2);
    }
}