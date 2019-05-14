package com.example.liao.isuke.activity.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import com.example.liao.isuke.R;
import com.example.liao.isuke.activity.mine.AdviceAty;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.databinding.ActivityDeviceOperateBinding;
import com.example.liao.isuke.factory.HashMapFactory;
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

/**
 * Created by liao on 2018/3/26.
 */

public class DeviceOperateAty extends BaseAty {

    private ActivityDeviceOperateBinding mBinding;

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_device_operate);
        UIUtils.setDaoHangLan(this, this);

    }

    @Override
    protected void initData() {
        mBinding.titleTheme.setText(UIUtils.getString(R.string.device_operate));

        mBinding.sharedUserNum.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.setName.setText(HomeFragment.clickDevice.getDevice_alias());
    }

    @Override
    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        /*设置备注*/
        mBinding.setRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remark = mBinding.setName.getText().toString().trim();
                Intent intent = new Intent(DeviceOperateAty.this, SocketRemarkAty.class);
                intent.putExtra("remark", remark);
                startActivityForResult(intent, 0);
            }
        });
        /*共享用户*/
        mBinding.sharedUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeviceOperateAty.this, SharedUserAty.class));

            }
        });
        /*定时开启*/
        mBinding.timeOpening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeviceOperateAty.this, TimedOnAty.class));
            }
        });
        /*电量功耗*/
        mBinding.powerTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(DeviceOperateAty.this, PowerDissipationAty.class));

            }
        });
        /*固件升级*/
        mBinding.deviceUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        /*意见建议*/
        mBinding.deviceAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeviceOperateAty.this, AdviceAty.class));
            }
        });

        /*删除设备*/
        mBinding.deleteDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestDeleteDevice();
            }
        });
    }

    private void requestDeleteDevice() {
        String data = HomeFragment.clickDevice.getDevice_id() + "@" + HomeFragment.clickDevice.getDevice_user_id();
        Map map = RequestData.deleteDevice(data);
        if (map != null)
            deleteDevice(map);
    }

    private void deleteDevice(Map<String, String> map) {
        Log.e("成功", "getVerify");
        Call<String> call = RetrofitUtils.getRequestServies().deleteDevice(map);//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (RetrofitUtils.isSuccess(response)) {
                    String result = response.body().toString();

                    Log.e("成功", response.body().toString());
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = (String) jsonObject.get("resultcode");

                        if (result1.equals(RetrofitUtils.SUCCESS)) {
                            UIUtils.toast(DeviceOperateAty.this, UIUtils.getString(R.string.success));
                            SocketDeviceAty.isFinish = true;
                            HomeFragment.needRefresh = true;
                            finish();
                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(DeviceOperateAty.this, error_msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    UIUtils.toast(DeviceOperateAty.this, UIUtils.getString(R.string.change_success));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(DeviceOperateAty.this, UIUtils.getString(R.string.request_timeout));
            }
        });

    }

}
