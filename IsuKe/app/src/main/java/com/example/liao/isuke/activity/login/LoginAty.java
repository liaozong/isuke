package com.example.liao.isuke.activity.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.liao.isuke.MainActivity;
import com.example.liao.isuke.R;
import com.example.liao.isuke.base.BaseApplication;
import com.example.liao.isuke.base.BaseAty;
import com.example.liao.isuke.bean.AppUserInfo;
import com.example.liao.isuke.databinding.ActivityLoginBinding;
import com.example.liao.isuke.net.RetrofitUtils;
import com.example.liao.isuke.request.LoginRequest;
import com.example.liao.isuke.utils.PreferenceUtil;
import com.example.liao.isuke.utils.UIUtils;
import com.example.liao.isuke.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liao on 2018/3/14.
 */

public class LoginAty extends BaseAty {

    private ActivityLoginBinding mBinding;

    @Override
    protected void initView() {

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    private List<String> dataset;

    @Override
    protected void initData() {
        dataset = new ArrayList<>();
        String[] stringArr = UIUtils.getStringArr(R.array.cities_data);
        dataset.clear();
        for (String s : stringArr) {
            dataset.add(s);
        }
    }

    @Override
    protected void initEvent() {

        mBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginAty.this, MainActivity.class));
                finish();
                UIUtils.buttonClick(mBinding.login);
                clickLogin();
            }


        });

        mBinding.loginregist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginAty.this, PhoneAty.class);
                intent.putExtra("type", "login");
                startActivity(intent);
            }
        });
        mBinding.forgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginAty.this, ForgetPwdAty.class));
                Intent intent = new Intent(LoginAty.this, PhoneAty.class);
                intent.putExtra("type", "forget");
                startActivity(intent);
            }
        });
        mBinding.areaItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop();
            }
        });

    }


    private void clickLogin() {
        String username = mBinding.username.getText().toString().trim();
        String pwd = mBinding.pwd.getText().toString().trim();

//        username = "15919738009";
//        username = "17322309201";
        username = "15521331528";
        pwd = "123456";

        if (username.length() == 0) {
            UIUtils.toast(UIUtils.getContext(), UIUtils.getString(R.string.verify_phone_num));
            return;
        } else if (!Utils.isPhoneNum(username)) {
            UIUtils.toast(UIUtils.getContext(), UIUtils.getString(R.string.registeraty_phone_error));
            return;
        } else if (pwd.length() == 0) {
            UIUtils.toast(UIUtils.getContext(), UIUtils.getString(R.string.forget_edit_pwd));
            return;
        } else if (pwd.length() < 6) {
            UIUtils.toast(this, UIUtils.getString(R.string.new_pwd_length));
            return;
        }
//        String trim = mBinding.areaNum.getText().toString().trim();
        try {
            dealUserInfo(new LoginRequest(username, pwd, "0").loadData());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        getLoginData(RequestData.getLoginParameter(username, trim.substring(1, trim.length()), "", pwd, "", "0"));
    }

    private void dealUserInfo(AppUserInfo appUserInfo) {
        UIUtils.print("appuserinfo..." + appUserInfo.toString());
        if (appUserInfo != null) {
            UIUtils.print("appuserinfo..." + appUserInfo.toString());
            PreferenceUtil.saveUser(LoginAty.this, appUserInfo);
            PreferenceUtil.commitBoolean("isLogin", true);

            BaseApplication.setUser(appUserInfo);
            startActivity(new Intent(LoginAty.this, MainActivity.class));
            finish();
        }
    }

    private String result = "";

    public String getLoginData(Map<String, String> map) {
        if (map == null) return "";

        Call<String> call = RetrofitUtils.getRequestServies().login(map);//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (RetrofitUtils.isSuccess(response)) {
                    try {
                        result = response.body().toString();
                        Log.e("成功", response.body().toString());
                        JSONObject jsonObject = new JSONObject(result);
                        String result1 = (String) jsonObject.get("resultcode");

                        if (result1.equals(RetrofitUtils.SUCCESS)) {
                            Gson gson = new Gson();
                            String user1 = jsonObject.getString("appUser");
                            Log.e("User----", "" + user1.toString());
                            AppUserInfo user = gson.fromJson(user1, AppUserInfo.class);
                            //user.setNickname(getString(R.string.DefaultNickName));
                            BaseApplication.setUser(user);
                            Log.e("成功", "user!!!" + user.toString());
                            startActivity(new Intent(LoginAty.this, MainActivity.class));
                            finish();
                        } else {
                            String error_msg = (String) jsonObject.get("error_msg");
                            UIUtils.toast(LoginAty.this, error_msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("成功", "异常");
                    }

                } else {
                    UIUtils.toast(LoginAty.this, UIUtils.getString(R.string.request_failed));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UIUtils.toast(LoginAty.this, UIUtils.getString(R.string.request_timeout));
            }
        });
        return result;
    }

    private void showPop() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                String s = dataset.get(options1);
                mBinding.area.setText(s.substring(0, s.indexOf("+")));
                mBinding.areaNum.setText(s.substring(s.indexOf("+"), s.length()));

            }

        })
                .setSubmitText(UIUtils.getString(R.string.ok))//确定按钮文字
                .setCancelText(UIUtils.getString(R.string.cancle))//取消按钮文字
                .setTitleText("")//标题
                .setSubCalSize(16)//确定和取消文字大小
                .setTitleSize(14)//标题文字大小
                .setTitleColor(UIUtils.getColor(R.color.text_color_normal))//标题文字颜色
                .setSubmitColor(UIUtils.getColor(R.color.org_login_word))//确定按钮文字颜色
                .setCancelColor(UIUtils.getColor(R.color.text_color_normal))//取消按钮文字颜色
                .setTitleBgColor(UIUtils.getColor(R.color.baise))//标题背景颜色 Night mode
                .setBgColor(UIUtils.getColor(R.color.isuke_background))//滚轮背景颜色 Night mode
                .setContentTextSize(13)//滚轮文字大小
//                .setLinkage(true)//设置是否联动，默认true
                .setLabels("", "", "")//设置选择的三级单位
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();
        pvOptions.setPicker(dataset);// pvOptions.setPicker(options1Items);//添加数据源
        pvOptions.show();
    }
}
