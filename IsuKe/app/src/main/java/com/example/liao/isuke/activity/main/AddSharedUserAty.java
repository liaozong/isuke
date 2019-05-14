package com.example.liao.isuke.activity.main;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.bean.ShareUser;
import com.example.liao.isuke.databinding.ActivityAddshareduserBinding;
import com.example.liao.isuke.fragment.HomeFragment;
import com.example.liao.isuke.net.RequestData;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.utils.UIUtils;
import com.example.liao.isuke.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSharedUserAty extends BaseAty {

    private ActivityAddshareduserBinding mBinding;

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_addshareduser);
        UIUtils.setDaoHangLan(this, this);
    }

    @Override
    protected void initData() {

        mBinding.titleTheme.setText(UIUtils.getString(R.string.add_shared));
    }

    @Override
    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(AddSharedUserAty.this,SharedAty.class));
                String trim = mBinding.userphone.getText().toString().trim();
                if (Utils.isPhoneNum(trim))
                    addUser(trim);
                else
                    UIUtils.toast(AddSharedUserAty.this, UIUtils.getString(R.string.registeraty_phone_error));
            }
        });
    }

    private void addUser(String phone) {
        if (BaseApplication.getUser() == null)
            return;
        Map map = RequestData.addShareUser(phone, BaseApplication.getUser().getPhone(), HomeFragment.clickDevice.getDevice_id() + "");
        addSharedUser(map);
    }

    private void addSharedUser(Map map) {

        Call<String> call = RetrofitUtils.getRequestServies().addShareUser(map);//传入我们请求的键值对的值

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
                            UIUtils.toast(AddSharedUserAty.this, UIUtils.getString(R.string.request_success));
                            setResult(1);
                            finish();
                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(AddSharedUserAty.this, error_msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    UIUtils.toast(AddSharedUserAty.this, UIUtils.getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(AddSharedUserAty.this, UIUtils.getString(R.string.request_timeout));

            }
        });
    }
}
