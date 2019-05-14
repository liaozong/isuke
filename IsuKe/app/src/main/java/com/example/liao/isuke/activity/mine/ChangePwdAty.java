package com.example.liao.isuke.activity.mine;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.databinding.ActivityChangepwdBinding;
import com.example.liao.isuke.factory.HashMapFactory;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.utils.UIUtils;
import com.example.liao.isuke.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liao on 2017/11/30.
 */

public class ChangePwdAty extends BaseAty {

    private ActivityChangepwdBinding mBinding;

    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_changepwd);
        UIUtils.setDaoHangLan(this, this);
    }

    protected void initData() {
        mBinding.titleTheme.setText(UIUtils.getString(R.string.change_pwd));
    }

    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBinding.changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEnter();
            }
        });
    }

    private void checkEnter() {
        String trim = mBinding.oldPwd.getText().toString().trim();
        String trim2 = mBinding.newPwd.getText().toString().trim();
        String trim3 = mBinding.newPwdAgain.getText().toString().trim();
        if (trim.length() == 0) {
            UIUtils.toast(UIUtils.getContext(), UIUtils.getString(R.string.no_old_pwd));
            return;
        } else if (trim2.length() == 0) {
            UIUtils.toast(UIUtils.getContext(), UIUtils.getString(R.string.no_new_pwd));
            return;
        } else if (trim3.length() == 0) {
            UIUtils.toast(UIUtils.getContext(), UIUtils.getString(R.string.no_new_pwd_again));
            return;
        } else if (!trim2.equals(trim3)) {
            UIUtils.toast(UIUtils.getContext(), UIUtils.getString(R.string.two_pwd));
            return;
        }

        Map<String, String> map = HashMapFactory.getHashMap();
        map.put("language", RetrofitUtils.language);
        map.put("app_user_id", BaseApplication.getUser().getApp_user_id() + "");
        //MD5加密密码后
        map.put("old_password", Utils.stringMD5(trim));
        map.put("new_password", Utils.stringMD5(trim2));
        toChangePwd(map);

    }

    private void toChangePwd(Map<String, String> params) {

        Call<String> call = RetrofitUtils.getRequestServies().changePwd(params);//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body().toString();

                Log.e("成功", response.body().toString());

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String result1 = (String) jsonObject.get("resultcode");

                    if (result1.equals(RetrofitUtils.SUCCESS)) {
                        finish();
                        Toasty.success(ChangePwdAty.this,"修改成功").show();
                        //UIUtils.toast(ChangePwdAty.this, UIUtils.getString(R.string.change_success));
                    } else {
                        String error_msg = (String) jsonObject.get("error_msg");
                        //UIUtils.toast(ChangePwdAty.this, error_msg);
                        Toasty.warning(ChangePwdAty.this,error_msg).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(ChangePwdAty.this, UIUtils.getString(R.string.request_timeout));

            }
        });
    }


}
