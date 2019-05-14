package com.example.liao.isuke.activity.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.liao.isuke.R;
import com.example.liao.isuke.RecyclerView.MyAdapter;
import com.example.liao.isuke.RecyclerView.Recycler;
import com.example.liao.isuke.adapter.RepeatAdapter;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.click.RecyclerItemClickListener;
import com.example.liao.isuke.databinding.ActivityRepeatBinding;
import com.example.liao.isuke.runnable.RecyclerViewStop;
import com.example.liao.isuke.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class RepeatAty extends BaseAty {

    private ActivityRepeatBinding mBinding;
    private RepeatAdapter adapter;
    private String click;

    @Override
    protected void init() {
        Intent intent = getIntent();
        click = intent.getStringExtra("click");

    }

    private void checkClick(String click) {
        if (click.contains(UIUtils.getString(R.string.Monday))) {
            mCheckData.add("1");
        }
        if (click.contains(UIUtils.getString(R.string.Tuesday))) {
            mCheckData.add("2");
        }
        if (click.contains(UIUtils.getString(R.string.Wednesday))) {
            mCheckData.add("3");
        }
        if (click.contains(UIUtils.getString(R.string.Thursday))) {
            mCheckData.add("4");
        }
        if (click.contains(UIUtils.getString(R.string.Friday))) {
            mCheckData.add("5");
        }
        if (click.contains(UIUtils.getString(R.string.Saturday))) {
            mCheckData.add("6");
        }
        if (click.contains(UIUtils.getString(R.string.Sunday))) {
            mCheckData.add("7");
        }
        adapter.setCheckData(mCheckData);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_repeat);
        UIUtils.setDaoHangLan(this, this);
    }

    private List<String> mData = new ArrayList<>();
    private List<String> mCheckData = new ArrayList<>();

    @Override
    protected void initData() {
        mBinding.titleTheme.setText(R.string.add_timed);

        mData.clear();
        String[] stringArr = UIUtils.getStringArr(R.array.repeat);
        for (String s : stringArr) {
            mData.add(s);
        }


        adapter = new RepeatAdapter(R.layout.list_repeat, mData);
        Recycler.setRecycler(this, mBinding.repeatRecyclerview, adapter);
        checkClick(click);
    }

    @Override
    protected void initEvent() {

        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBinding.repeatRecyclerview.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mCheckData.contains(position + ""))
                    mCheckData.remove(position + "");
                else
                    mCheckData.add(position + "");

                adapter.setCheckData(mCheckData);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int posotion) {

            }
        }));

        mBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckData.size() != 0) {
                    Intent intent = new Intent();
                    String click = "";
                    for (String s : mCheckData) {
                        click = click + s + ",";
                    }
                    click = click.substring(0, click.length() - 1);
                    intent.putExtra("click", click);
                    setResult(100, intent);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("click", "");

                    setResult(100, intent);
                }
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
