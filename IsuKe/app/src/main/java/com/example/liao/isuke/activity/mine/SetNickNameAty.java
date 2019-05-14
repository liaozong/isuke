package com.example.liao.isuke.activity.mine;

import android.app.Application;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import com.example.liao.isuke.MainActivity;
import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.bean.AppUserInfo;
import com.example.liao.isuke.bean.UserInfo;
import com.example.liao.isuke.databinding.ActivitySetNickNameBinding;
import com.example.liao.isuke.net.RequestData;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.utils.UIUtils;
import com.example.liao.isuke.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetNickNameAty extends BaseAty {
    ActivitySetNickNameBinding mBinding;


    @Override
    protected void initView() {
        super.initView();
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_set_nick_name);
        UIUtils.setDaoHangLan(this, this);
    }

    @Override
    protected void initData() {
        mBinding.titleTheme.setText("设置昵称");
        mBinding.titleSave.setText("保存");
        mBinding.etNickname.setText(BaseApplication.getUser()==null?"":BaseApplication.getUser().getNickname());
    }

    @Override
    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBinding.titleSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSave();
            }
        });

    }

    private void clickSave() {
        if (mBinding.etNickname.getText().toString().equals("")) {
            Toasty.info(SetNickNameAty.this, "请填写昵称").show();
        } else if (Utils.illegalChar(mBinding.etNickname.getText().toString())) {
            Toasty.warning(SetNickNameAty.this, "含有特殊字符，请重新填写").show();
        } else if (BaseApplication.getUser().getNickname().equals(mBinding.etNickname.getText().toString())) {
            Toasty.info(SetNickNameAty.this, "请输入不同的昵称").show();
        } else {

            String nicknameString = mBinding.etNickname.getText().toString();
            Map map = RequestData.getNickName(nicknameString);
            getSetNickNameResult(map);
        }
    }

    private String result = "";

    private String getSetNickNameResult(Map<String, String> map) {

        Call<String> call = RetrofitUtils.getRequestServies().modifyUserInfo(map);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (RetrofitUtils.isSuccess(response)) {
                    try {
                        result = response.body().toString();
                        Log.e("服务器响应成功", result);

                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = (String) jsonObject.get("resultcode");

                        /**修改成功后的处理：将BaseApplication的nickname修改后保存
                         * 更新 个人信息 的的名字显示，更新 个人中心 的名字显示
                         */
                        if (result1.equals(RetrofitUtils.SUCCESS)) {
                            AppUserInfo appUserInfo = BaseApplication.getUser();
                            jsonObject = (JSONObject) jsonObject.get("appUser");
                            //Log.e("TAG", jsonObject.get("login_name").toString());
                            appUserInfo.setNickname((String) jsonObject.get("login_name"));
                            BaseApplication.setUser(appUserInfo);


                            Toasty.success(SetNickNameAty.this, "修改成功").show();
                            finish();
                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(SetNickNameAty.this, error_msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("成功", e.toString());
                    }

                } else {
                    UIUtils.toast(SetNickNameAty.this, UIUtils.getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(SetNickNameAty.this, UIUtils.getString(R.string.request_timeout));
            }
        });
        return result;

    }
}
