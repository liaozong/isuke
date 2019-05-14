package com.example.liao.isuke.activity.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.databinding.ActivityForgetPwdBinding;
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
 * Created by liao on 2017/11/27.
 */

public class ForgetPwdAty extends BaseAty {

    private ActivityForgetPwdBinding mBinding;
    private String phone;
    private String num;


    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_forget_pwd);

    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        num = intent.getStringExtra("num");
    }

    protected void initEvent() {
        mBinding.getVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVerifyCode();
            }
        });
        mBinding.forgetReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPwd();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
//        LayoutAnimation.setAnimation(mLayoutPhoneNum, mLayoutVerify, mLayoutPwd, mLayoutPwdAgain, mLayoutCommit);
    }

    /*language,verifycode，password，phone，orgId*/
    private void resetPwd() {
        String verifyCode = mBinding.forgetEditVerify.getText().toString().trim();
        String pwd = mBinding.forgetEditPwd.getText().toString().trim();
        if (verifyCode.length() == 0) {
            UIUtils.toast(this, getString(R.string.verify_edit_verify));
            return;
        } else if (verifyCode.length() != 4) {
            UIUtils.toast(this, UIUtils.getString(R.string.verifiy_wrong));
            return;
        } else if (pwd.length() == 0) {
            UIUtils.toast(this, getString(R.string.forget_edit_pwd));
            return;
        } else if (pwd.length() < 6) {

            UIUtils.toast(this, UIUtils.getString(R.string.new_pwd_length));
            return;
        }
        Map map = RequestData.getForgetPwdParameter(phone, verifyCode, pwd);
        forgetPwd(map);
    }


    public void getVerifyCode() {
        if (!isGettingVerifyCode) {
            isGettingVerifyCode = true;
            Map map = RequestData.getVerifyParameter(phone, num, "1");
            getResult(map);
        }

    }

    private void forgetPwd(Map<String, String> params) {

        Call<String> call = RetrofitUtils.getRequestServies().forGetPwd(params);//传入我们请求的键值对的值

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
                            UIUtils.toast(UIUtils.getContext(), UIUtils.getString(R.string.change_success));
                            exit();
                            startActivity(new Intent(ForgetPwdAty.this, LoginAty.class));
                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(ForgetPwdAty.this, error_msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    UIUtils.toast(ForgetPwdAty.this, UIUtils.getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(ForgetPwdAty.this, UIUtils.getString(R.string.request_timeout));

            }
        });
    }

    private void getResult(Map<String, String> params) {

        Call<String> call = RetrofitUtils.getRequestServies().getVerifyCode(params);//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (RetrofitUtils.isSuccess(response)) {

                    String result = response.body().toString();

                    Log.e("成功", "!!!!!!!!!!");
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = (String) jsonObject.get("resultcode");

                        if (result1.equals(RetrofitUtils.SUCCESS)) {
                            reFreshVerifyCodeHandler.sendEmptyMessage(1);

                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(ForgetPwdAty.this, error_msg);
                            reFreshVerifyCodeHandler.sendEmptyMessage(0);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    UIUtils.toast(ForgetPwdAty.this, UIUtils.getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                reFreshVerifyCodeHandler.sendEmptyMessage(0);
                UIUtils.toast(ForgetPwdAty.this, UIUtils.getString(R.string.request_timeout));
            }
        });

    }

    private int count = 60;
    private boolean isGettingVerifyCode = false;
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

}
