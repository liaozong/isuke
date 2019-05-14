package com.example.liao.isuke.activity.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.bean.DeviceDetail;
import com.example.liao.isuke.databinding.ActivitySocketRemarkBinding;
import com.example.liao.isuke.fragment.HomeFragment;
import com.example.liao.isuke.net.RequestData;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.utils.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SocketRemarkAty extends BaseAty {

    private String remark;

    @Override
    protected void init() {
        Intent intent = getIntent();
        remark = intent.getStringExtra("remark");
    }

    private ActivitySocketRemarkBinding mBinding;

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_socket_remark);
        UIUtils.setDaoHangLan(this, this);
    }

    @Override
    protected void initData() {
        mBinding.titleTheme.setText(UIUtils.getString(R.string.set_name));

        mBinding.name.setText(remark);
        if (remark != null && remark.length() != 0)
            mBinding.name.setSelection(remark.length());
    }

    @Override
    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestDeviceAlias();

            }
        });
    }

    private void requestDeviceAlias() {

        Map map = RequestData.setDeviceAlise(HomeFragment.clickDevice.getDevice_sub_id() + "", mBinding.name.getText().toString().trim(), HomeFragment.clickDevice.getDevice_belong_type() + "");
        if (map != null)
            changeDeviceAlias(map);
    }

    private void changeDeviceAlias(Map<String, String> map) {

        Call<String> call = RetrofitUtils.getRequestServies().deviceAlias(map);//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (RetrofitUtils.isSuccess(response)) {
                    try {
                        String result = response.body().toString();
                        Log.e("成功", response.body().toString());
                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = (String) jsonObject.get("resultcode");

                        if (result1.equals(RetrofitUtils.SUCCESS)) {

                            HomeFragment.clickDevice.setDevice_alias(mBinding.name.getText().toString().trim());
                            UIUtils.toast(SocketRemarkAty.this, UIUtils.getString(R.string.change_success));
                            finish();
                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(SocketRemarkAty.this, error_msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("成功", "异常");
                    }
                } else {
                    UIUtils.toast(SocketRemarkAty.this, UIUtils.getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(SocketRemarkAty.this, UIUtils.getString(R.string.request_timeout));

            }
        });
    }

}
