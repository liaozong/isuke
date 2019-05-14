package com.example.liao.isuke.activity.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.liao.isuke.R;
import com.example.liao.isuke.RecyclerView.Recycler;
import com.example.liao.isuke.adapter.DeviceTypeAdapter;
import com.example.liao.isuke.adapter.MainAdapter;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.bean.MainListData;
import com.example.liao.isuke.databinding.ActivityAddDevicetypeBinding;
import com.example.liao.isuke.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liao on 2018/3/21.
 */

public class AddDeviceTypeAty extends BaseAty {

    private ActivityAddDevicetypeBinding mBinding;

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_devicetype);

        UIUtils.setDaoHangLan(this, this);
    }

    List<MainListData> mData = new ArrayList<>();

    private DeviceTypeAdapter mAdapter;

    @Override
    protected void initData() {
        mBinding.titleTheme.setText(UIUtils.getString(R.string.add_device_type));

        mData.add(new MainListData(R.mipmap.default_avatar, "插座"));
        mData.add(new MainListData(R.mipmap.default_avatar, "开关"));
        mData.add(new MainListData(R.mipmap.default_avatar, "照明"));
        mData.add(new MainListData(R.mipmap.default_avatar, "其他设备"));
        mAdapter = new DeviceTypeAdapter(R.layout.list_devicetype, mData);
        Recycler.setGrid3Recycler(mBinding.devicetypeRecyclerview, mAdapter);
    }

    @Override
    protected void initEvent() {

        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBinding.titleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddDeviceTypeAty.this, AddDeviceAty.class));
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(AddDeviceTypeAty.this, AddDeviceAty.class));
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 3:
//                        startActivity(new Intent(AddDeviceTypeAty.this, AddDeviceAty.class));
                        break;
                    default:
                        break;
                }
            }
        });

    }
}
