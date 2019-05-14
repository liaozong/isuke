package com.example.liao.isuke.activity.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.databinding.ActivityVerifycodeBinding;
import com.example.liao.isuke.factory.HashMapFactory;
import com.example.liao.isuke.factory.ThreadPoolFactory;
import com.example.liao.isuke.net.RequestData;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.utils.UIUtils;
import com.example.liao.isuke.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liao on 2018/3/21.
 */

public class VerifyCodeAty extends BaseAty {

    private ActivityVerifycodeBinding mBinding;
    private String phone;
    private String num;

    @Override
    protected void init() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        num = intent.getStringExtra("num");
    }

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_verifycode);
    }

    private boolean isGettingVerifyCode = false;

    @Override
    protected void initEvent() {
        mBinding.titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBinding.finishRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegister();
            }
        });

        mBinding.getVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickVerifyCode();
            }
        });
    }

    private void toRegister() {
        String verifyCode = mBinding.registerEditPhonenum.getText().toString().trim();
        String pwd = mBinding.pwd.getText().toString().trim();

        if (verifyCode.length() != 4) {
            UIUtils.toast(this, UIUtils.getString(R.string.verifiy_wrong));
            return;
        }
        if (pwd.length() < 6) {
            UIUtils.toast(this, UIUtils.getString(R.string.new_pwd_length));
            return;
        }
        Map map = RequestData.getRgisterParameter(phone, num, verifyCode, pwd, "");
        registerAcount(map);
    }

    private void registerAcount(Map<String, String> map) {

        Call<String> call = RetrofitUtils.getRequestServies().register(map);//传入我们请求的键值对的值

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
                            UIUtils.toast(VerifyCodeAty.this, UIUtils.getString(R.string.regist_success));
                            exit();
                            startActivity(new Intent(VerifyCodeAty.this, LoginAty.class));

                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(VerifyCodeAty.this, error_msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    UIUtils.toast(VerifyCodeAty.this, UIUtils.getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(VerifyCodeAty.this, UIUtils.getString(R.string.request_timeout));
            }
        });

    }

    /*验证码参数：language,phone,tag( register , reset, login),countryCode,orgId=-1*/
    private void clickVerifyCode() {
        if (!isGettingVerifyCode) {
            isGettingVerifyCode = true;
            Map map = RequestData.getVerifyParameter(phone, num, "0");
            getVerify(map);
        }
    }

    private int count = 60;
    /*验证码倒计时和重新获取*/
    Handler reFreshVerifyCodeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                count = 60;
                mBinding.getVerify.setText(count + "s");
                ThreadPoolFactory.getNormalThread().submitTask(new Count());
            } else if (msg.what == 2) {
                if (count >= 0) {
                    mBinding.getVerify.setText(count + "s");
                    if (isGettingVerifyCode == false) {
                        mBinding.getVerify.setText(getString(R.string.login_get_again));
                    }
                } else {
                    isGettingVerifyCode = false;
                    mBinding.getVerify.setText(getString(R.string.login_get_again));
                }
            } else {
                isGettingVerifyCode = false;
                mBinding.getVerify.setClickable(true);
                mBinding.getVerify.setText(getString(R.string.login_get_again));
            }
        }

    };

    private class Count implements Runnable {
        @Override
        public void run() {

            while (isGettingVerifyCode) {
                try {
                    Thread.sleep(1000);
                    count = count - 1;
                    reFreshVerifyCodeHandler.sendEmptyMessage(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void getVerify(Map<String, String> map) {
        Log.e("成功", "getVerify");
        Call<String> call = RetrofitUtils.getRequestServies().getVerifyCode(map);//传入我们请求的键值对的值

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
                            reFreshVerifyCodeHandler.sendEmptyMessage(1);

                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(VerifyCodeAty.this, error_msg);
                            reFreshVerifyCodeHandler.sendEmptyMessage(0);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    UIUtils.toast(VerifyCodeAty.this, UIUtils.getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                reFreshVerifyCodeHandler.sendEmptyMessage(0);
                UIUtils.toast(VerifyCodeAty.this, UIUtils.getString(R.string.request_timeout));
            }
        });

    }
}
