package com.example.liao.isuke.activity.main;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import com.example.liao.isuke.R;
import com.example.liao.isuke.adapter.PowerAdapter;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.bean.PowerData;
import com.example.liao.isuke.bean.PowerGeneral;
import com.example.liao.isuke.databinding.ActivityPowerBinding;
import com.example.liao.isuke.fragment.HomeFragment;
import com.example.liao.isuke.net.RequestData;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.utils.UIUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PowerDissipationAty extends BaseAty {

    private ActivityPowerBinding mBinding;
    private PowerAdapter powerAdapter;

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_power);
        UIUtils.setDaoHangLan(this, this);
    }

    private List<PowerData.PowerMonthListBean> mData = new ArrayList();

    @Override
    protected void initData() {

        mBinding.titleTheme.setText(UIUtils.getString(R.string.PowerTutor));
        powerAdapter = new PowerAdapter(this, mData);
        mBinding.powerList.setAdapter(powerAdapter);

        requestDevicePower();
    }

    private void requestDevicePower() {
        Map map = RequestData.getDevicePowerInfo(HomeFragment.clickDevice.getDevice_id() + "");
        if (map != null)
            getDevicePowerInfo(map);
    }

    public static List<PowerData> mPowerData = new ArrayList<>();

    private void getDevicePowerInfo(Map map) {
        Call<String> call = RetrofitUtils.getRequestServies().devicePower(map);//传入我们请求的键值对的值

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
                            String power = jsonObject.getString("powerGeneral");
                            String powerData = jsonObject.getString("powerData");
                            Gson gson = new Gson();
                            PowerGeneral powerGeneral = gson.fromJson(power, PowerGeneral.class);
                            List<PowerData> mlist = gson.fromJson(powerData, new TypeToken<List<PowerData>>() {
                            }.getType());
                            mPowerData.clear();
                            mPowerData.addAll(mlist);
                            setView(powerGeneral);
                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(PowerDissipationAty.this, error_msg);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    UIUtils.toast(PowerDissipationAty.this, UIUtils.getString(R.string.change_success));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(PowerDissipationAty.this, UIUtils.getString(R.string.request_timeout));
            }
        });

    }

    private void setView(PowerGeneral powerGeneral) {
        mBinding.todaypower.setText(powerGeneral.getTodayPower());
        mBinding.currentVoltage.setText(powerGeneral.getCurrentVoltage());
        mBinding.currentElectricity.setText(powerGeneral.getCurrentElectricity());
        mBinding.currentPower.setText(powerGeneral.getCurrentPower());
        mBinding.totalPower.setText(powerGeneral.getTotalPower());
        mData.clear();
        for (int x = 0; x < mPowerData.size(); x++) {
            PowerData powerData = mPowerData.get(x);
            String year = powerData.getYear();
            PowerData.PowerMonthListBean powerMonthListBean = new PowerData.PowerMonthListBean(year);
            mData.add(powerMonthListBean);
            for (PowerData.PowerMonthListBean p : powerData.getPowerMonthList()) {
                p.setYear(year);
                mData.add(p);
            }
        }
        powerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
