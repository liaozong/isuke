package com.example.liao.isuke.activity.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.example.liao.isuke.R;
import com.example.liao.isuke.RecyclerView.Recycler;
import com.example.liao.isuke.adapter.RepeatAdapter;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.databinding.ActivityDeviceWifiBinding;
import com.example.liao.isuke.databinding.ActivityRepeatBinding;
import com.example.liao.isuke.utils.NetUtils;
import com.example.liao.isuke.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class DeviceWifiAty extends BaseAty {

    private ActivityDeviceWifiBinding mBinding;

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_device_wifi);
        UIUtils.setDaoHangLan(this, this);
    }

    @Override
    protected void initData() {
        mBinding.titleTheme.setText(R.string.conn_devicewifi);


    }

    @Override
    protected void onResume() {
        super.onResume();
        String connectWifiSsid = NetUtils.getConnectWifiSsid(this);
        connectWifiSsid = connectWifiSsid.substring(1, connectWifiSsid.length() - 1);
        if (connectWifiSsid.equals("isuke")) {
            setResult(1, new Intent());
            finish();
        }

        UIUtils.toast(this, "当前连接的wifi" + connectWifiSsid);
    }

    @Override
    protected void initEvent() {

        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBinding.conn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
            }
        });
    }
}
